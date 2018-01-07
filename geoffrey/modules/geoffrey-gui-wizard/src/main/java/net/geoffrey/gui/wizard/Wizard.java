package net.geoffrey.gui.wizard;

import java.io.InputStream;

public interface Wizard
{
	String getDescriptiveName();
	
	InputStream getSmallIcon();
	
	WizardComponent getAssistantOpenComponent();
	
	WizardComponent getAssistantNewComponent();
}
