package net.geoffrey.gui.beans.binding;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.TextField;

public final class TextFieldBindings
{
	private TextFieldBindings()
	{
	}
	
	public static BooleanBinding hasValue(TextField field)
	{
		return Bindings.isNotEmpty(StringBindings.trim(field.textProperty()));
	}
}
