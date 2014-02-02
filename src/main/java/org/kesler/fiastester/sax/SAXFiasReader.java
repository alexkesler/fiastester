package org.kesler.fiastester.sax;

import org.kesler.fiastester.dao.DAOFactory;
import org.kesler.fiastester.logic.FIASRecord;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.02.14.
 */
public class SAXFIASReader implements FIASHandlerListener{

    private SAXFIASReaderListener listener;

    private List<FIASRecord> records;

    private int recordCount = 0;

    public SAXFIASReader(SAXFIASReaderListener listener) {
        this.listener = listener;
        records = new ArrayList<FIASRecord>();
    }

    @Override
    public void newRecord(FIASRecord record) {
        if (records.size() == 1000) {
            listener.saxFIASReaderMessage("Прочинано " + recordCount + " записей. Сохраняем в БД");
            DAOFactory.getInstance().getFiasRecordDAO().addRecords(records);
            records = new ArrayList<FIASRecord>();
        }
        listener.saxFIASReaderMessage("Прочинано " + recordCount + " записей.");
        records.add(record);
        recordCount++;
    }

    public void readFIAS(File fiasFile) {

        listener.saxFIASReaderMessage("Начинаем...");

        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(fiasFile,new FIASHandler(this));
            DAOFactory.getInstance().getFiasRecordDAO().addRecords(records);
            records.clear();
            listener.saxFIASReaderMessage("Сохранено " + recordCount + " записей.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
