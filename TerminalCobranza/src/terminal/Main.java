package terminal;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
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

	public static void main(String[] args) {
		Agencia agencia;
		boolean open = true;
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (open) {
				User user = null;
				String userName = "";
				String password = "";
				while (userName.equals("")) {
					System.out.print("login: ");
					userName = teclado.readLine().trim();
				}
				if (userName.equals("exit")){
					break;
				}
				System.out.print("password: ");
				password = readPassword();
				System.out.println("Conecting to server...");
				try {
					agencia = new Agencia("terminal_id");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				try {
					user = agencia.login(userName, password);
				} catch (Exception e) {
					agencia.close();
					System.out.println(e.getMessage());
					continue;
				}
				System.out.println("Login as: " + user.getUserName());
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
							System.out.println("Fin: " + t.getEndDateTime());
							System.out.printf("Importe: %4.2f\n", t.getFloatAmount());
						} catch (IOException e) {
							System.out.println(e.getMessage());
							break;
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
						} catch (IOException e) {
							System.out.println(e.getMessage());
							break;
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
				agencia.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exit program");
	}
}
