package net.jeemi.ri.agent.gf5.spi.machinery.event;

import java.util.EventListener;

/**
 * Machine event listener.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface MachineEventListener
extends EventListener
{
	void machineReady(MachineEvent event);
	
	void machineLost(MachineEvent event);
}
