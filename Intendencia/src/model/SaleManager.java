package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.Credit;
import data.Operator;
import data.Sale;

public class SaleManager {
	private static SaleManager instance = null;

	private DataSource ds;

	private SaleManager() {
		this.ds = null;
	}

	public static SaleManager getInstance() {
		if (instance == null) {
			instance = new SaleManager();
		}
		return instance;
	}

	private void init() throws Exception {
		if (this.ds == null) {
			InitialContext initContext = new InitialContext();
			this.ds = (DataSource) initContext.lookup("java:jboss/datasources/IntendenciaDS");
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
			sale.setEndDateTime(startTime + (long)(m * 30) * 60000);
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
							"INSERT INTO Estacionamientos (Operador, Operacion, Matricula, Inicio, Final, Anulado) VALUES (?, ?, ?, ?, ?, ?)");
					pre.setLong(1, operator.getId());
					pre.setLong(2, operacion);
					pre.setString(3, sale.getPlate());
					pre.setLong(4, sale.getStartDateTime());
					pre.setLong(5, sale.getEndDateTime());
					pre.setBoolean(6, false);
					pre.execute();
					pre.close();
					connection.commit();
					connection.close();
					sale.seteTicketNumber(operacion);
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

	public Credit parkingCancel(String operator, long eTicketNumber) {
		Credit credit = new Credit();
		if (eTicketNumber % 2 == 0) {
			credit.setResult(200);
			credit.setMessage("Ok");
			credit.setAmount(-1400);
			credit.setCustomerName("customerMame");
			credit.seteCreditNumber(0);
			credit.seteTicketNumber(eTicketNumber);
			credit.setSaleDate(new Date().getTime());
		} else {
			credit.setResult(402);
			credit.setMessage("Invalid ticket number");
		}
		return credit;
	}
}
