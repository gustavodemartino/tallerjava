package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.Ticket;
import tcp_service.Agencia;

public class Terminal extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5424215328670752425L;
	private JLabel texto1, texto2;
	private JButton botonLogin, botonSalir, botonAlta, botonAnular;
	private String nombreTerminal = "Abitab / Punta Carretas 001";
	private JPanel cartas, cardLogin, cardInicio, cardAlta, cardAnulacion;
	Agencia agencia;

	public Terminal() {
		initUI();
	}

	private void initUI() {

		try {
			agencia = new Agencia(nombreTerminal);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		setTitle("IMM - Tickets Estacionamiento");
		ImageIcon webIcon = new ImageIcon("favicon.png");
		setIconImage(webIcon.getImage());

		setSize(500, 350);
		setLocationRelativeTo(null); // center the window on the screen
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //close the window if we
		// click on the Close button of the titlebar
		setLayout(new BorderLayout(5, 5)); // divide la ventana en 5 partes:
											// centro, arriba, abajo, derecha e
											// izquierda.

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					agencia.close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Header - Barra espaceadora superior
		JPanel topEspacio = new JPanel();
		texto1 = new JLabel(" ");
		topEspacio.add(texto1);
		topEspacio.setBackground(Color.ORANGE);
		add(topEspacio, BorderLayout.PAGE_START);

		// Footer - Info de conexión
		JPanel infoTerminal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		texto1 = new JLabel("Terminal: " + nombreTerminal);
		infoTerminal.add(texto1);
		infoTerminal.setBackground(Color.ORANGE);
		add(infoTerminal, BorderLayout.PAGE_END);

		cartas = new JPanel(new CardLayout());
		cargaCarta1();
		cargaCarta2();
		cargaCarta3();
		cargaCarta4();
		add(cartas, BorderLayout.CENTER);

	}

	private JPanel cargaMenu() {
		// Botonera
		JPanel menu = new JPanel();
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		menu.setPreferredSize(new Dimension(110, 100));

		botonAlta = new JButton("Nuevo ticket");
		botonAlta.setPreferredSize(new Dimension(100, 30));
		botonAlta.setMargin(new Insets(2, 2, 2, 2));
		// botonAlta.addActionListener(this);
		botonAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cartas.getLayout());
				cl.show(cartas, "ALTA");
			}
		});
		menu.add(botonAlta);

		botonAnular = new JButton("Anular ticket");
		botonAnular.setPreferredSize(new Dimension(100, 30));
		botonAnular.setMargin(new Insets(2, 2, 2, 2));
		botonAnular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cartas.getLayout());
				cl.show(cartas, "ANULA");
			}
		});
		menu.add(botonAnular);

		botonSalir = new JButton("Cerrar sesión");
		botonSalir.setPreferredSize(new Dimension(100, 30));
		botonSalir.setMargin(new Insets(2, 2, 2, 2));
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					agencia.logout();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CardLayout cl = (CardLayout) (cartas.getLayout());
				cl.show(cartas, "LOGIN");
			}
		});
		menu.add(botonSalir);

		return menu;

	}

	private void cargaCarta1() {

		cardLogin = new JPanel();
		// cardLogin.setLayout(new BoxLayout(cardLogin, BoxLayout.Y_AXIS));
		cardLogin.setLayout(null);

		JLabel userLabel = new JLabel("Usuario");
		userLabel.setBounds(80, 10, 80, 25);
		cardLogin.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setText("admin");
		userText.setBounds(170, 10, 160, 25);
		cardLogin.add(userText);

		JLabel passwordLabel = new JLabel("Clave");
		passwordLabel.setBounds(80, 40, 80, 25);
		cardLogin.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(170, 40, 160, 25);
		passwordText.setText("admin");
		cardLogin.add(passwordText);

		JLabel mensajeError = new JLabel("");
		mensajeError.setForeground(Color.RED);
		mensajeError.setBounds(80, 120, 300, 25);
		cardLogin.add(mensajeError);

		botonLogin = new JButton("Iniciar sesión");
		botonLogin.setBounds(170, 80, 100, 30);
		botonLogin.setMargin(new Insets(2, 2, 2, 2));
		botonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					mensajeError.setText("");
					String strPassword = new String(passwordText.getPassword());
					agencia.login(userText.getText(), strPassword);

					userText.setText("");
					passwordText.setText("");

					CardLayout cl = (CardLayout) (cartas.getLayout());
					cl.show(cartas, "INICIO");
				} catch (Exception e) {
					mensajeError.setText(e.getMessage());
				}
			}
		});
		cardLogin.add(botonLogin);

		cartas.add(cardLogin, "LOGIN");
	}

	private void cargaCarta2() {

		cardInicio = new JPanel(new BorderLayout(5, 5));

		JPanel contenedor = new JPanel();
		contenedor.setLayout(null);
		texto2 = new JLabel("Bienvenido al sistema de gestión de tickets");
		texto2.setBounds(50, 50, 300, 40);
		contenedor.add(texto2);

		cardInicio.add(contenedor, BorderLayout.CENTER);

		cardInicio.add(cargaMenu(), BorderLayout.LINE_START);

		cartas.add(cardInicio, "INICIO");
	}

	private void cargaCarta3() {

		cardAlta = new JPanel(new BorderLayout(5, 5));

		JPanel contenedor = new JPanel();
		contenedor.setLayout(null);

		JLabel matriculaLabel = new JLabel("Matrícula");
		matriculaLabel.setBounds(50, 30, 80, 25);
		contenedor.add(matriculaLabel);

		JTextField matriculaText = new JTextField(20);
		matriculaText.setBounds(140, 30, 160, 25);
		contenedor.add(matriculaText);

		JLabel minutosLabel = new JLabel("Minutos");
		minutosLabel.setBounds(50, 60, 80, 25);
		contenedor.add(minutosLabel);

		JTextField minutosText = new JTextField(20);
		minutosText.setBounds(140, 60, 160, 25);
		contenedor.add(minutosText);

		JLabel inicioLabel = new JLabel("Inicio");
		inicioLabel.setBounds(50, 90, 80, 25);
		contenedor.add(inicioLabel);

		JTextField inicioText = new JTextField(20);
		inicioText.setBounds(140, 90, 160, 25);
		contenedor.add(inicioText);

		JLabel mensajeError = new JLabel("");
		mensajeError.setBounds(50, 150, 300, 25);
		contenedor.add(mensajeError);
		JLabel mensajeError2 = new JLabel("");
		mensajeError2.setBounds(50, 175, 300, 25);
		contenedor.add(mensajeError2);
		JLabel mensajeError3 = new JLabel("");
		mensajeError3.setBounds(50, 200, 300, 25);
		contenedor.add(mensajeError3);

		JButton botonConfirm = new JButton("Confirmar");
		botonConfirm.setBounds(140, 120, 100, 30);
		botonConfirm.setMargin(new Insets(2, 2, 2, 2));
		botonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					mensajeError.setText("");
					SimpleDateFormat sdf = new SimpleDateFormat("k:m");
					Date date = sdf.parse(inicioText.getText());

					int cantidadMinutos = Integer.parseInt(minutosText.getText());
					Ticket t = agencia.vender(matriculaText.getText(), cantidadMinutos, date);

					matriculaText.setText("");
					minutosText.setText("");
					inicioText.setText("");

					mensajeError.setForeground(Color.BLACK);
					mensajeError.setText(
							"Se ha creado el ticket " + t.getTicketNumber() + " para la matrícula " + t.getPlate());
					mensajeError2.setForeground(Color.BLACK);
					mensajeError2.setText("desde " + t.getStartDateTime() + " hasta " + t.getEndDateTime());
					mensajeError3.setForeground(Color.BLACK);
					mensajeError3.setText("Importe a cobrar = $" + t.getAmount());
					// CardLayout cl = (CardLayout)(cartas.getLayout());
					// cl.show(cartas,"INICIO");
				} catch (Exception e) {
					mensajeError.setForeground(Color.RED);
					mensajeError.setText(e.getMessage());
				}
			}
		});
		contenedor.add(botonConfirm);

		cardAlta.add(contenedor, BorderLayout.CENTER);

		cardAlta.add(cargaMenu(), BorderLayout.LINE_START);

		cartas.add(cardAlta, "ALTA");
	}

	private void cargaCarta4() {

		cardAnulacion = new JPanel(new BorderLayout(5, 5));

		texto2 = new JLabel("Función de anulación");
		texto2.setAlignmentX(CENTER_ALIGNMENT);
		texto2.setAlignmentY(CENTER_ALIGNMENT);
		cardAnulacion.add(texto2, BorderLayout.CENTER);

		cardAnulacion.add(cargaMenu(), BorderLayout.LINE_START);

		cartas.add(cardAnulacion, "ANULA");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		CardLayout cl = (CardLayout) (cartas.getLayout());

		if (e.getSource() == botonLogin) {
			cl.show(cartas, "INICIO");
		}

		if (e.getSource() == botonSalir) {
			cl.show(cartas, "LOGIN");

			/*
			 * try{ Thread.sleep(500); System.exit(0); } catch(Exception excep)
			 * { System.exit(0); }
			 */

		}

		if (e.getSource() == botonAlta) {
			cl.show(cartas, "ALTA");

			/*
			 * texto2.setText("Desde aqui podrá vender nuevos tickets"); try {
			 * Agencia agencia = new Agencia("terminal_id");
			 * agencia.login("admin", "admin"); Date hoy = new Date(); Ticket t
			 * = agencia.vender("Prueba1",30,hoy); String infoString =
			 * "Agencia: " + t.getAgency(); infoString += "Hora venta: " +
			 * t.getSaleDateTime(); infoString += "Ticket número: " +
			 * t.getTicketNumber(); infoString += "Matrícula: " + t.getPlate();
			 * infoString += "Inicio: " + t.getStartDateTime(); infoString +=
			 * "Minutos: " + t.getMinutes(); infoString += "Importe: " +
			 * t.getAmount(); texto2.setText(infoString); } catch (Exception e)
			 * { // TODO Auto-generated catch block texto2.setText("Error!"); }
			 */

		}

		if (e.getSource() == botonAnular) {
			cl.show(cartas, "ANULA");
			// texto2.setText("Desde aqui podrá anular tickets vendidos");
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Terminal ventana = new Terminal();
			ventana.setVisible(true);
		});
	}

}
