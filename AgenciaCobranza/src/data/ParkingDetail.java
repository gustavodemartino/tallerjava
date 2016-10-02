package data;

import java.util.Date;

public class ParkingDetail {

	private Date saleDateTime;
	private long saleTicket;
	private String plate;
	private Date parkingStart;
	private int duration;
	private Date cancelationDateTime;
	private long amount;
	private long cancelationNumber;
	private long credit;
	private boolean isCanceled;
	
	public ParkingDetail(){
		this.isCanceled = false;
	}

	public void setSaleDateTime(Date saleDateTime) {
		this.saleDateTime = saleDateTime;
	}

	public void setSaleTicket(long saleTicket) {
		this.saleTicket = saleTicket;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setStartDateTime(Date parkingStart) {
		this.parkingStart = parkingStart;
	}

	public void setDuration(int minutes) {
		this.duration = minutes;
	}

	public void setCancelationDateTime(Date cancelationDateTime) {
		this.isCanceled = true;
		this.cancelationDateTime = cancelationDateTime;
	}

	public void setAmount(long cents) {
		this.amount = cents;
	}

	public void setCancelationNumber(long cancelationNumber) {
		this.cancelationNumber = cancelationNumber;
	}

	public void setCredit(long cents) {
		this.credit = cents;
	}

	public void setParkingStart(Date parkingStart) {
		this.parkingStart = parkingStart;
	}
	
	public Date getParkingStart() {
		return parkingStart;
	}

	public Date getSaleDateTime() {
		return saleDateTime;
	}

	public long getSaleTicket() {
		return saleTicket;
	}

	public String getPlate() {
		return plate;
	}

	public int getDuration() {
		return duration;
	}

	public boolean getIsCanceled(){
		return this.isCanceled;
	}
	public Date getCancelationDateTime() {
		return cancelationDateTime;
	}

	public float getAmount() {
		return ((float)amount)/100;
	}

	public long getCancelationNumber() {
		return cancelationNumber;
	}

	public float getCredit() {
		return ((float)credit)/100;
	}

}
