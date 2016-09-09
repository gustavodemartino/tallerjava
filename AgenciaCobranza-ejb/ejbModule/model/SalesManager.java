package model;

import java.util.Date;

import data.Ticket;
import data.TicketCancelParameters;
import data.TicketSaleParameters;
import intendencia.Parking;
import intendencia.ParkingService;
import util.FixedPoint;

public class SalesManager {
	private static SalesManager instance = null;
	private Parking parkingService;

	private SalesManager() {
		this.parkingService = new ParkingService().getParkingPort();
	}

	public static SalesManager getInstance() {
		if (instance == null) {
			instance = new SalesManager();
		}
		return instance;
	}

	public Ticket saleTicket(TicketSaleParameters data) {
		Ticket result = new Ticket();
		result.setAgency("Agencia de cobranza");
		result.setSaleDateTime(new Date());
		result.setTicketNumber(1);
		result.setPlate(data.getPlate());
		result.setStartDateTime(data.getStartTime());
		result.setMinutes(data.getMinutes());
		result.setAmount(new FixedPoint(data.getMinutes() / 30 * 14, 2));
		return result;
	}

	public void cancelTicket(TicketCancelParameters data) {
		// TODO
	}

}
