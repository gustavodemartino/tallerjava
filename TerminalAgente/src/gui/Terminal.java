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
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tallerjava.data.Ticket;
import terminal.Agencia;

public class Terminal extends JFrame implements ActionListener{
	private static final long serialVersionUID = 5424215328670752425L;
	private JLabel texto1, texto2;
	private JButton botonLogin, botonSalir, botonAlta, botonAnular;
	private String nombreTerminal = "Abitab / Punta Carretas 001";
	private JPanel cartas, cardLogin, cardInicio, cardAlta, cardAnulacion; 
	
	public Terminal() {
		initUI();
	}
	
	private void initUI() {	
		setTitle("IMM - Tickets Estacionamiento");
		ImageIcon webIcon = new ImageIcon("favicon.png");
        setIconImage(webIcon.getImage());
        
		setSize(500,350);
		setLocationRelativeTo(null);	//center the window on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//close the window if we click on the Close button of the titlebar
		setLayout(new BorderLayout(5,5));	//divide la ventana en 5 partes: centro, arriba, abajo, derecha e izquierda.
	    
	    //Header - Barra espaceadora superior
  		JPanel topEspacio = new JPanel();
  		texto1=new JLabel(" ");
		topEspacio.add(texto1);
  		topEspacio.setBackground(Color.ORANGE);
  		add(topEspacio,BorderLayout.PAGE_START);
  		 		
	    //Footer - Info de conexión
		JPanel infoTerminal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		texto1=new JLabel("Terminal: " + nombreTerminal);
		infoTerminal.add(texto1);
		infoTerminal.setBackground(Color.ORANGE);
		add(infoTerminal,BorderLayout.PAGE_END);
				
		cartas = new JPanel(new CardLayout());
		cargaCarta1();
		cargaCarta2();
		cargaCarta3();
		cargaCarta4();
		add(cartas,BorderLayout.CENTER);
		
	}
	
	private JPanel cargaMenu() {
		//Botonera
		JPanel menu = new JPanel ();
		menu.setLayout(new FlowLayout(FlowLayout.LEFT));
		menu.setPreferredSize(new Dimension(110, 100));
		
		botonAlta = new JButton("Nuevo ticket");
		botonAlta.setPreferredSize(new Dimension(100, 30));
		botonAlta.setMargin(new Insets(2,2,2,2));
		//botonAlta.addActionListener(this);
		botonAlta.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
			  {
				  CardLayout cl = (CardLayout)(cartas.getLayout());
				  cl.show(cartas,"ALTA");
			  }
			});
		menu.add(botonAlta);
		
		botonAnular = new JButton("Anular ticket");
		botonAnular.setPreferredSize(new Dimension(100, 30));
		botonAnular.setMargin(new Insets(2,2,2,2));
		botonAnular.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
			  {
				  CardLayout cl = (CardLayout)(cartas.getLayout());
				  cl.show(cartas,"ANULA");
			  }
			});
		menu.add(botonAnular);
		
		botonSalir = new JButton("Cerrar sesión");
		botonSalir.setPreferredSize(new Dimension(100, 30));
		botonSalir.setMargin(new Insets(2,2,2,2));
		botonSalir.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
			  {
				  CardLayout cl = (CardLayout)(cartas.getLayout());
				  cl.show(cartas,"LOGIN");
			  }
			});
		menu.add(botonSalir);
		
		return menu;
		
	}
	
	private void cargaCarta1() {
		
		cardLogin = new JPanel();
		cardLogin.setLayout(new BoxLayout(cardLogin, BoxLayout.Y_AXIS));		
		
		texto2 = new JLabel("Funcion de login");
		texto2.setAlignmentX(CENTER_ALIGNMENT);
		cardLogin.add(texto2);
		
		botonLogin = new JButton("Iniciar sesión");
		botonLogin.setPreferredSize(new Dimension(100, 30));
		botonLogin.setMargin(new Insets(2,2,2,2));
		botonLogin.setAlignmentX(CENTER_ALIGNMENT);
		botonLogin.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e)
			  {
				  CardLayout cl = (CardLayout)(cartas.getLayout());
				  cl.show(cartas,"INICIO");
			  }
			});
		cardLogin.add(botonLogin);
		
		cartas.add(cardLogin, "LOGIN");
	}
	
	private void cargaCarta2() {
		
		cardInicio = new JPanel(new BorderLayout(5,5));
		
		texto2 = new JLabel("Bienvenido");// al sistema de gestión de tickets de estacionamiento de la IMM");
		cardInicio.add(texto2,BorderLayout.CENTER);
		
		cardInicio.add(cargaMenu(),BorderLayout.LINE_START);
		
		cartas.add(cardInicio, "INICIO");
	}
	
	private void cargaCarta3() {
		
		cardAlta = new JPanel(new BorderLayout(5,5));
		
		texto2 = new JLabel("Función de alta");
		texto2.setAlignmentX(CENTER_ALIGNMENT);
		texto2.setAlignmentY(CENTER_ALIGNMENT);
		cardAlta.add(texto2,BorderLayout.CENTER);
		
		cardAlta.add(cargaMenu(),BorderLayout.LINE_START);
		
		cartas.add(cardAlta, "ALTA");
	}
	
	private void cargaCarta4() {
		
		cardAnulacion = new JPanel(new BorderLayout(5,5));
		
		texto2 = new JLabel("Función de anulación");
		texto2.setAlignmentX(CENTER_ALIGNMENT);
		texto2.setAlignmentY(CENTER_ALIGNMENT);
		cardAnulacion.add(texto2,BorderLayout.CENTER);
		
		cardAnulacion.add(cargaMenu(),BorderLayout.LINE_START);
		
		cartas.add(cardAnulacion, "ANULA");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		CardLayout cl = (CardLayout)(cartas.getLayout());
		
		if (e.getSource() == botonLogin) {
			cl.show(cartas,"INICIO");
		}
		
		if (e.getSource() == botonSalir) {			
			cl.show(cartas,"LOGIN");
			
			/*try{
				Thread.sleep(500);
				System.exit(0);
			} catch(Exception excep) {
				System.exit(0);
			}*/
									
		}
		
		if (e.getSource() == botonAlta) {			
			cl.show(cartas,"ALTA");
			
			/*
			texto2.setText("Desde aqui podrá vender nuevos tickets");
			try {
				Agencia agencia = new Agencia("terminal_id");		
				agencia.login("admin", "admin");
				Date hoy = new Date();
				Ticket t = agencia.vender("Prueba1",30,hoy);
				String infoString = "Agencia: " + t.getAgency();
				infoString += "Hora venta: " + t.getSaleDateTime();
				infoString += "Ticket número: " + t.getTicketNumber();
				infoString += "Matrícula: " + t.getPlate();
				infoString += "Inicio: " + t.getStartDateTime();
				infoString += "Minutos: " + t.getMinutes();
				infoString += "Importe: " + t.getAmount();
				texto2.setText(infoString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				texto2.setText("Error!");
			}*/			
			
		}
		
		if (e.getSource() == botonAnular) {
			cl.show(cartas,"ANULA");
			//texto2.setText("Desde aqui podrá anular tickets vendidos");
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Terminal ventana = new Terminal();
			ventana.setVisible(true);
        });
	}
	
}
