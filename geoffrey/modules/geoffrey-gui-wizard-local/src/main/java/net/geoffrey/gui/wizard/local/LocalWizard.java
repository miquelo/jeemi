package net.geoffrey.gui.wizard.local;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardComponent;

public class LocalWizard
implements Wizard
{
	private final WizardComponent assistantNewComponent;
	private final WizardComponent assistantOpenComponent;
	
	public LocalWizard(FXMLLoader assistantNewLoader,
			FXMLLoader assistantOpenLoader)
	throws IOException
	{
		Objects.requireNonNull(assistantOpenLoader);
		assistantOpenComponent = new WizardComponent(
			Objects.requireNonNull(assistantOpenLoader).load(),
			assistantOpenLoader.getController());
		assistantNewComponent = new WizardComponent(
			Objects.requireNonNull(assistantNewLoader.load()),
			assistantNewLoader.getController());
	}
	
	@Override
	public String getDescriptiveName()
	{
		return "Local Machine";
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
