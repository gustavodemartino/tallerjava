package services;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import data.Credit;
import data.Sale;
import model.SalesManager;

@WebService
public class Parking {

	@WebMethod
	public Sale parkingSale(String operator, String plate, long startTime, int minutes) {
		System.out.println("\nIntendencia" + "\nOperación: Venta" + "\nOperador: " + operator + "\nMatrícula: " + plate
				+ "\nInicio: " + new Date(startTime) + "\nDuración: " + minutes + " minutos");
		return SalesManager.getInstance().parkingSale(operator, plate, startTime, minutes);
	}

	@WebMethod
	public Credit parkingCancel(String operator, long eTicketNumber) {
		System.out.println("\nIntendencia" + "\nOperación: Cancelación" + "\nOperador: " + operator + "\nTicket: "
				+ eTicketNumber);
		return SalesManager.getInstance().parkingCancel(operator, eTicketNumber);
	}
}
