package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.Credit;
import data.Operator;
import data.Sale;
import data.SaleDetail;

public class SalesManager {
	private static SalesManager instance = null;

	private DataSource ds;

	private long nextOperation;

	private SalesManager() {
		this.ds = null;
	}

	public static SalesManager getInstance() {
		if (instance == null) {
			instance = new SalesManager();
		}
		return instance;
	}

	private void init() throws Exception {
		if (this.ds == null) {
			InitialContext initContext = new InitialContext();
			this.ds = (DataSource) initContext.lookup("java:jboss/datasources/IntendenciaDS");
			Connection connection = ds.getConnection();
			Statement sta = connection.createStatement();
			ResultSet res = sta.executeQuery(
					"SELECT MAX(Ticket) as Ticket, MAX(Autorizacion) as Autorizacion FROM Estacionamientos");
			res.next();
			this.nextOperation = Math.max(res.getLong("Ticket"), res.getLong("Autorizacion")) + 1;
			res.close();
			sta.close();
			connection.close();
		}
	}

	public Sale saleParking(String operatorName, String plate, long startTime, int minutes) {
		Sale sale = new Sale();
		Operator operator = null;
		try {
			this.init();
			operator = OperatorManager.getInstance().getOperator(operatorName);
		} catch (Exception e) {
			sale.setResult(501);
			sale.setMessage(e.getMessage());
		}
		if (plate == null) {
			plate = "";
		}
		plate = plate.trim().toUpperCase();
		if (startTime == 0) {
			startTime = new Date().getTime();
		}

		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long actualTime = cal.getTimeInMillis();

		cal.setTimeInMillis(startTime);
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		cal.setTimeInMillis(actualTime);
		cal.set(Calendar.HOUR_OF_DAY, hora);
		cal.set(Calendar.MINUTE, min);
		startTime = cal.getTimeInMillis();

		if (operator == null) {
			sale.setResult(401);
			sale.setMessage("No se pudieron validar las credenciales del operador");
		} else if (!plate.matches("[A-S][A-Z]{2}\\d{4}")) {
			sale.setResult(402);
			sale.setMessage("Matrícula inválida");
		} else if (startTime < actualTime) {
			sale.setResult(403);
			sale.setMessage("Hora del inicio del estacionamiento inválida");
		} else if (minutes < 1) {
			sale.setResult(404);
			sale.setMessage("Tiempo de estacionamiento inválido");
		} else {
			sale.setResult(200);
			sale.setMessage("Ok");
			sale.setSaleDate(new Date().getTime());
			sale.setCustomerName(operator.getName());
			sale.setPlate(plate);
			sale.setStartDateTime(startTime);

			int m = (minutes / 30 + (minutes % 30 > 0 ? 1 : 0));
			sale.setEndDateTime(startTime + (long) (m * 30) * 60000);
			sale.setAmount(m * 1400);
			try {
				Connection connection = this.ds.getConnection();
				PreparedStatement pre;
				connection.setAutoCommit(false);
				pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Importe) VALUES (?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				pre.setLong(1, sale.getSaleDate());
				pre.setLong(2, sale.getAmount());
				try {
					pre.executeUpdate();
					ResultSet res = pre.getGeneratedKeys();
					res.next();
					long operacion = res.getLong(1);
					res.close();
					pre.close();
					pre = connection.prepareStatement(
							"INSERT INTO Estacionamientos (Operador, Operacion, Matricula, Inicio, Final, Ticket) VALUES (?, ?, ?, ?, ?, ?)");
					pre.setLong(1, operator.getDbId());
					pre.setLong(2, operacion);
					pre.setString(3, sale.getPlate());
					pre.setLong(4, sale.getStartDateTime());
					pre.setLong(5, sale.getEndDateTime());
					pre.setLong(6, this.nextOperation);
					pre.execute();
					pre.close();
					connection.commit();
					connection.close();
					sale.seteTicketNumber(this.nextOperation++);
				} catch (Exception e) {
					sale.setResult(502);
					sale.setMessage(e.getMessage());
					pre.close();
					connection.rollback();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sale;
	}

	public Credit parkingCancel(String operatorName, long eTicketNumber) {
		Credit credit = new Credit();
		Operator operator = null;
		do {
			try {
				this.init();
				operator = OperatorManager.getInstance().getOperator(operatorName);
			} catch (Exception e) {
				credit.setResult(401);
				credit.setMessage("No se pudieron validar las credenciales del operador");
				break;
			}
			try {
				Connection connection = this.ds.getConnection();
				connection.setAutoCommit(false);
				PreparedStatement pre = connection.prepareStatement(
						"SELECT Id, FechaHora, Importe, Matricula, Inicio, Anulacion, Autorizacion FROM Operaciones as op, Estacionamientos as es WHERE op.Id = es.Operacion AND es.Operador = ? AND es.Ticket = ?");
				pre.setLong(1, operator.getDbId());
				pre.setLong(2, eTicketNumber);
				ResultSet res = pre.executeQuery();
				if (!res.next()) {
					credit.setResult(404);
					credit.setMessage("Número de ticket inválido");
					break;
				}
				res.getLong("Anulacion");
				if (!res.wasNull()) {
					credit.setResult(405);
					credit.setMessage("El ticket solicitado ya ha sido anulado");
					break;
				}
				if (res.getLong("Inicio") < (new Date()).getTime()) {
					credit.setResult(406);
					credit.setMessage("Imposible anular un ticket ya utilizado");
					break;
				}
				long id = res.getLong("Id");
				credit.setAmount(-res.getLong("Importe"));
				credit.setAutorization(nextOperation);
				credit.setTicket(eTicketNumber);
				credit.setCreditDate(new Date().getTime());
				res.close();
				pre.close();

				try {
					pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Importe) VALUES (?, ?)",
							Statement.RETURN_GENERATED_KEYS);
					pre.setLong(1, credit.getCreditDate());
					pre.setLong(2, credit.getAmount());
					pre.executeUpdate();
					res = pre.getGeneratedKeys();
					res.next();
					long operacion = res.getLong(1);
					res.close();
					pre.close();

					pre = connection.prepareStatement(
							"UPDATE Estacionamientos SET  Anulacion=?, Autorizacion = ? WHERE Operacion=?");
					pre.setLong(1, operacion);
					pre.setLong(2, credit.getAutorization());
					pre.setLong(3, id);
					pre.executeUpdate();
					pre.close();
					connection.commit();
					connection.close();
					credit.setResult(200);
					credit.setMessage("Ok");
					nextOperation++;
				} catch (Exception e) {
					connection.rollback();
					connection.close();
					throw new Exception(e.getMessage());
				}
			} catch (Exception e) {
				credit.setResult(501);
				credit.setMessage(e.getMessage());
			}
		} while (false);
		return credit;
	}

	public List<SaleDetail> getSales(Date from, Date to) throws Exception {
		this.init();
		List<SaleDetail> result = new ArrayList<SaleDetail>();
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT o.FechaHora AS tsEmision, p.Id AS IdOperador, p.Firma AS FirmaOperador, p.Nombre AS NombreOperador, Matricula, Inicio AS tsInicio, Final AS tsFinal,Ticket,o.Importe, a.FechaHora AS tsAnulacion,Autorizacion, a.Importe AS Credito FROM Estacionamientos AS e INNER JOIN Operaciones AS o ON e.Operacion= o.Id INNER JOIN Operadores AS p ON e.Operador = p.id LEFT JOIN Operaciones AS a ON e.Anulacion= a.Id WHERE o.FechaHora >=? AND o.FechaHora<=?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		ResultSet res = pre.executeQuery();
		while (res.next()) {
			Operator o = new Operator(res.getLong("IdOperador"), res.getString("FirmaOperador"),
					res.getString("NombreOperador"));
			SaleDetail sale = new SaleDetail(o, new Date(res.getLong("tsEmision")), res.getString("Matricula"),
					new Date(res.getLong("tsInicio")), new Date(res.getLong("tsFinal")), res.getLong("Ticket"),
					res.getLong("Importe"));
			long tsa = res.getLong("tsAnulacion");
			if (!res.wasNull()) {
				sale.setCancelation(new Date(tsa), res.getLong("Autorizacion"), res.getLong("Credito"));
			}
			result.add(sale);
		}
		return result;
	}
}
