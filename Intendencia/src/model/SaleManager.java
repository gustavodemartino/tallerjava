package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import data.Credit;
import data.Operator;
import data.Sale;

public class SaleManager {
	private static SaleManager instance = null;

	private long nextTicketNumber;

	private SaleManager() {
		// Obtener nextTicketNumber
	}

	public static SaleManager getInstance() {
		if (instance == null) {
			instance = new SaleManager();
		}
		return instance;
	}

	public Sale saleParking(String operatorName, String plate, long startTime, int minutes) {
		Sale sale = new Sale();
		Operator operator = OperatorManager.getInstance().getOperator(operatorName);
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
		} else if (!plate.matches("[A-S]{3}\\d{4}")) {
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
			sale.setEndDateTime(startTime + (m * 30) * 60000);
			sale.setAmount(m * 1400);
			nextTicketNumber++;
			sale.seteTicketNumber(nextTicketNumber);
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
			nextTicketNumber++;
			credit.seteCreditNumber(this.nextTicketNumber);
			credit.seteTicketNumber(eTicketNumber);
			credit.setSaleDate(new Date().getTime());
		} else {
			credit.setResult(402);
			credit.setMessage("Invalid ticket number");
		}
		return credit;
	}
}
