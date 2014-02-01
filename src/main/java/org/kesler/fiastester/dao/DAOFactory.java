package org.kesler.fiastester.dao;

import org.kesler.fiastester.dao.impl.GenericDAOImpl;

import org.kesler.fiastester.logic.FIASRecord;

public class DAOFactory {
	private static GenericDAO<FIASRecord> fiasRecordDAO = null;

	private static DAOFactory instance = null;

	public static synchronized DAOFactory getInstance() {
		if (instance == null) {	
			instance = new DAOFactory();
		}
		return instance;
	}


	public GenericDAO<FIASRecord> getFiasRecordDAO() {
		if (fiasRecordDAO == null) {
			fiasRecordDAO = new GenericDAOImpl<FIASRecord>(FIASRecord.class);
		}
		return fiasRecordDAO;
	}



}