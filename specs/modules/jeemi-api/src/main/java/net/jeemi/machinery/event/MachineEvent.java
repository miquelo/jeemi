package net.jeemi.machinery.event;

import net.jeemi.machinery.MachineRef;

/**
 * Machine event.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class MachineEvent
extends MachineryEvent
{
	private static final long serialVersionUID = 1L;

	public MachineEvent(MachineRef source)
	{
		super(source);
	}
	
	@Override
	public MachineRef getSource()
	{
		return (MachineRef) super.getSource();
	}
}
