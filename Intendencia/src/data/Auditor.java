package data;

import java.util.Date;
import java.util.Random;

import model.OperatorManager;
import model.UserManager;

public class Auditor {
	private Date fechaHora;
	private User usuario; 
	private Operator operador;
	private int evento;
	private int nivel;
	private String detalle;
	
	public Auditor(Date fechaHora, long usuarioId, long operadorId, int evento, int nivel, String detalle) {
		this.setFechaHora(fechaHora);
		this.setUsuario(usuarioId);
		this.setOperador(operadorId);
		this.setEvento(evento);
		this.setNivel(nivel);
		this.setDetalle(detalle);
	}
	
	public Date getFechaHora() {
		return this.fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public User getUsuario() {
		return this.usuario;
	}
	public void setUsuario(long usuarioId) {
		try {
			this.usuario = UserManager.getInstance().getUser(usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Operator getOperador() {
		return this.operador;
	}
	public void setOperador(long operadorId) {
		try {
			this.operador = OperatorManager.getInstance().getOperator(operadorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getEvento() {
		return this.evento;
	}
	public void setEvento(int evento) {
		this.evento = evento;
	}
	public int getNivel() {
		return this.nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getDetalle() {
		return this.detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	} 

}
