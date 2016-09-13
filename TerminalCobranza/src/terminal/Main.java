package terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import data.Ticket;
import tcp_service.Agencia;

public class Main {
	public static void main(String[] args) {
		try {
			Agencia agencia = new Agencia("terminal_id");

			System.out.println("Login as: " + agencia.login("admin", "admin").getUserName());

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
						System.out.println("Cantidad de minutos inv�lida");
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
						System.out.println("Ticket n�mero: " + t.getTicketNumber());
						System.out.println("Matr�cula: " + t.getPlate());
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
						System.out.println("Formato del n�mero incorrecto");
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
