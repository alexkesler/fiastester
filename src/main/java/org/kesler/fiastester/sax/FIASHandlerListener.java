package org.kesler.fiastester.sax;

import org.kesler.fiastester.logic.FIASRecord;

public interface FIASHandlerListener {
    public void newRecord(FIASRecord record);
}