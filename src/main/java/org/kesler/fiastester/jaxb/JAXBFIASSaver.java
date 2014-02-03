package org.kesler.fiastester.jaxb;

import com.alee.laf.filechooser.WebFileChooser;
import org.kesler.fiastester.dao.DAOFactory;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Алексей on 03.02.14.
 */
public class JAXBFIASSaver {

    private JAXBFIASListener listener;

    public JAXBFIASSaver(JAXBFIASListener listener) {
        this.listener = listener;
    }

    public void saveFIAS(File file) {

        FIAS fias = new FIAS();
        listener.jaxbMessage("Читаем список объектов из БД");
        fias.setRecords(DAOFactory.getInstance().getFiasRecordDAO().getAllRecords());
        try {
            JAXBContext context = JAXBContext.newInstance(FIAS.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            listener.jaxbMessage("Сохраняем в файл");
            marshaller.marshal(fias, file);
            listener.jaxbMessage("Готово");

        } catch (Exception e) {
            listener.jaxbMessage("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
