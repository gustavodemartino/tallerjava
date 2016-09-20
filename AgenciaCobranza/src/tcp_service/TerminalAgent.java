package tcp_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import data.ErrorMessage;
import data.LoginParameters;
import data.Message;
import data.Ticket;
import data.TicketCancelParameters;
import data.TicketSaleParameters;
import data.User;
import model.Constants;
import model.SalesManager;
import model.UserManager;


public class TerminalAgent implements Runnable {
	private Socket socket;
	private int connection;
	private PrintWriter writer;
	private User user = null;

	public TerminalAgent(Socket socket, int connectionNum) throws IOException {
		this.socket = socket;
		this.connection = connectionNum;
		this.writer = new PrintWriter(this.socket.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
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

				if (command.getCommand() == Message.COMMAND_LOGIN) {
					if (this.user != null) {
						throw new Exception(Constants.ERROR_MSG_REPEATED_LOGIN);
					}
					try {
						this.user = UserManager.getInstance().login((LoginParameters) command.getData());
						response = new Message(Message.LOGIN_OK, this.user);
					} catch (Exception e) {
						response = new Message(Message.LOGIN_ERROR, new ErrorMessage(e.getMessage()));
					}
					writer.println(response.toString() + "\n");
					
				} else if (command.getCommand() == Message.COMMAND_LOGOUT) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					this.user = null;
					response = new Message(Message.LOGOUT_OK, null);
					writer.println(response.toString() + "\n");

				} else if (command.getCommand() == Message.COMMAND_TICKET_SALE) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					try {
						Ticket ticket = SalesManager.getInstance().saleTicket((TicketSaleParameters) command.getData());
						response = new Message(Message.TICKET_SALE_OK, ticket);
					} catch (Exception e) {
						response = new Message(Message.TICKET_SALE_ERROR, new ErrorMessage(e.getMessage()));
					}
					writer.println(response.toString() + "\n");
					
				} else if (command.getCommand() == Message.COMMAND_TICKET_CANCEL) {
					if (this.user == null) {
						throw new Exception(Constants.ERROR_MSG_LOGIN_REQUIRED);
					}
					try {
						SalesManager.getInstance().cancelTicket((TicketCancelParameters) command.getData());
						response = new Message(Message.TICKET_CANCEL_OK, null);
					} catch (Exception e) {
						response = new Message(Message.TICKET_CANCEL_ERROR, new ErrorMessage(e.getMessage()));
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
