package consola;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.Refound;
import data.Ticket;
import data.User;
import gui.Terminal;
import tcp_service.Agencia;

public class Main {

	private static String terminal_id = "terminal_id";
	private static String help = "Use:\n\t-h | -- help para obtener ayuda\n\t--textmode Usar consola en modo texto\n\t-t <terminal_id> para indicar el id de la terminal\n\t--clearpassword para ingresar la clave en texto claro (para procesamiento batch)";

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

	private static void vender(Agencia agencia, String[] comando) {
		if (comando.length < 3) {
			System.out.println("Cantidad incorrecta de parametros");
			return;
		}
		int minutos = 0;
		Date horaInicial;
		try {
			minutos = Integer.parseInt(comando[2]);
		} catch (Exception e) {
			System.out.println("Cantidad de minutos inválida");
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("k:m");
		if (comando.length == 3) {
			horaInicial = new Date(0);
		} else {
			try {
				horaInicial = sdf.parse(comando[3]);
			} catch (Exception e) {
				System.out.println("Formato de la hora incial incorrecto. Use HH:MM");
				return;
			}
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
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void anular(Agencia agencia, String[] comando) {
		if (comando.length != 2) {
			System.out.println("Cantidad incorrecta de parametros");
			return;
		}
		long ticket = 0;
		try {
			ticket = Integer.parseInt(comando[1]);
		} catch (Exception e) {
			System.out.println("Formato del número incorrecto");
			return;
		}
		try {
			Refound refound = agencia.anular(ticket);
			System.out.println("Ticket cancelado correctamente");
			System.out.println("Fecha y hora: " + refound.getDateTime());
			System.out.println("Autorización: " + refound.getAuthorization());
			System.out.println("Ticket anulado: " + refound.getTicket());
			System.out.println("Crédito: " + refound.getFloatAmount());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		boolean useClearPassword = false;
		boolean useGui = true;
		int index = 0;
		while (args.length > index) {
			if (args.length > (index) && (args[index].equals("-h") || args[index].equals("--help"))) {
				System.out.println(help);
				return;
			} else if (args.length > index && args[index].equals("-t")) {
				index++;
				if (args.length > index) {
					terminal_id = args[index];
					index++;
				} else {
					System.err.println("Falta parámetro para -t. Use el parámetro -h para obtener ayuda.");
					return;
				}
			} else if (args.length > index && args[index].equals("--clearpassword")) {
				useClearPassword = true;
				useGui = false;
				index++;
			} else if (args.length > index && args[index].equals("--textmode")) {
				useGui = false;
				index++;
			} else {
				System.err
						.println("Parámetro inválido \"" + args[index] + "\". Use el parámetro -h para obtener ayuda.");
				return;
			}
		}
		if (useGui) {
			EventQueue.invokeLater(() -> {
				Terminal ventana = new Terminal();
				ventana.setVisible(true);
			});

		} else {
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
					if (userName.equals("exit")) {
						break;
					}
					System.out.print("password: ");
					if (useClearPassword) {
						password = teclado.readLine();
					} else {
						password = readPassword();
					}
					System.out.println("Conecting to server...");
					try {
						agencia = new Agencia(terminal_id);
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
					System.out.println("Loged as: " + user.getName());
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
							vender(agencia, comando);
						} else if (comando[0].equals("anular")) {
							anular(agencia, comando);
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
}
