package net.jeemi.ri.agent.gf5.spi.machinery.event;

import java.util.EventObject;

import net.jeemi.ri.agent.gf5.spi.machinery.MachineRef;

/**
 * Machine event.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class MachineEvent
extends EventObject
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
