package terminal;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.Ticket;
import data.User;
import tcp_service.Agencia;

public class Main {

	private static String readPassword() throws Exception {
		String result = null;
		Console console = System.console();
		if (console == null) {
			System.out.print("(Cannot get console. Using clear input): ");
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			result = teclado.readLine();
		} else {
			char passwordArray[] = console.readPassword();
			result = new String(passwordArray);
		}
		return result;
	}

	private static User login(Agencia agencia) throws Exception {
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		User user = null;
		String userName = "";
		while (true) {
			while (userName.equals("")) {
				System.out.print("login: ");
				userName = teclado.readLine().trim();
			}
			System.out.print("password: ");
			String password = readPassword();
			try {
				System.out.println("Conecting to server...");
				user = agencia.login(userName, password);
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				userName = "";
			}
		}
		return user;
	}

	public static void main(String[] args) {
		try {
			Agencia agencia = new Agencia("terminal_id");
			boolean open = true;
			while (open) {
				User user = login(agencia);
				System.out.println("Login as: " + user.getUserName());
				BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					System.out.print(">");
					String entrada = teclado.readLine().replaceAll("\\s+", " ").trim();
					if (entrada.length() == 0) {
						continue;
					}
					String[] comando = entrada.split(" ");
					if (comando[0].equals("logout")) {
						agencia.logout();
						break;
					} else if (comando[0].equals("vender")) {
						if (comando.length != 4) {
							System.out.println("Cantidad incorrecta de parametros");
							continue;
						}
						int minutos = 0;
						Date horaInicial;
						try {
							minutos = Integer.parseInt(comando[2]);
						} catch (Exception e) {
							System.out.println("Cantidad de minutos inválida");
							continue;
						}
						SimpleDateFormat sdf = new SimpleDateFormat("k:m");
						try {
							horaInicial = sdf.parse(comando[3]);
						} catch (Exception e) {
							System.out.println("Formato de la hora incial incorrecto. Use HH:MM");
							continue;
						}
						try {
							Ticket t = agencia.vender(comando[1], minutos, horaInicial);
							System.out.println("Agencia: " + t.getAgency());
							System.out.println("Hora venta: " + t.getSaleDateTime());
							System.out.println("Ticket número: " + t.getTicketNumber());
							System.out.println("Matrícula: " + t.getPlate());
							System.out.println("Inicio: " + t.getStartDateTime());
							System.out.println("Minutos: " + t.getMinutes());
							System.out.println("Importe: " + t.getAmount());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}

					} else if (comando[0].equals("anular")) {
						if (comando.length != 2) {
							System.out.println("Cantidad incorrecta de parametros");
							continue;
						}
						long ticket = 0;
						try {
							ticket = Integer.parseInt(comando[1]);
						} catch (Exception e) {
							System.out.println("Formato del número incorrecto");
							continue;
						}
						try {
							agencia.anular(ticket);
							System.out.println("Ticket cancelado correctamente");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if (comando[0].equals("exit")) {
						open = false;
						break;
					} else {
						System.out.println("Comando incorrecto");
					}
				}
			}
			agencia.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exit program");
	}
}
