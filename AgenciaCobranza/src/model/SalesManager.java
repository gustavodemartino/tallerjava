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
import data.Refound;
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
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE);
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

	public long getSalesCount(Date from, Date to) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT COUNT(Ticket) FROM Estacionamientos WHERE tsVenta >= ? AND tsVenta <= ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		ResultSet res = pre.executeQuery();
		res.next();
		long result = res.getLong(1);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public long getActiveSalesCount(Date from, Date to) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT COUNT(Ticket) FROM Estacionamientos WHERE tsAnulacion IS NULL AND tsVenta >= ? AND tsVenta <= ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		ResultSet res = pre.executeQuery();
		res.next();
		long result = res.getLong(1);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public long getCanceledSalesCount(Date from, Date to) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT COUNT(Ticket) FROM Estacionamientos WHERE tsAnulacion IS NOT NULL AND tsVenta >= ? AND tsVenta <= ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		ResultSet res = pre.executeQuery();
		res.next();
		long result = res.getLong(1);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public List<ParkingDetail> getActiveSales(Date from, Date to, long fromRecord, int limit) throws Exception {
		List<ParkingDetail> result = new ArrayList<ParkingDetail>();
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT * FROM Estacionamientos WHERE tsAnulacion IS NULL AND tsVenta >= ? AND tsVenta <= ? LIMIT ? OFFSET ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		pre.setInt(3, limit);
		pre.setLong(4, fromRecord);
		ResultSet res = pre.executeQuery();
		while (res.next()) {
			ParkingDetail p = new ParkingDetail();
			p.setSaleDateTime(new Date(res.getLong("tsVenta")));
			p.setSaleTicket(res.getLong("Ticket"));
			p.setPlate(res.getString("Matricula"));
			p.setStartDateTime(new Date(res.getLong("Inicio")));
			p.setDuration(res.getInt("Minutos"));
			p.setAmount(res.getLong("Pago"));
			result.add(p);
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public List<ParkingDetail> getCanceledSales(Date from, Date to, long fromRecord, int limit) throws Exception {
		List<ParkingDetail> result = new ArrayList<ParkingDetail>();
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT * FROM Estacionamientos WHERE tsAnulacion IS NOT NULL AND tsVenta >= ? AND tsVenta <= ? LIMIT ? OFFSET ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		pre.setInt(3, limit);
		pre.setLong(4, fromRecord);
		ResultSet res = pre.executeQuery();
		while (res.next()) {
			ParkingDetail p = new ParkingDetail();
			p.setSaleDateTime(new Date(res.getLong("tsVenta")));
			p.setSaleTicket(res.getLong("Ticket"));
			p.setPlate(res.getString("Matricula"));
			p.setStartDateTime(new Date(res.getLong("Inicio")));
			p.setDuration(res.getInt("Minutos"));
			p.setAmount(res.getLong("Pago"));
			p.setCancelationDateTime(new Date(res.getLong("tsAnulacion")));
			p.setCancelationNumber(res.getLong("Autorizacion"));
			p.setCredit(res.getLong("Credito"));
			result.add(p);
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public List<ParkingDetail> getSales(Date from, Date to, long fromRecord, int limit) throws Exception {
		System.out.println("From: " + from + " to: " + to);
		List<ParkingDetail> result = new ArrayList<ParkingDetail>();
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT * FROM Estacionamientos WHERE tsVenta >= ? AND tsVenta <= ? LIMIT ? OFFSET ?");
		pre.setLong(1, from.getTime());
		pre.setLong(2, to.getTime());
		pre.setInt(3, limit);
		pre.setLong(4, fromRecord);
		ResultSet res = pre.executeQuery();
		while (res.next()) {
			ParkingDetail p = new ParkingDetail();
			p.setSaleDateTime(new Date(res.getLong("tsVenta")));
			p.setSaleTicket(res.getLong("Ticket"));
			p.setPlate(res.getString("Matricula"));
			p.setStartDateTime(new Date(res.getLong("Inicio")));
			p.setDuration(res.getInt("Minutos"));
			p.setAmount(res.getLong("Pago"));
			Long l = res.getLong("tsAnulacion");
			if (l != 0) {
				p.setCancelationDateTime(new Date(l));
				p.setCancelationNumber(res.getLong("Autorizacion"));
				p.setCredit(res.getLong("Credito"));
			}
			result.add(p);
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public Refound cancelTicket(TicketCancelParameters data) throws Exception {
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
			pre.setLong(1, credit.getCreditDate());
			pre.setLong(2, credit.getAutorization());
			pre.setLong(3, credit.getAmount());
			pre.executeUpdate();
			ResultSet res = pre.getGeneratedKeys();
			res.next();
			long operacion = res.getLong(1);
			res.close();
			pre.close();
			pre = connection.prepareStatement("INSERT INTO Anulaciones (Operacion, Ticket) VALUES (?, ?)");
			pre.setLong(1, operacion);
			pre.setLong(2, credit.getTicket());
			pre.execute();
			pre.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			connection.close();
			throw new Exception(e.getMessage());
		}
		Refound result = new Refound();
		result.setDateTime(new Date(credit.getCreditDate()));
		result.setTicket(credit.getTicket());
		result.setAmount(credit.getAmount());
		result.setAuthorization(credit.getAutorization());
		return result;
	}
}
