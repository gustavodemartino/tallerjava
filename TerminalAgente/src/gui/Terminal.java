package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Terminal extends JFrame implements ActionListener{
	private static final long serialVersionUID = 5424215328670752425L;
	private JLabel texto1, texto2;
	private JButton botonSalir, botonAlta, botonAnular;
	private String nombreTerminal = "Abitab / Punta Carretas 001";
	
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
	    
	    //Menu de acciones
  		JPanel topEspacio = new JPanel();
  		topEspacio.setPreferredSize(new Dimension(100, 10));
  		add(topEspacio,BorderLayout.NORTH);
  		
	    //Footer
		JPanel infoTerminal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		texto1=new JLabel("Terminal: " + nombreTerminal);
		texto1.setSize(300,40);
		infoTerminal.add(texto1);
		add(infoTerminal,BorderLayout.SOUTH);
		
		
		//Menu de acciones
		JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menu.setPreferredSize(new Dimension(110, 100));
		
		botonAlta = new JButton("Nuevo ticket");
		botonAlta.setPreferredSize(new Dimension(100, 30));
		botonAlta.setMargin(new Insets(2,2,2,2));
		botonAlta.addActionListener(this);
		menu.add(botonAlta);
		
		botonAnular = new JButton("Anular ticket");
		botonAnular.setPreferredSize(new Dimension(100, 30));
		botonAnular.setMargin(new Insets(2,2,2,2));
		botonAnular.addActionListener(this);
		menu.add(botonAnular);
		
		botonSalir = new JButton("Salir");
		botonSalir.setPreferredSize(new Dimension(100, 30));
		botonSalir.addActionListener(this);
		menu.add(botonSalir);
		
		add(menu,BorderLayout.WEST);
		
		//Contenedor principal
		JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		texto2 = new JLabel("Bienvenido al sistema");
		texto2.setSize(300,40);
		centro.add(texto2);
		
		add(centro,BorderLayout.CENTER);
		
		//pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonSalir) {			
			try{
				Thread.sleep(500);
				System.exit(0);
			} catch(Exception excep) {
				System.exit(0);
			}
		}
		
		if (e.getSource() == botonAlta) {
			texto2.setText("Desde aqui podrá vender nuevos tickets");
		}
		
		if (e.getSource() == botonAnular) {
			texto2.setText("Desde aqui podrá anular tickets vendidos");
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Terminal ventana = new Terminal();
			ventana.setVisible(true);
        });
	}
	
}
