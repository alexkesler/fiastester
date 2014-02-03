package org.kesler.fiastester.gui.main;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.WebPopup;
import com.alee.utils.swing.DocumentChangeListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.*;
import java.util.List;


public class MainView extends JFrame {

    MainViewController controller;

    private JLabel saxMessageLabel;
    private WebList addressesList;
    private WebPopup addressPopup;
    private WebTextField  addressTextField;
    private JLabel jaxbMessageLabel;

	public MainView(MainViewController controller) {
		this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setSize(400,300);

        this.setLocationRelativeTo(null);
    }

	private void createGUI() {

        JPanel mainPanel = new JPanel(new MigLayout("fillx, nogrid"));

        addressTextField = new WebTextField(30);
        addressPopup = new WebPopup(PopupStyle.lightSmall);
        addressPopup.setRequestFocusOnShow(false);
        addressPopup.setSize(addressTextField.getWidth(), 50);


        addressTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
            @Override
            public void documentChanged(DocumentEvent documentEvent) {
                controller.computeAddressesByString(addressTextField.getText());
//                addressPopup.packPopup();
//                addressPopup.showAsPopupMenu(addressTextField);
//                addressTextField.requestFocus();

            }
        });

        addressTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAddress();
            }
        });

        addressTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_DOWN ||
                        e.getKeyCode()==KeyEvent.VK_UP) {
                    addressesList.requestFocusInWindow();
                    super.keyPressed(e);
                }

            }
        });


        String[] strings = {"Один","Два","Три","Очень длинный текст"};

        addressesList = new WebList(strings);
        addressesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addressesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount()==2) selectAddress();
            }
        });
        addressesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER) selectAddress();
            }
        });

        WebScrollPane addressesListScrollPane = new WebScrollPane(addressesList);

        addressPopup.add(addressesListScrollPane);
        addressPopup.setAnimated(true);



        JButton readFIASButton = new JButton("Прочитать ФИАС");
        readFIASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.importFIAS();
            }
        });

        saxMessageLabel = new JLabel("Сообщение о загрузке");
        saxMessageLabel.setBorder(BorderFactory.createEtchedBorder());

        JButton loadFIASButton = new JButton("Загрузить ФИАС");
        loadFIASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadFIAS();
            }
        });


        JButton saveFIASButton = new JButton("Выгрузить ФИАС");
        saveFIASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exportFIAS();
            }
        });

        jaxbMessageLabel = new JLabel("Сообщение о сохранении");
        jaxbMessageLabel.setBorder(BorderFactory.createEtchedBorder());


        mainPanel.add(addressTextField, "wrap");
        mainPanel.add(readFIASButton, "wrap");
        mainPanel.add(saxMessageLabel, "growx, wrap");
        mainPanel.add(loadFIASButton);
        mainPanel.add(saveFIASButton, "wrap");
        mainPanel.add(jaxbMessageLabel, "growx, wrap");
//        mainPanel.add(addressesListScrollPane, "span, grow");



        this.setContentPane(mainPanel);

	}

    public void setSAXMessage(String message) {
        saxMessageLabel.setText(message);
    }

    public void setJAXBMessage(String message) {
        jaxbMessageLabel.setText(message);
    }

    public void setAddresses(List<String> addresses) {
        addressesList.setListData(addresses.toArray());

        if(addresses.size() > 0) {
            addressesList.setSelectedIndex(0);
            if(!addressPopup.isShowing()){
//                addressPopup.packPopup();
                addressPopup.showPopup(addressTextField,0,addressTextField.getHeight());
            }
        }
        else addressPopup.hidePopup();
    }

    private void selectAddress() {
        addressTextField.setText((String)addressesList.getSelectedValue());
        addressTextField.requestFocus();
    }

}