package org.kesler.fiastester.dao;

import org.kesler.fiastester.dao.impl.FIASRecordDAOImpl;

public class DAOFactory {
	private static FIASRecordDAO fiasRecordDAO = null;

	private static DAOFactory instance = null;

	public static synchronized DAOFactory getInstance() {
		if (instance == null) {	
			instance = new DAOFactory();
		}
		return instance;
	}


	public FIASRecordDAO getFiasRecordDAO() {
		if (fiasRecordDAO == null) {
			fiasRecordDAO = new FIASRecordDAOImpl();
		}
		return fiasRecordDAO;
	}



}