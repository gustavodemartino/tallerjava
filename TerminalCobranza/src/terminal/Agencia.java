package terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import tallerjava.data.Message;
import tallerjava.data.ErrorMessage;
import tallerjava.data.LoginParameters;
import tallerjava.data.Ticket;
import tallerjava.data.TicketCancelParameters;
import tallerjava.data.TicketSaleParameters;

public class Agencia {
	private String terminalId;
	private Socket socket = null;
	private String serverName = "localhost";
	private int port = 1218;
	private BufferedReader entrada;
	private PrintWriter salida;
	
	public Agencia(String terminalId) throws Exception {
		this.terminalId = terminalId;
		this.socket = new Socket(serverName, port);
		this.entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.salida = new PrintWriter(socket.getOutputStream(), true);
	}

	private Message procesarRespuesta() throws Exception {
		StringBuffer sb = new StringBuffer();
		String datos;
		while (true) {
			datos = entrada.readLine().trim();
			if (datos.equals("")) {
				break;
			} else {
				sb.append(datos);
			}
		}
		Message result = new Message(sb.toString());
		// TODO Debug
		System.out.println("<"+sb.toString()+">");
		sb.delete(0, sb.length());
		return result;
	}

	public void login(String userName, String password) throws Exception {
		LoginParameters parameters = new LoginParameters(userName, password, this.terminalId);
		Message command = new Message(Message.COMMAND_LOGIN, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.LOGIN_ERROR) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
	}

	public void logout() throws Exception {
		Message command = new Message(Message.COMMAND_LOGOUT, null);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() != Message.LOGOUT_OK) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
		System.out.println("Logout ok");
	}

	public void close() throws IOException {
		this.salida.close();
		this.salida = null;
		this.entrada.close();
		this.entrada = null;
		this.socket.close();
		this.socket = null;
	}

	public Ticket vender(String matricula, int minutos, Date horaInicial) throws Exception {
		TicketSaleParameters parameters = new TicketSaleParameters(matricula, minutos, horaInicial);
		Message command = new Message(Message.COMMAND_TICKET_SALE, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.TICKET_SALE_ERROR) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
		return (Ticket) respuesta.getData();
	}

	public void anular(long ticket) throws Exception {
		TicketCancelParameters parameters = new TicketCancelParameters(ticket);
		Message command = new Message(Message.COMMAND_TICKET_CANCEL, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.TICKET_CANCEL_ERROR) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
	}
}
