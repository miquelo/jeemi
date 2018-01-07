package net.geoffrey.gui.wizard.google;

import net.geoffrey.gui.wizard.WizardConfiguration;
import net.geoffrey.gui.wizard.WizardController;
import net.jeemi.agent.assistant.AssistantFactory;

public class GoogleWizardOpenController
implements WizardController
{
	public GoogleWizardOpenController()
	{
	}
	
	@Override
	public void configure(WizardConfiguration config)
	{
	}
	
	@Override
	public AssistantFactory getFactory()
	{
		throw new UnsupportedOperationException();
	}
}
