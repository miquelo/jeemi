package net.geoffrey.gui.wizard;

import java.io.IOException;

public interface WizardFactory
{
	Wizard getWizard()
	throws IOException;
}
