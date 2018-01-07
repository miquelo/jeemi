package net.geoffrey.gui;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import net.geoffrey.gui.wizard.WizardConfiguration;

public class AssistantAddConfiguration
implements WizardConfiguration
{
	private final BooleanProperty valid;
	
	public AssistantAddConfiguration(BooleanProperty valid)
	{
		this.valid = Objects.requireNonNull(valid);
	}
	
	@Override
	public BooleanProperty validProperty()
	{
		return valid;
	}
}
