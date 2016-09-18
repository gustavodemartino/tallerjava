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
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import data.Ticket;
import data.User;
import tcp_service.Agencia;

public class Terminal extends JFrame  implements ActionListener {
	private static final long serialVersionUID = 5424215328670752425L;
	private JLabel infoUsuario, infoTerminal;
	private JButton botonLogin, botonSalir, botonAlta, botonAnular;
	private JPanel cartas, cardLogin, cardInicio, cardAlta, cardAnulacion;
	Agencia agencia;
	private String terminalId = "terminal_1";
	private String nombreTerminal = "Terminal 1";
	User usuarioDatos;
	

	public Terminal() {
		initUI();
	}

	private void initUI() {

		try {
			agencia = new Agencia(terminalId);
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
		JPanel topEspacio = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		infoUsuario = new JLabel("(Sin usuario conectado)");
		topEspacio.add(infoUsuario);
		topEspacio.setBackground(Color.orange);
		add(topEspacio, BorderLayout.PAGE_START);

		// Footer - Info de conexión
		JPanel botomEspacio = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		infoTerminal = new JLabel("Terminal: " + nombreTerminal);
		botomEspacio.add(infoTerminal);
		botomEspacio.setBackground(Color.orange);
		add(botomEspacio, BorderLayout.PAGE_END);

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
					infoUsuario.setText("(Sin usuario conectado)");
				} catch (Exception e1) {
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
		userText.setText("user");
		userText.setBounds(170, 10, 160, 25);
		cardLogin.add(userText);

		JLabel passwordLabel = new JLabel("Clave");
		passwordLabel.setBounds(80, 40, 80, 25);
		cardLogin.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(170, 40, 160, 25);
		passwordText.setText("user");
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
					usuarioDatos = agencia.login(userText.getText(), strPassword);
					
					infoUsuario.setText("Bienvenido " + usuarioDatos.getUserName());
					//infoUsuario.setText("Hola " + userText.getText());
					
					userText.setText("");
					passwordText.setText("");
					
					CardLayout cl = (CardLayout) (cartas.getLayout());
					cl.show(cartas, "INICIO");
				} catch (Exception e) {
					mensajeError.setText(e.getMessage());
					e.printStackTrace();
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
		
		SpinnerModel hora = new SpinnerNumberModel( 0,  //initial value
		                               				0,  //min
	                               					23, //max
	                               					1); //step
		SpinnerModel minutos = new SpinnerNumberModel(  0,  //initial value
   														0,  //min
   														59, //max
   														1); //step

		JSpinner spinnerHora = new JSpinner(hora);
		JSpinner spinnerMinutos = new JSpinner(minutos);

		JLabel inicioLabel = new JLabel("Inicio");
		inicioLabel.setBounds(50, 90, 80, 25);
		contenedor.add(inicioLabel);

//		JTextField inicioText = new JTextField(20);
//		inicioText.setBounds(140, 90, 160, 25);
//		contenedor.add(inicioText);
		
		Date hoy = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(hoy);   // assigns calendar to given date 		
		
		spinnerHora.setBounds(140, 90, 50, 25);
		spinnerHora.setValue(calendar.get(Calendar.HOUR_OF_DAY));
		contenedor.add(spinnerHora);
		
		JLabel column = new JLabel(" : ");
		column.setBounds(190, 90, 10, 25);
		contenedor.add(column);
		
		spinnerMinutos.setBounds(200, 90, 50, 25);
		spinnerMinutos.setValue(calendar.get(Calendar.MINUTE));
		contenedor.add(spinnerMinutos);

		
		JLabel mensajeLinea1 = new JLabel("");
		mensajeLinea1.setBounds(50, 150, 300, 25);
		contenedor.add(mensajeLinea1);
		JLabel mensajeLinea2 = new JLabel("");
		mensajeLinea2.setBounds(50, 175, 300, 25);
		contenedor.add(mensajeLinea2);


		JButton botonConfirm = new JButton("Confirmar");
		botonConfirm.setBounds(140, 120, 100, 30);
		botonConfirm.setMargin(new Insets(2, 2, 2, 2));
		botonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					mensajeLinea1.setText("");
					
					SimpleDateFormat sdf = new SimpleDateFormat("k:m");
					Date date = sdf.parse(spinnerHora.getValue()+":"+spinnerMinutos.getValue());
					
					int cantidadMinutos = Integer.parseInt(minutosText.getText());
					Ticket t = agencia.vender(matriculaText.getText(), cantidadMinutos, date);
					
					matriculaText.setText("");
					minutosText.setText("");
					spinnerHora.setValue(0);
					spinnerMinutos.setValue(0);
					
					Date horaInicio = t.getStartDateTime();
					calendar.setTime(horaInicio);   // assigns calendar to given date
					String horaIniStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
					
					Date horaFin = t.getEndDateTime();
					calendar.setTime(horaFin);   // assigns calendar to given date
					String horaFinStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
					
					String confirmacion;
					confirmacion =  "Ticket: " + t.getTicketNumber() + "\n";
					confirmacion += "Matrícula: " + t.getPlate() + "\n";
					confirmacion += "Válido desde " + horaIniStr  + " hasta " + horaFinStr + "\n";
					confirmacion += "\n" + "Importe a cobrar = $" + t.getFloatAmount() + "\n";
					confirmacion += " " + "\n";
					
					//custom title, no icon
					JOptionPane.showMessageDialog(cardAlta,confirmacion,"Confirmación de venta",JOptionPane.PLAIN_MESSAGE);

					// CardLayout cl = (CardLayout)(cartas.getLayout());
					// cl.show(cartas,"INICIO");
				} catch (Exception e) {
					mensajeLinea1.setForeground(Color.RED);
					mensajeLinea1.setText(e.getMessage());
					e.printStackTrace();
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
		
		JLabel mensajeLinea1 = new JLabel("");
		mensajeLinea1.setBounds(50, 110, 300, 25);
		contenedor.add(mensajeLinea1);
		
		JButton botonConfirm = new JButton("Confirmar");
		botonConfirm.setBounds(140, 70, 100, 30);
		botonConfirm.setMargin(new Insets(2, 2, 2, 2));
		botonConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent eve) {
				try {
					mensajeLinea1.setText("");
					
					long ticketLong = Integer.parseInt(ticketText.getText());
					agencia.anular(ticketLong);

					String confirmacion;
					confirmacion =  "Ticket " + ticketLong + " anulado correctamente\n";
					confirmacion += " " + "\n";
					
					//custom title, no icon
					JOptionPane.showMessageDialog(cardAnulacion,confirmacion,"Confirmación de anulación",JOptionPane.PLAIN_MESSAGE);

					// CardLayout cl = (CardLayout)(cartas.getLayout());
					// cl.show(cartas,"INICIO");
				} catch (Exception e) {
					mensajeLinea1.setForeground(Color.RED);
					mensajeLinea1.setText(e.getMessage());
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Terminal ventana = new Terminal();
			ventana.setVisible(true);
		});
	}

}
