package net.geoffrey.gui.wizard.google;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardFactory;

public class GoogleWizardFactory
implements WizardFactory
{
	public GoogleWizardFactory()
	{
	}
	
	@Override
	public Wizard getWizard()
	throws IOException
	{
		FXMLLoader newLoader = new FXMLLoader(
			getClass().getResource(
				"/net/geoffrey/gui/wizard/google/assistant-new.fxml"),
			ResourceBundle.getBundle(
				"net.geoffrey.gui.wizard.google.Messages"));
		newLoader.setControllerFactory(type ->
			new GoogleWizardNewController());
		
		FXMLLoader openLoader = new FXMLLoader(
			getClass().getResource(
				"/net/geoffrey/gui/wizard/google/assistant-open.fxml"),
			ResourceBundle.getBundle(
				"net.geoffrey.gui.wizard.google.Messages"));
		openLoader.setControllerFactory(type ->
			new GoogleWizardOpenController());
		
		return new GoogleWizard(newLoader, openLoader);
	}
}
