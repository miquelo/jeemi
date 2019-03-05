package net.jeemi.machinery.event;

import java.util.EventObject;

public class MachineEvent
extends EventObject
{
	private static final long serialVersionUID = 1L;
	
	public MachineEvent(Object source)
	{
		super(source);
	}
}
