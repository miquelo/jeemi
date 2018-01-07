package net.geoffrey.gui.wizard.local;

import net.geoffrey.gui.wizard.WizardConfiguration;
import net.geoffrey.gui.wizard.WizardController;
import net.jeemi.agent.assistant.AssistantFactory;

public class LocalWizardOpenController
implements WizardController
{
	public LocalWizardOpenController()
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
