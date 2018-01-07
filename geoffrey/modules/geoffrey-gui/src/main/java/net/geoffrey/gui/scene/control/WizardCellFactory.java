package net.geoffrey.gui.scene.control;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import net.geoffrey.gui.wizard.Wizard;

public class WizardCellFactory
implements Callback<ListView<Wizard>, ListCell<Wizard>>
{
	public WizardCellFactory()
	{
	}
	
	@Override
	public ListCell<Wizard> call(ListView<Wizard> param)
	{
		return new WizardListCell();
	}
}
