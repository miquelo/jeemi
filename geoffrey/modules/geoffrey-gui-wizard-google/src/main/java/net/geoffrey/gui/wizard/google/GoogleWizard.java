package net.geoffrey.gui.wizard.google;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardComponent;

public class GoogleWizard
implements Wizard
{
	private final WizardComponent assistantNewComponent;
	private final WizardComponent assistantOpenComponent;
	
	public GoogleWizard(FXMLLoader assistantNewLoader,
			FXMLLoader assistantOpenLoader)
	throws IOException
	{
		Objects.requireNonNull(assistantOpenLoader);
		assistantNewComponent = new WizardComponent(
				Objects.requireNonNull(assistantNewLoader.load()),
				assistantNewLoader.getController());
		assistantOpenComponent = new WizardComponent(
			Objects.requireNonNull(assistantOpenLoader).load(),
			assistantOpenLoader.getController());
	}
	
	@Override
	public String getDescriptiveName()
	{
		return "Google Cloud Platform";
	}

	@Override
	public InputStream getSmallIcon()
	{
		return getClass().getResourceAsStream("small-icon.png");
	}

	@Override
	public WizardComponent getAssistantNewComponent()
	{
		return assistantNewComponent;
	}
	
	@Override
	public WizardComponent getAssistantOpenComponent()
	{
		return assistantOpenComponent;
	}
}
