package net.geoffrey.gui.scene.control;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.geoffrey.gui.wizard.Wizard;

public class WizardListCell
extends ListCell<Wizard>
{
	private static final double SMALL_ICON_SIZE = 16;

	public WizardListCell()
	{
	}
	
	@Override
	protected void updateItem(Wizard item, boolean empty)
	{
		super.updateItem(item, empty);
		if (item == null || empty)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			setText(item.getDescriptiveName());
			setGraphic(new ImageView(new Image(
				item.getSmallIcon(),
				SMALL_ICON_SIZE,
				SMALL_ICON_SIZE,
				true,
				true)));
		}
	}
}
