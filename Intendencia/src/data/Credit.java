package data;

public class Credit {
	private int result;
	private String message;
	private long creditDate; // miliseconds
	private long eCreditNumber;
	private String customerName;
	private long amount; // Cents
	private long eTicketNumber;

	public long getSaleDate() {
		return creditDate;
	}

	public void setSaleDate(long creditDate) {
		this.creditDate = creditDate;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
