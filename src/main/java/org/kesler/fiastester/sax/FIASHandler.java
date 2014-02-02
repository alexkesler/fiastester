package org.kesler.fiastester.sax;

import org.kesler.fiastester.logic.FIASRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.02.14.
 */
public class FIASHandler extends DefaultHandler {

    FIASHandlerListener listener;

    public FIASHandler(FIASHandlerListener listener) {

        this.listener = listener;

    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if(qName.equalsIgnoreCase("OBJECT")) {

             if (attributes.getValue("REGIONCODE").equalsIgnoreCase("66") &&
                     attributes.getValue("LIVESTATUS").equalsIgnoreCase("1")) {
                 FIASRecord record = new FIASRecord();

                 record.setAoGUID(attributes.getValue("AOGUID"));
                 record.setParentGUID(attributes.getValue("PARENTGUID"));
                 record.setFormalName(attributes.getValue("FORMALNAME"));
                 record.setShortName(attributes.getValue("SHORTNAME"));
                 record.setRegionCode(attributes.getValue("REGIONCODE"));
                 record.setAoLevel(attributes.getValue("AOLEVEL"));

                 listener.newRecord(record);

             }

         }
    }

}
