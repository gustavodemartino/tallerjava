package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.ParkingDetail;
import data.Ticket;
import data.TicketCancelParameters;
import data.TicketSaleParameters;
import intendenciaWS.Credit;
import intendenciaWS.Parking;
import intendenciaWS.ParkingService;
import intendenciaWS.Sale;

public class SalesManager {
	private static SalesManager instance = null;
	private Parking parkingService;
	private DataSource ds;
	private String operador = "abitab";

	private SalesManager() throws Exception {
		ParkingService p = new ParkingService();
		this.parkingService = p.getParkingPort();
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE_LOOKUP);
	}

	public static SalesManager getInstance() throws Exception {
		if (instance == null) {
			instance = new SalesManager();
		}
		return instance;
	}

	public Ticket saleTicket(TicketSaleParameters data) throws Exception {
		Sale sale = this.parkingService.parkingSale(operador, data.getPlate(), data.getStartTime().getTime(),
				data.getMinutes());
		if (sale.getResult() != 200) {
			throw new Exception(sale.getResult() + ": " + sale.getMessage());
		}
		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Numero, Importe) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pre.setLong(1, sale.getSaleDate());
			pre.setLong(2, sale.getETicketNumber());
			pre.setLong(3, sale.getAmount());

			pre.executeUpdate();
			ResultSet res = pre.getGeneratedKeys();
			res.next();
			long operacion = res.getLong(1);
			res.close();
			pre.close();
			pre = connection.prepareStatement(
					"INSERT INTO Tickets (Operacion, Matricula, Inicio, Minutos) VALUES (?, ?, ?, ?)");
			pre.setLong(1, operacion);
			pre.setString(2, sale.getPlate());
			pre.setLong(3, sale.getStartDateTime());
			pre.setInt(4, (int) ((sale.getEndDateTime() - sale.getStartDateTime()) / 60000));
			pre.execute();
			pre.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			connection.close();
			throw new Exception(e.getMessage());
		}

		Ticket result = new Ticket();
		result.setAgency(sale.getCustomerName());
		result.setSaleDateTime(new Date(sale.getSaleDate()));
		result.setStartDateTime(new Date(sale.getStartDateTime()));
		result.setEndDateTime(new Date(sale.getEndDateTime()));
		result.setTicketNumber(sale.getETicketNumber());
		result.setPlate(sale.getPlate());
		result.setAmount(sale.getAmount());
		return result;
	}

	public List<ParkingDetail> getSales(Date from, Date to) throws Exception {
		System.out.println("From: " + from + " to: " + to);
		List<ParkingDetail> result = new ArrayList<ParkingDetail>();
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT v.FechaHoraVenta, v.Ticket, v.Matricula, v.Inicio, v.Minutos, c.FechaHoraAnulacion, c.Anulacion, v.Pago, c.Devolucion FROM ( SELECT op.FechaHora as FechaHoraVenta, op.Numero as Ticket, op.Importe as Pago, ti.Matricula, ti.Inicio, ti.Minutos FROM Operaciones as op, Tickets as ti WHERE ti.Operacion = op.Id AND op.FechaHora > ? AND op.FechaHora < ?) AS v LEFT OUTER JOIN ( SELECT op.FechaHora as FechaHoraAnulacion, op.Numero as Anulacion, op.Importe as Devolucion, an.Numero as Ticket FROM Operaciones as op, Anulaciones as an WHERE an.Operacion = op.Id ) AS c ON v.Ticket = c.Ticket");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		ResultSet res = pre.executeQuery();
		while (res.next()) {
			ParkingDetail p = new ParkingDetail();
			p.setSaleDateTime(new Date(res.getLong("FechaHoraVenta")));
			p.setSaleTicket(res.getLong("Ticket"));
			p.setPlate(res.getString("Matricula"));
			p.setStartDateTime(new Date(res.getLong("Inicio")));
			p.setDuration(res.getInt("Minutos"));
			p.setAmount(res.getLong("Pago"));
			Long l = res.getLong("FechaHoraAnulacion");
			if (l != 0) {
				p.setCancelationDateTime(new Date(l));
				p.setCancelationNumber(res.getLong("Anulacion"));
				p.setCredit(res.getLong("Devolucion"));
			}
			result.add(p);
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public void cancelTicket(TicketCancelParameters data) throws Exception {
		Credit credit = this.parkingService.parkingCancel(operador, data.getTicketNumber());
		if (credit.getResult() != 200) {
			throw new Exception(credit.getResult() + ": " + credit.getMessage());
		}
		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Numero, Importe) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pre.setLong(1, credit.getSaleDate());
			pre.setLong(2, credit.getECreditNumber());
			pre.setLong(3, credit.getAmount());
			pre.executeUpdate();
			ResultSet res = pre.getGeneratedKeys();
			res.next();
			long operacion = res.getLong(1);
			res.close();
			pre.close();
			pre = connection.prepareStatement("INSERT INTO Anulaciones (Operacion, Numero) VALUES (?, ?)");
			pre.setLong(1, operacion);
			pre.setLong(2, credit.getETicketNumber());
			pre.execute();
			pre.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			connection.close();
			throw new Exception(e.getMessage());
		}
	}

}
