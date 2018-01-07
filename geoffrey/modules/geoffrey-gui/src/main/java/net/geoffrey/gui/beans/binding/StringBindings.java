package net.geoffrey.gui.beans.binding;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableStringValue;

public final class StringBindings
{
	private StringBindings()
	{
	}
	
	public static StringBinding trim(ObservableStringValue str)
	{
		return Bindings.createStringBinding(() -> str.get().trim(), str);
	}
}
