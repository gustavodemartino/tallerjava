package terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.Ticket;

public class Main {
	public static void main(String[] args) {
		try {
			Agencia agencia = new Agencia("terminal_id");

			agencia.login("admin", "admin");

			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.print(">");
				String entrada = teclado.readLine().replaceAll("\\s+", " ").trim();
				if (entrada.length() == 0) {
					continue;
				}
				String[] comando = entrada.split(" ");
				if (comando[0].equals("logout")) {
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
					if (minutos < 30 || minutos > 600 || minutos % 30 != 0) {
						System.out.println("La cantidad de minutos tiene que ser múltiplo de 30");
						continue;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("k:m");
					try {
						horaInicial = sdf.parse(comando[3]);
					} catch (Exception e) {
						System.out.println("Formato de la hora incial incorrecto. Use HH:MM");
						continue;
					}
					Ticket t = agencia.vender(comando[1], minutos, horaInicial);
					System.out.println("Agencia: " + t.getAgency());
					System.out.println("Hora venta: " + t.getSaleDateTime());
					System.out.println("Ticket número: " + t.getTicketNumber());
					System.out.println("Matrícula: " + t.getPlate());
					System.out.println("Inicio: " + t.getStartDateTime());
					System.out.println("Minutos: " + t.getMinutes());
					System.out.println("Importe: " + t.getAmount());

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
				} else {
					System.out.println("Comando incorrecto");
				}
			}
			agencia.logout();
			agencia.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		System.out.println("Exit program");
	}
}
