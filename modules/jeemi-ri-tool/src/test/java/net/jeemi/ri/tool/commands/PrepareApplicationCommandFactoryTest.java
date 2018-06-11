package net.jeemi.ri.tool.commands;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.tool.ApplicationCommand;
import net.jeemi.ri.tool.ApplicationCommandFactory;

public class PrepareApplicationCommandFactoryTest
{
	@Rule
	public final MockitoRule mockitoRule;
	
	public PrepareApplicationCommandFactoryTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void call()
	throws Exception
	{
		ApplicationCommandFactory factory =
				new PrepareApplicationCommandFactory();
		
		ApplicationCommand command = factory.call();
		
		assertThat(command).isInstanceOf(PrepareApplicationCommand.class);
	}
}
