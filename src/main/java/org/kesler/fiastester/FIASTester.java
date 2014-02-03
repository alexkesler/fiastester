package org.kesler.fiastester;


import com.alee.laf.WebLookAndFeel;
import org.kesler.fiastester.gui.main.MainViewController;
import org.kesler.fiastester.util.OptionsUtil;

public class FIASTester {

	public static void main(String args[]) {

        OptionsUtil.readOptions();
        OptionsUtil.saveOptions();
        WebLookAndFeel.install();

        MainViewController.getInstance().showMainView();

	}

}