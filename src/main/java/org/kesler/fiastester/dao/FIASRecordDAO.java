package org.kesler.fiastester.dao;

import org.kesler.fiastester.logic.FIASRecord;

import java.util.List;

public interface FIASRecordDAO extends DAOObservable{
	
    public void addRecords(List<FIASRecord> records);

	public List<FIASRecord> getAllRecords();

    public void removeAllRecords();

}