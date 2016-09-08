package services;

import java.util.Date;

public class Sale {
	private Date saleDate;
	private long eTicketNumber;
	private String customerName;
	private long amount; // Cents

	private String plate;
	private Date startDateTime;
	private int minutes;

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public long geteTicketNumber() {
		return eTicketNumber;
	}

	public void seteTicketNumber(long eTicketNumber) {
		this.eTicketNumber = eTicketNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

}
