package services;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import data.Credit;
import data.Sale;
import model.SaleManager;

@WebService
public class Parking {

	@WebMethod
	public Sale parkingSale(String operator, String plate, long startTime, int minutes) {
		System.out.println("\nIntendencia" + "\nOperaci�n: Venta" + "\nOperador: " + operator + "\nMatr�cula: " + plate
				+ "\nInicio: " + new Date(startTime) + "\nDuraci�n: " + minutes + " minutos");
		return SaleManager.getInstance().saleParking(operator, plate, startTime, minutes);
	}

	@WebMethod
	public Credit parkingCancel(String operator, long eTicketNumber) {
		System.out.println("\nIntendencia" + "\nOperaci�n: Cancelaci�n" + "\nOperador: " + operator + "\nTicket: "
				+ eTicketNumber);
		return SaleManager.getInstance().parkingCancel(operator, eTicketNumber);
	}
}
