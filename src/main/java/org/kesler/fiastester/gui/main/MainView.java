package org.kesler.fiastester.gui.main;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.text.WebTextField;
import com.alee.managers.popup.PopupStyle;
import com.alee.managers.popup.WebPopup;
import com.alee.utils.swing.DocumentChangeListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;


public class MainView extends JFrame {

	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setSize(300,200);

        this.setLocationRelativeTo(null);
    }

	private void createGUI() {

        JPanel mainPanel = new JPanel(new MigLayout("fill"));

        final WebTextField addressTextField = new WebTextField(30);
        final WebPopup addressPopup = new WebPopup(PopupStyle.bevel);


        addressTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
            @Override
            public void documentChanged(DocumentEvent documentEvent) {

                addressPopup.packPopup();
                addressPopup.showAsPopupMenu(addressTextField);
                addressTextField.requestFocus();

            }
        });


        String[] strings = {"Один","Два","Три","Очень длинный текст"};

        JList addressVariantsList = new JList(strings);

        addressPopup.add(addressVariantsList);

        mainPanel.add(addressTextField, "wrap");



        this.setContentPane(mainPanel);

	}

}