package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import data.Refound;
import data.Ticket;
import data.User;
import tcp_service.Agencia;

public class Terminal extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5424215328670752425L;
	private JLabel infoUsuario, infoTerminal;
	private JButton botonLogin, botonSalir, botonAlta, botonAnular;
	private JPanel cartas, cardLogin, cardInicio, cardAlta, cardAnulacion;
	Agencia agencia;
	User usuarioDatos;
	String ubicacionNombre;

	public Terminal() {
		initUI();
	}

	private void initUI() {

		setTitle("IMM - Tickets Estacionamiento");
		ImageIcon webIcon = new ImageIcon("favicon.png");
		setIconImage(webIcon.getImage());

		setSize(600, 450);
		setLocationRelativeTo(null); // center the window on the screen
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //close the window if we
		// click on the Close button of the titlebar
		setLayout(new BorderLayout(5, 5)); // divide la ventana en 5 partes:
											// centro, arriba, abajo, derecha e
											// izquierda.

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if (agencia != null)
						agencia.close();
					System.exit(0);
				} catch (IOException e1) {
					System.exit(0);
				}
			}
		});

		// Header - Barra espaceadora superior
		JPanel topEspacio = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		infoUsuario = new JLabel("(Sin usuario conectado)");
		infoUsuario.setForeground(Color.WHITE);
		topEspacio.add(infoUsuario);
		topEspacio.setBackground(Color.GRAY);
		add(topEspacio, BorderLayout.PAGE_START);

		// Footer - Info de conexión
		JPanel botomEspacio = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		infoTerminal = new JLabel();
		if (ubicacionNombre == null || ubicacionNombre.isEmpty())
			ubicacionNombre = "(desconectado)";
		infoTerminal.setText("Terminal: " + ubicacionNombre);
		infoTerminal.setForeground(Color.WHITE);
		botomEspacio.add(infoTerminal);
		botomEspacio.setBackground(Color.GRAY);
		add(botomEspacio, BorderLayout.PAGE_END);

		cartas = new JPanel(new CardLayout());
		try {
			cargaCarta1();
			cargaCarta2();
			cargaCarta3();
			cargaCarta4();
			add(cartas, BorderLayout.CENTER);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(cartas, e1.getMessage(), "Error de aplicación", JOptionPane.PLAIN_MESSAGE);
			// e1.printStackTrace();
		}
	}

	private JPanel cargaMenu() {
		// Botonera
		JPanel menu = new JPanel();
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		menu.setPreferredSize(new Dimension(150, 100));

		botonAlta = new JButton("Nuevo ticket");
		botonAlta.setBackground(Color.LIGHT_GRAY);
		botonAlta.setPreferredSize(new Dimension(140, 40));
		// botonAlta.setMargin(new Insets(2, 2, 2, 2));
		// botonAlta.addActionListener(this);
		botonAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cartas.getLayout());
				cl.show(cartas, "ALTA");
			}
		});
		menu.add(botonAlta);

		botonAnular = new JButton("Anular ticket");
		botonAnular.setBackground(Color.LIGHT_GRAY);
		botonAnular.setPreferredSize(new Dimension(140, 40));
		// botonAnular.setMargin(new Insets(2, 2, 2, 2));
		botonAnular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cartas.getLayout());
				cl.show(cartas, "ANULA");
			}
		});
		menu.add(botonAnular);

		botonSalir = new JButton("Cerrar sesión");
		botonSalir.setBackground(Color.LIGHT_GRAY);
		botonSalir.setPreferredSize(new Dimension(140, 40));
		// botonSalir.setMargin(new Insets(2, 2, 2, 2));
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cartas.getLayout());
				try {
					agencia.logout();
					infoUsuario.setText("(Sin usuario conectado)");
					cl.show(cartas, "LOGIN");
				} catch (Exception e1) {
					cl.show(cartas, "LOGIN");
					// e1.printStackTrace();
				}
			}
		});
		menu.add(botonSalir);

		return menu;

	}

	private void cargaCarta1() throws Exception {

		cardLogin = new JPanel();
		// cardLogin.setLayout(new BoxLayout(cardLogin, BoxLayout.Y_AXIS));
		cardLogin.setLayout(null);

		JLabel userLabel = new JLabel("Usuario");
		userLabel.setBounds(130, 60, 80, 25);
		cardLogin.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setText("user");
		userText.setBounds(220, 60, 160, 25);
		cardLogin.add(userText);

		JLabel passwordLabel = new JLabel("Clave");
		passwordLabel.setBounds(130, 90, 80, 25);
		cardLogin.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(220, 90, 160, 25);
		passwordText.setText("user");
		cardLogin.add(passwordText);

		JLabel terminalLabel = new JLabel("Terminal");
		terminalLabel.setBounds(130, 120, 80, 25);
		cardLogin.add(terminalLabel);

		JTextField terminalText = new JTextField(20);
		terminalText.setText("terminal_1");
		terminalText.setBounds(220, 120, 160, 25);
		cardLogin.add(terminalText);

		JLabel mensajeError = new JLabel("");

		botonLogin = new JButton("Iniciar sesión");
		botonLogin.setBounds(220, 170, 100, 30);
		botonLogin.setMargin(new Insets(2, 2, 2, 2));

		mensajeError.setForeground(Color.RED);
		mensajeError.setBounds(130, 220, 300, 100);
		mensajeError.setText("");
		cardLogin.add(mensajeError);

		botonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {

					agencia = new Agencia(terminalText.getText());

					mensajeError.setText("");
					String strPassword = new String(passwordText.getPassword());
					usuarioDatos = agencia.login(userText.getText(), strPassword);

					infoUsuario.setText("Bienvenido " + usuarioDatos.getName());
					infoTerminal.setText("Terminal: " + agencia.getTerminalId());

					CardLayout cl = (CardLayout) (cartas.getLayout());
					cl.show(cartas, "INICIO");
				} catch (Exception e) {
					mensajeError.setText(e.getMessage());
					// e.printStackTrace();
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
		JLabel texto2 = new JLabel("Sistema de gestión de parking - IMM");
		texto2.setBounds(100, 50, 300, 40);
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

		SpinnerModel hora = new SpinnerNumberModel(0, // initial value
				0, // min
				23, // max
				1); // step
		SpinnerModel minutos = new SpinnerNumberModel(0, // initial value
				0, // min
				59, // max
				1); // step

		JSpinner spinnerHora = new JSpinner(hora);
		JSpinner spinnerMinutos = new JSpinner(minutos);

		JLabel inicioLabel = new JLabel("Inicio");
		inicioLabel.setBounds(50, 90, 80, 25);
		contenedor.add(inicioLabel);

		// JTextField inicioText = new JTextField(20);
		// inicioText.setBounds(140, 90, 160, 25);
		// contenedor.add(inicioText);

		Date hoy = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new
																// calendar
																// instance
		calendar.setTime(hoy); // assigns calendar to given date

		spinnerHora.setBounds(140, 90, 50, 25);
		spinnerHora.setValue(calendar.get(Calendar.HOUR_OF_DAY));
		contenedor.add(spinnerHora);

		JLabel column = new JLabel(" : ");
		column.setBounds(190, 90, 10, 25);
		contenedor.add(column);

		spinnerMinutos.setBounds(200, 90, 50, 25);
		int minutosCalendar = calendar.get(Calendar.MINUTE) + 5;
		spinnerMinutos.setValue(minutosCalendar);
		contenedor.add(spinnerMinutos);

		JLabel mensajeLinea = new JLabel("");
		mensajeLinea.setBounds(50, 150, 300, 100);
		mensajeLinea.setText("");
		mensajeLinea.setForeground(Color.RED);
		contenedor.add(mensajeLinea);

		JButton botonConfirm = new JButton("Confirmar");
		botonConfirm.setBounds(140, 120, 100, 30);
		botonConfirm.setMargin(new Insets(2, 2, 2, 2));
		botonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("k:m");
					Date date = sdf.parse(spinnerHora.getValue() + ":" + spinnerMinutos.getValue());

					int cantidadMinutos = Integer.parseInt(minutosText.getText());
					Ticket t = agencia.vender(matriculaText.getText(), cantidadMinutos, date);

					matriculaText.setText("");
					minutosText.setText("");
					spinnerHora.setValue(0);
					spinnerMinutos.setValue(0);

					Date horaInicio = t.getStartDateTime();
					calendar.setTime(horaInicio); // assigns calendar to given
													// date
					String horaIniStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

					Date horaFin = t.getEndDateTime();
					calendar.setTime(horaFin); // assigns calendar to given date
					String horaFinStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

					String confirmacion;
					confirmacion = "Ticket: " + t.getTicketNumber() + "\n";
					confirmacion += "Matrícula: " + t.getPlate() + "\n";
					confirmacion += "Válido desde " + horaIniStr + " hasta " + horaFinStr + "\n";
					confirmacion += "\n" + "Importe a cobrar = $" + t.getFloatAmount() + "\n";
					confirmacion += " " + "\n";

					// custom title, no icon
					JOptionPane.showMessageDialog(cardAlta, confirmacion, "Confirmación de venta",
							JOptionPane.PLAIN_MESSAGE);

					// CardLayout cl = (CardLayout)(cartas.getLayout());
					// cl.show(cartas,"INICIO");
				} catch (Exception e) {
					mensajeLinea.setText(e.getMessage());
					// e.printStackTrace();
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

		JPanel contenedor = new JPanel();
		contenedor.setLayout(null);

		JLabel ticketLabel = new JLabel("Ticket");
		ticketLabel.setBounds(50, 30, 80, 25);
		contenedor.add(ticketLabel);

		JTextField ticketText = new JTextField(20);
		ticketText.setBounds(140, 30, 160, 25);
		contenedor.add(ticketText);

		JLabel mensajeLinea = new JLabel("");
		mensajeLinea.setBounds(50, 110, 300, 100);
		mensajeLinea.setForeground(Color.RED);
		mensajeLinea.setText("");
		contenedor.add(mensajeLinea);

		JButton botonConfirm = new JButton("Confirmar");
		botonConfirm.setBounds(140, 70, 100, 30);
		botonConfirm.setMargin(new Insets(2, 2, 2, 2));
		botonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					long ticketLong = Integer.parseInt(ticketText.getText());
					Refound refound = agencia.anular(ticketLong);

					String confirmacion;
					confirmacion = "Ticket cancelado correctamente\nFecha y hora: " + refound.getDateTime()
							+ "\nAutorización: " + refound.getAuthorization() + "\nTicket anulado: "
							+ refound.getTicket() + "\nCrédito: " + refound.getFloatAmount();

					// custom title, no icon
					JOptionPane.showMessageDialog(cardAnulacion, confirmacion, "Confirmación de anulación",
							JOptionPane.PLAIN_MESSAGE);

					// CardLayout cl = (CardLayout)(cartas.getLayout());
					// cl.show(cartas,"INICIO");
				} catch (Exception e) {
					mensajeLinea.setText(e.getMessage());
				}
			}
		});
		contenedor.add(botonConfirm);

		cardAnulacion.add(contenedor, BorderLayout.CENTER);

		cardAnulacion.add(cargaMenu(), BorderLayout.LINE_START);

		cartas.add(cardAnulacion, "ANULA");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
