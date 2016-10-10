package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.Auditor;


public class AuditManager {
	private static AuditManager instance = null;
	private DataSource ds;
	
	public enum Event{
		LOGIN_ERROR
	}
	
	private AuditManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup("java:jboss/datasources/IntendenciaDS");
	}

	public static AuditManager getInstance() throws Exception {
		if (instance == null) {
			instance = new AuditManager();
		}
		return instance;
	}

	public void register(Event event, String user) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Auditor> reporte(Date desde, Date hasta) throws Exception {
		List<Auditor> lista = new ArrayList<Auditor>();
		
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT * FROM Auditoria WHERE FechaHora >=? AND FechaHora<=?");
		pre.setLong(1, desde.getTime());
		pre.setLong(2, hasta.getTime());
		ResultSet res = pre.executeQuery();
		
		while (res.next()) {
			lista.add(new Auditor(new Date(res.getLong("FechaHora")), res.getLong("Usuario"), res.getLong("Operador"),res.getInt("Evento"),res.getInt("Nivel"),res.getString("Detalle")));
		}
		
		pre.close();
		res.close();
		connection.close();

		return lista;
	}

}
