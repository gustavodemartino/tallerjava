package tcp_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

import data.AuditEvent;
import data.ErrorMessage;
import data.Location;
import data.Login;
import data.LoginParameters;
import data.Message;
import data.Refound;
import data.Ticket;
import data.TicketCancelParameters;
import data.TicketSaleParameters;
import data.User;
import model.AuditManager;
import model.Constants;
import model.LoginManager;
import model.SalesManager;

public class TerminalSession implements Runnable {
	private Socket socket;
	private int connection;
	private PrintWriter writer;
	private User user = null;
	private Location location;

	public TerminalSession(Socket socket, int connectionNum) throws IOException {
		this.socket = socket;
		this.connection = connectionNum;
		this.writer = new PrintWriter(this.socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
			SimpleDateFormat sdfh = new SimpleDateFormat("kk:mm");
			AuditManager auditor = AuditManager.getInstance();
			StringBuffer sb = new StringBuffer();
			BufferedReader r = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			boolean scan = true;
			while (scan) {
				String datos;
				while (true) {
					datos = r.readLine().trim();
					if (datos.equals("")) {
						if (sb.length() == 0) {
							scan = false;
						}
						break;
					} else {
						sb.append(datos);
					}
				}
				if (sb.length() == 0) {
					break;
				}
				Message command = new Message(sb.toString());
				sb.delete(0, sb.length());
				Message response = null;

				// *********************
				// *** COMMAND_LOGIN ***
				// *********************
				if (command.getCommand() == Message.COMMAND_LOGIN) {
					if (this.user != null) {
						throw new Exception(Constants.ERROR_MSG_REPEATED_LOGIN);
					}
					Login loginResult;
					LoginParameters param = (LoginParameters) command.getData();
					try {
						loginResult = LoginManager.getInstance().login(param);
						this.user = loginResult.getUser();
						this.location = loginResult.getLocation();
						response = new Message(Message.LOGIN_OK, this.user);

						auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_LOGIN_OK, (String) null);

					} catch (Exception e) {
						response = new Message(Message.LOGIN_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(param.getUserId(), param.getLocationName(),
								AuditEvent.AUDIT_EVENT_LOGIN_ERROR);
					}
					writer.println(response.toString() + "\n");

					// **********************
					// *** COMMAND_LOGOUT ***
					// **********************
				} else if (command.getCommand() == Message.COMMAND_LOGOUT) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_LOGOUT_OK, (String) null);

					this.user = null;
					response = new Message(Message.LOGOUT_OK, null);
					writer.println(response.toString() + "\n");

					// ***************************
					// *** COMMAND_TICKET_SALE ***
					// ***************************
				} else if (command.getCommand() == Message.COMMAND_TICKET_SALE) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					TicketSaleParameters param = (TicketSaleParameters) command.getData();
					try {

						Ticket ticket = SalesManager.getInstance().saleTicket(param);
						response = new Message(Message.TICKET_SALE_OK, ticket);

						auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_SALE_OK,
								"Hora: " + sdf.format(ticket.getSaleDateTime()) + "\nMatrícula: "
										+ ticket.getPlate() + "\nInicio: " + sdfh.format(param.getStartTime())
										+ "\nMinutos solicitados: " + param.getMinutes() + "\nInicio autorizado: "
										+ sdfh.format(ticket.getStartDateTime()) + "\nFinal autorizado: " + sdfh.format(ticket.getEndDateTime())
										+ "\nTicket: " + ticket.getTicketNumber() + "\nImporte: "
										+ ticket.getFloatAmount());

					} catch (Exception e) {
						response = new Message(Message.TICKET_SALE_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_SALE_ERROR,
								"Matrícula: " + param.getPlate() + "\nInicio: "
										+ sdf.format(param.getStartTime()) + "\nMinutos: "
										+ param.getMinutes() + "\nError: " + e.getMessage());
					}
					writer.println(response.toString() + "\n");

					// *****************************
					// *** COMMAND_TICKET_CANCEL ***
					// *****************************
				} else if (command.getCommand() == Message.COMMAND_TICKET_CANCEL) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					TicketCancelParameters param = (TicketCancelParameters) command.getData();
					try {
						Refound refound = SalesManager.getInstance().cancelTicket(param);
						response = new Message(Message.TICKET_CANCEL_OK, refound);
						auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_ANNULATION_OK,
								"Hora: " + sdf.format(refound.getDateTime()) + "\nTicket: "
										+ refound.getTicket() + "\nAutorización: " + refound.getAuthorization()
										+ "\nImporte: " + refound.getFloatAmount());

					} catch (Exception e) {
						response = new Message(Message.TICKET_CANCEL_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(this.user, this.location, AuditEvent.AUDIT_EVENT_ANNULATION_ERROR,
								"Ticket: " + param.getTicketNumber()+"\nError: "+ e.getMessage());
					}
					writer.println(response.toString() + "\n");
				}
			}
			System.out.println("#" + connection + " Closed by client");
		} catch (Exception e) {
			System.out.println("#" + connection + " " + e.getMessage());
		}
		try {
			System.out.println("#" + connection + " Closing connection");
			socket.close();
		} catch (Exception e) {
			System.out.println("#" + connection + " Exception closing");
		}
	}
}
