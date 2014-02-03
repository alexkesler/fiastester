package org.kesler.fiastester.gui.main;

import com.alee.laf.filechooser.WebFileChooser;
import org.kesler.fiastester.jaxb.JAXBFIASLoader;
import org.kesler.fiastester.jaxb.JAXBFIASSaver;
import org.kesler.fiastester.jaxb.JAXBFIASListener;
import org.kesler.fiastester.logic.FIASModel;
import org.kesler.fiastester.logic.FIASModelListener;
import org.kesler.fiastester.sax.SAXFIASReader;
import org.kesler.fiastester.sax.SAXFIASReaderListener;
import org.kesler.fiastester.util.OptionsUtil;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.02.14.
 */
public class MainViewController implements SAXFIASReaderListener, FIASModelListener, JAXBFIASListener{

    private static MainViewController instance = null;

    private MainView mainView;

    private FIASModel fiasModel;

    int waiterCount = 0;

    private MainViewController() {
        mainView = new MainView(this);
        fiasModel = new FIASModel(this);
    }

    public static synchronized MainViewController getInstance() {
        if (instance == null) instance = new MainViewController();
        return instance;
    }

    public void showMainView() {

        mainView.setVisible(true);
    }

    public JFrame getMainView() {return mainView;}

    public void importFIAS() {
        WebFileChooser fileChooser = new WebFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы XML","xml");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(mainView);
        if(result == WebFileChooser.APPROVE_OPTION) {
            final File fiasFile = fileChooser.getSelectedFile();
            final SAXFIASReader reader = new SAXFIASReader(this);
            Thread readerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    reader.readFIAS(fiasFile);
                }
            });
            readerThread.start();
        }
    }

    public void exportFIAS() {
        WebFileChooser fileChooser = new WebFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы XML","xml");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filter);


        int result = fileChooser.showSaveDialog(mainView);
        if(result == WebFileChooser.APPROVE_OPTION) {
            File file =  fileChooser.getSelectedFile();
            JAXBFIASSaver saver = new JAXBFIASSaver(this);
            saver.saveFIAS(file);
        }
    }

    public void loadFIAS() {
        WebFileChooser fileChooser = new WebFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Файлы XML","xml");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(mainView);
        if(result == WebFileChooser.APPROVE_OPTION) {
            File file =  fileChooser.getSelectedFile();
            JAXBFIASLoader loader = new JAXBFIASLoader(this);
            loader.loadFIAS(file);
        }
    }


    public void saxFIASReaderMessage(String message) {
        mainView.setSAXMessage(message);
    }

    public void computeAddressesByString(final String searchString) {

        // реализуем ожидание 1 сек пока не введем все
        Thread waiter = new Thread(new Runnable() {
            @Override
            public void run() {
                waiterCount++;
                try {
                    Thread.sleep(700);
                } catch (Exception e) {}
                // если по окончании ожидания больше клавиш не нажато - запускаемся
                System.out.println("WaiterCount = " + waiterCount + " Search: " + searchString);
                if (waiterCount < 2) fiasModel.computeAddressesInSeparateThread(searchString);
                waiterCount--;
            }
        });

        waiter.start();


//        List<String> addresses = FIASModel.getInstance().computeAddress(searchString);
//        mainView.setAddressVariants(addresses);
    }

    @Override
    public void addresesFiltered(List<String> addreses) {
        mainView.setAddresses(addreses);
    }

    @Override
    public void jaxbMessage(String message) {
        mainView.setJAXBMessage(message);
    }

}
