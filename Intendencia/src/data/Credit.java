package data;

public class Credit {
	private int result;
	private String message;
	private long creditDate; // miliseconds
	private String customerName;
	private long autorization;
	private long amount; // Cents
	private long ticket;

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

	public long getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(long creditDate) {
		this.creditDate = creditDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getAutorization() {
		return autorization;
	}

	public void setAutorization(long autorization) {
		this.autorization = autorization;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getTicket() {
		return ticket;
	}

	public void setTicket(long ticket) {
		this.ticket = ticket;
	}

}
