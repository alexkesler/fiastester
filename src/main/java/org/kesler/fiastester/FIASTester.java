package org.kesler.fiastester;


import org.kesler.fiastester.gui.main.MainViewController;
import org.kesler.fiastester.util.OptionsUtil;

public class FIASTester {

	public static void main(String args[]) {

        OptionsUtil.readOptions();
        OptionsUtil.saveOptions();

        MainViewController.getInstance().showMainView();

	}

}