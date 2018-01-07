package net.geoffrey.gui.wizard.local;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardFactory;

public class LocalWizardFactory
implements WizardFactory
{
	public LocalWizardFactory()
	{
	}
	
	@Override
	public Wizard getWizard()
	throws IOException
	{
		FXMLLoader newLoader = new FXMLLoader(
			getClass().getResource(
				"/net/geoffrey/gui/wizard/local/assistant-new.fxml"),
			ResourceBundle.getBundle(
				"net.geoffrey.gui.wizard.local.Messages"));
		newLoader.setControllerFactory(type ->
			new LocalWizardNewController());
		
		FXMLLoader openLoader = new FXMLLoader(
			getClass().getResource(
				"/net/geoffrey/gui/wizard/local/assistant-open.fxml"),
			ResourceBundle.getBundle(
				"net.geoffrey.gui.wizard.local.Messages"));
		openLoader.setControllerFactory(type ->
			new LocalWizardOpenController());
		
		return new LocalWizard(newLoader, openLoader);
	}
}
