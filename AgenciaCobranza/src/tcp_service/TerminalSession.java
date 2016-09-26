package tcp_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import data.ErrorMessage;
import data.Location;
import data.Login;
import data.LoginParameters;
import data.Message;
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
			AuditManager auditor = AuditManager.getInstance();
			String datosExtra = "";
			
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
					try {
						LoginParameters infoParamLogin = (LoginParameters) command.getData();
						datosExtra = infoParamLogin.getUserId() + ";" + infoParamLogin.getTerminalId() + ";";
						
						loginResult = LoginManager.getInstance().terminalLogin(infoParamLogin);
						this.user = loginResult.getUser();
						this.location = loginResult.getLocation();
						response = new Message(Message.LOGIN_OK, this.user);
						
						auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_LOGIN, AuditManager.EVENT_LEVEL_INFO, datosExtra);
						
					} catch (Exception e) {
						response = new Message(Message.LOGIN_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(0, 0, AuditManager.AUDIT_EVENT_LOGIN, AuditManager.EVENT_LEVEL_ERROR, datosExtra);
					}
					writer.println(response.toString() + "\n");
				
				// **********************
				// *** COMMAND_LOGOUT ***
				// **********************	
				} else if (command.getCommand() == Message.COMMAND_LOGOUT) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_LOGOUT, AuditManager.EVENT_LEVEL_INFO, "");
					
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
					try {
						TicketSaleParameters infoParamSale = (TicketSaleParameters) command.getData();
						datosExtra = infoParamSale.getPlate() + ";" + infoParamSale.getMinutes() + ";" + infoParamSale.getStartTime() + ";";
						Ticket ticket = SalesManager.getInstance().saleTicket(infoParamSale);
						response = new Message(Message.TICKET_SALE_OK, ticket);
						
						auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_SALE, AuditManager.EVENT_LEVEL_INFO, datosExtra);
						
					} catch (Exception e) {
						response = new Message(Message.TICKET_SALE_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_SALE, AuditManager.EVENT_LEVEL_ERROR, datosExtra);
					}
					writer.println(response.toString() + "\n");
					
				// *****************************
				// *** COMMAND_TICKET_CANCEL ***
				// *****************************				
				} else if (command.getCommand() == Message.COMMAND_TICKET_CANCEL) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					try {
						TicketCancelParameters infoParamCancel = (TicketCancelParameters) command.getData();
						datosExtra = infoParamCancel.getTicketNumber() + ";";
						SalesManager.getInstance().cancelTicket(infoParamCancel);
						response = new Message(Message.TICKET_CANCEL_OK, null);
						
						auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_ANNULATION, AuditManager.EVENT_LEVEL_INFO, datosExtra);
						
					} catch (Exception e) {
						response = new Message(Message.TICKET_CANCEL_ERROR, new ErrorMessage(e.getMessage()));
						auditor.register(this.user.getId(), this.location.getId(), AuditManager.AUDIT_EVENT_ANNULATION, AuditManager.EVENT_LEVEL_ERROR, datosExtra);
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
