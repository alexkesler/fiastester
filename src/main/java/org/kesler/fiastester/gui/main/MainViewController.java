package org.kesler.fiastester.gui.main;

import com.alee.laf.filechooser.WebFileChooser;
import org.kesler.fiastester.logic.FIASModel;
import org.kesler.fiastester.logic.FIASModelListener;
import org.kesler.fiastester.sax.SAXFIASReader;
import org.kesler.fiastester.sax.SAXFIASReaderListener;
import org.kesler.fiastester.util.OptionsUtil;


import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.02.14.
 */
public class MainViewController implements SAXFIASReaderListener, FIASModelListener{

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

    public void importFIAS() {
        WebFileChooser fileChooser = new WebFileChooser();

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
        int result = fileChooser.showOpenDialog(mainView);
        if(result == WebFileChooser.APPROVE_OPTION) {

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

    public void addresesFiltered(List<String> addreses) {
        mainView.setAddresses(addreses);
    }



}
