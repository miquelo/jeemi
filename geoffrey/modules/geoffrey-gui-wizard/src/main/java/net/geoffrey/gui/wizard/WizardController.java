package net.geoffrey.gui.wizard;

import net.jeemi.agent.assistant.AssistantFactory;

public interface WizardController
{
	void configure(WizardConfiguration config);
	
	AssistantFactory getFactory();
}
