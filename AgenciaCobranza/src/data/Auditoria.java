package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import model.Constants;

public class Auditoria {
	
	public void auditar(long usuario, String localidad, int accion, int nivel, String detalle) throws Exception {
		
		long millis = new Date().getTime();
		
		InitialContext initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup(Constants.DATASOURCE_LOOKUP);
		Connection connection = ds.getConnection();
		PreparedStatement pre;
		
		try {
			pre = connection.prepareStatement("INSERT INTO Auditoria (FechaHora, Usuario, Localidad, Accion, Nivel, Detalle) VALUES (?, ?, ?, ?, ?, ?)");
			pre.setLong(  1, millis);
			pre.setLong(  2, usuario);
			pre.setString(3, localidad);
			pre.setInt(   4, accion);
			pre.setInt(   5, nivel);
			pre.setString(6, detalle);
			pre.execute();
			pre.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception("Error al auditar: " + e.getMessage());
		}
	}
}
