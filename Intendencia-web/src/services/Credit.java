package services;

public class Credit {
	private long saleDate; // Miliseconds
	private long eCreditNumber;
	private String customerName;
	private long amount; // Cents
	private long eTicketNumber;

	public long getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(long saleDate) {
		this.saleDate = saleDate;
	}

	public long geteCreditNumber() {
		return eCreditNumber;
	}

	public void seteCreditNumber(long eCreditNumber) {
		this.eCreditNumber = eCreditNumber;
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

	public long geteTicketNumber() {
		return eTicketNumber;
	}

	public void seteTicketNumber(long eTicketNumber) {
		this.eTicketNumber = eTicketNumber;
	}

}
