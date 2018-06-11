package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.tool.commands.PrepareApplicationCommandFactory;

public class ApplicationCommandRegistryTest
{
	private static final String PREPARE_COMMAND_NAME = "prepare";
	private static final String PREPARE_UNKNOWN_NAME = "unknown";

	private ApplicationCommandRegistry cmdReg;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationCommandRegistryTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		cmdReg = new ApplicationCommandRegistry();
	}
	
	@Test
	public void getPrepareCommandFactory()
	throws Exception
	{
		ApplicationCommandFactory command = cmdReg.getCommandFactory(
			PREPARE_COMMAND_NAME);
		
		assertThat(command)
			.isInstanceOf(PrepareApplicationCommandFactory.class);
	}
	
	@Test(expected = UnknownApplicationCommandException.class)
	public void getUnknownCommand()
	throws Exception
	{
		cmdReg.getCommandFactory(PREPARE_UNKNOWN_NAME);
	}
}
