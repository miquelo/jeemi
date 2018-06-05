package net.jeemi.spi.machinery.event;

import java.util.EventObject;

/**
 * Machinery event.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public abstract class MachineryEvent
extends EventObject
{
	private static final long serialVersionUID = 1L;

	protected MachineryEvent(Object source)
	{
		super(source);
	}
}
