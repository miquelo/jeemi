package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class UnknownApplicationCommandExceptionTest
{
	private static final String SOME_COMMAND_NAME = "some-command";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public UnknownApplicationCommandExceptionTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getCommandName()
	throws Exception
	{
		UnknownApplicationCommandException exception =
				new UnknownApplicationCommandException(SOME_COMMAND_NAME);
		
		assertThat(exception.getCommandName()).isEqualTo(SOME_COMMAND_NAME);
	}
}
