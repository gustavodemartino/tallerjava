package services;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import data.Credit;
import data.Sale;

@WebService
public class Parking {
	private long lastTicket = 0;

	@WebMethod
	public Sale parkingSale(String operator, String plate, long startTime, int minutes) {
		System.out.println("\nIntendencia" + "\nOperación: Venta" + "\nOperador: " + operator + "\nMatrícula: " + plate
				+ "\nInicio: " + new Date(startTime) + "\nDuración: " + minutes + " minutos");

		Sale sale = new Sale();
		if (minutes < 1) {
			sale.setResult(401);
			sale.setMessage("Tiempo de estacionamiento inválido");
		} else {
			sale.setResult(200);
			sale.setMessage("Ok");
			int m = (minutes / 30 + (minutes % 30 > 0 ? 1 : 0));
			sale.setAmount(m * 1400);
			sale.setMinutes(m * 30);
			sale.setCustomerName(operator);
			sale.setPlate(plate.trim().toUpperCase());
			lastTicket++;
			sale.seteTicketNumber(lastTicket);
			sale.setSaleDate(new Date().getTime());
			sale.setStartDateTime(startTime);
		}
		return sale;
	}

	@WebMethod
	public Credit parkingCancel(String operator, long eTicketNumber) {
		System.out.println("\nIntendencia" + "\nOperación: Cancelación" + "\nOperador: " + operator + "\nTicket: "
				+ eTicketNumber);

		Credit credit = new Credit();
		if (eTicketNumber % 2 == 0) {
			credit.setResult(200);
			credit.setMessage("Ok");
			credit.setAmount(-1400);
			credit.setCustomerName("customerMame");
			lastTicket++;
			credit.seteCreditNumber(this.lastTicket);
			credit.seteTicketNumber(eTicketNumber);
			credit.setSaleDate(new Date().getTime());
		} else {
			credit.setResult(402);
			credit.setMessage("Invalid ticket number");
		}
		return credit;
	}
}
