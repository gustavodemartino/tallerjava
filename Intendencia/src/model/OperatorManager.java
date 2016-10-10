package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.Operator;

public class OperatorManager {
	private static OperatorManager instance = null;
	private DataSource ds;

	private OperatorManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup("java:jboss/datasources/IntendenciaDS");
	}

	public static OperatorManager getInstance() throws Exception {
		if (instance == null) {
			instance = new OperatorManager();
		}
		return instance;
	}

	public Operator getOperator(String signature) throws Exception {
		Operator result = null;
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT Id, Nombre FROM Operadores WHERE firma = ?");
		pre.setString(1, signature);
		ResultSet res = pre.executeQuery();
		if (res.next()) {
			result = new Operator(res.getLong("Id"), signature, res.getString("Nombre"));
		}
		res.close();
		pre.close();
		connection.close();
		return result;
	}
	
	public Operator getOperator(long operatorId) throws Exception {
		Operator result = null;
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT * FROM Operadores WHERE Id = ?");
		pre.setLong(1, operatorId);
		ResultSet res = pre.executeQuery();
		if (res.next()) {
			result = new Operator(res.getLong("Id"), res.getString("Firma"), res.getString("Nombre"));
		}
		res.close();
		pre.close();
		connection.close();
		return result;
	}
}
