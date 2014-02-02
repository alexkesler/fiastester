package org.kesler.fiastester.dao;

import org.kesler.fiastester.logic.FIASRecord;

import java.util.List;

public interface FIASRecordDAO extends DAOObservable{
	
	public Long addRecord(FIASRecord record);

    public void addRecords(List<FIASRecord> records);

    public List<FIASRecord> getChildRecords(String parentGUID);

	public List<FIASRecord> getAllRecords();

}