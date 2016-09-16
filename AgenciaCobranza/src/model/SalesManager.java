package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

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

	private SalesManager() throws Exception {
		ParkingService p = new ParkingService();
		this.parkingService = p.getParkingPort();
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup("java:jboss/datasources/AgenciaDS");
	}

	public static SalesManager getInstance() throws Exception {
		if (instance == null) {
			instance = new SalesManager();
		}
		return instance;
	}

	public Ticket saleTicket(TicketSaleParameters data) throws Exception {
		Sale sale = this.parkingService.parkingSale("abitab", data.getPlate(),
				data.getStartTime().getTime(), data.getMinutes());
		if (sale.getResult() != 200) {
			throw new Exception(sale.getResult() + ": " + sale.getMessage());
		}
		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Numero, Importe) VALUES (?, ?, ?)");
			pre.setLong(1, sale.getSaleDate());
			pre.setLong(2, sale.getETicketNumber());
			pre.setLong(3, sale.getAmount());
			pre.execute();
			pre.close();
			pre = connection
					.prepareStatement("SELECT Id FROM Operaciones WHERE FechaHora = ? AND Numero = ? AND Importe = ?");
			pre.setLong(1, sale.getSaleDate());
			pre.setLong(2, sale.getETicketNumber());
			pre.setLong(3, sale.getAmount());
			ResultSet res = pre.executeQuery();
			long id = -1;
			if (res.next()) {
				id = res.getLong("Id");
			}
			res.close();
			pre.close();
			pre = connection.prepareStatement(
					"INSERT INTO Tickets (Operacion, Matricula, Inicio, Minutos) VALUES (?, ?, ?, ?)");
			pre.setLong(1, id);
			pre.setString(2, sale.getPlate());
			pre.setLong(3, sale.getStartDateTime());
			pre.setInt(4, (int)(sale.getEndDateTime() - sale.getStartDateTime()));
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

	public void cancelTicket(TicketCancelParameters data) throws Exception {
		Credit credit = this.parkingService.parkingCancel("Agencia de cobranza", data.getTicketNumber());
		if (credit.getResult() != 200) {
			throw new Exception(credit.getResult() + ": " + credit.getMessage());
		}
		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection.prepareStatement("INSERT INTO Operaciones (FechaHora, Numero, Importe) VALUES (?, ?, ?)");
			pre.setLong(1, credit.getSaleDate());
			pre.setLong(2, credit.getECreditNumber());
			pre.setLong(3, credit.getAmount());
			pre.execute();
			pre.close();
			pre = connection
					.prepareStatement("SELECT Id FROM Operaciones WHERE FechaHora = ? AND Numero = ? AND Importe = ?");
			pre.setLong(1, credit.getSaleDate());
			pre.setLong(2, credit.getECreditNumber());
			pre.setLong(3, credit.getAmount());
			ResultSet res = pre.executeQuery();
			long id = -1;
			if (res.next()) {
				id = res.getLong("Id");
			}
			res.close();
			pre.close();
			pre = connection.prepareStatement("INSERT INTO Anulaciones (Operacion, Numero) VALUES (?, ?)");
			pre.setLong(1, id);
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
