package net.jeemi.machinery;

import net.jeemi.machinery.event.MachineEventListener;

/**
 * Machinery.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface Machinery
{
	<T extends Machine> T lookup(MachineRef ref);
	
	DASMachine create(DASMachineSpecs specs);
	
	NodeMachine create(NodeMachineSpecs specs);
	
	void leave(MachineRef ref);
	
	void addMachineEventListener(MachineEventListener listener);
	
	void removeMachineEventListener(MachineEventListener listener);
}
