package model;

import data.Operator;

public class OperatorManager {
	private static OperatorManager instance = null;

	private OperatorManager() {

	}

	public static OperatorManager getInstance() {
		if (instance == null) {
			instance = new OperatorManager();
		}
		return instance;
	}

	public Operator getOperator(String firma) {
		// TODO Buscar en DB
		Operator result = new Operator();
		result.setId(-1);
		result.setName(firma);
		return result;
	}
}
