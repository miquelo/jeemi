package net.jeemi.ri.agent.gf4.spi.machinery.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.gf4.spi.machinery.MachineRef;
import net.jeemi.ri.agent.gf4.spi.machinery.event.MachineEvent;

public class MachineEventTest
{
	private static final MachineRef SOME_SOURCE = MachineRef.of(
		"some-machine-ref");
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public MachineEventTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getSource()
	throws Exception
	{
		MachineEvent event = new MachineEvent(SOME_SOURCE);
		
		assertThat(event.getSource()).isEqualTo(SOME_SOURCE);
	}
}
