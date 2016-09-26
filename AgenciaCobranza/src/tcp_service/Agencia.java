package tcp_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import data.ErrorMessage;
import data.LoginParameters;
import data.Message;
import data.Ticket;
import data.TicketCancelParameters;
import data.TicketSaleParameters;
import data.User;

public class Agencia {
	private static final int port = 1218;
	private String terminalId;
	private Socket socket = null;
	private String serverName = "localhost";
	private BufferedReader entrada;
	private PrintWriter salida;
	private User user = null;

	public Agencia(String terminalId) throws Exception {
		this.terminalId = terminalId;
		this.socket = new Socket(serverName, port);
		this.entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.salida = new PrintWriter(socket.getOutputStream(), true);
	}

	public User login(String userName, String password) throws Exception {
		LoginParameters parameters = new LoginParameters(userName, password, this.terminalId);
		Message command = new Message(Message.COMMAND_LOGIN, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.LOGIN_ERROR) {
			String message = ((ErrorMessage) respuesta.getData()).getMessage();
			throw new Exception(message);
		}
		this.user = (User)respuesta.getData();		
		return this.user  ;
	}

	public void logout() throws Exception {
		Message command = new Message(Message.COMMAND_LOGOUT, null);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() != Message.LOGOUT_OK) {
			String message = ((ErrorMessage) respuesta.getData()).getMessage();
			throw new Exception(message);
		}
		System.out.println("Logout ok");
	}

	public void close() throws IOException {
		this.salida.println();
		this.salida.close();
		this.salida = null;
		this.entrada.close();
		this.entrada = null;
		this.socket.close();
		this.socket = null;
	}

	public Ticket vender(String matricula, int minutos, Date horaInicial) throws IOException, Exception  {
		if (matricula == null || !matricula.matches("\\w{3}\\d{4}")){
			throw new Exception("Matrícula inválida");
		}
		if (minutos < 1 || minutos > 600){
			throw new Exception("Cantidad de minutos no válida");
		}
		if (horaInicial == null ){
			throw new Exception("Hora de inicio inválida");			
		}
		TicketSaleParameters parameters = new TicketSaleParameters(matricula, minutos, horaInicial);
		Message command = new Message(Message.COMMAND_TICKET_SALE, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.TICKET_SALE_ERROR) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
		return (Ticket) respuesta.getData();
	}

	public void anular(long ticket) throws IOException, Exception {
		TicketCancelParameters parameters = new TicketCancelParameters(ticket);
		Message command = new Message(Message.COMMAND_TICKET_CANCEL, parameters);
		salida.println(command.toString() + "\n");
		Message respuesta = procesarRespuesta();
		if (respuesta.getCommand() == Message.TICKET_CANCEL_ERROR) {
			throw new Exception(((ErrorMessage) respuesta.getData()).getMessage());
		}
	}

	private Message procesarRespuesta() throws IOException, Exception  {
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
		// System.out.println("<" + sb.toString() + ">");
		sb.delete(0, sb.length());
		return result;
	}

}
