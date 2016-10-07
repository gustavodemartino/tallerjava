package data;

import java.util.Date;

public class SaleDetail {
	private Operator operator;
	private Date saleTimeStamp;
	private String plate;
	private Date startTimeStamp;
	private Date endTimeStamp;
	private long ticket;
	private long amount;
	private Date cancelationTimeStam;
	private long authorization;
	private long creditAmount;

	public SaleDetail(Operator operator, Date saleTimeStamp, String plate, Date startTimeStamp, Date endTimeStamp,
			long ticket, long amount) {
		this.operator = operator;
		this.saleTimeStamp = saleTimeStamp;
		this.plate = plate;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.ticket = ticket;
		this.amount = amount;
	}

	public void setCancelation(Date cancelationTimeStam, long authorization, long creditAmount) {
		this.cancelationTimeStam = cancelationTimeStam;
		this.authorization = authorization;
		this.creditAmount = creditAmount;
	}

	public Operator getOperator() {
		return operator;
	}

	public Date getSaleTimeStamp() {
		return saleTimeStamp;
	}

	public String getPlate() {
		return plate;
	}

	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	public Date getEndTimeStamp() {
		return endTimeStamp;
	}

	public long getTicket() {
		return ticket;
	}

	public long getAmount() {
		return amount;
	}

	public Date getCancelationTimeStam() {
		return cancelationTimeStam;
	}

	public long getAuthorization() {
		return authorization;
	}

	public long getCreditAmount() {
		return creditAmount;
	}

}
