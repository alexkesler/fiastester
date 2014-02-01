package org.kesler.fiastester.logic;

import org.kesler.fiastester.dao.DAOFactory;

import java.util.List;

/**
 * Created by Алексей on 01.02.14.
 */
public class FIASModel {

    private static FIASModel instance = null;

    private FIASModel() {}

    public static synchronized FIASModel getInstance() {
        if (instance == null) {
            instance = new FIASModel();
        }
        return instance;
    }

    public List<FIASRecord> getChildRecordsForGUID(String guid) {
        return DAOFactory.getInstance().getFiasRecordDAO().getAllItems();
    }

}
