package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ApplicationCommandFactoryTest
{
	private static final ApplicationCommand SOME_COMMAND =
			new TestApplicationCommand();
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationCommandFactoryTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void call()
	throws Exception
	{
		ApplicationCommandFactory factory = new TestApplicationCommandFactory(
			SOME_COMMAND);
		
		ApplicationCommand command = factory.call();
		
		assertThat(command).isEqualTo(SOME_COMMAND);
	}
	
	private static class TestApplicationCommandFactory
	extends ApplicationCommandFactory
	{
		private final ApplicationCommand command;
		
		private TestApplicationCommandFactory(ApplicationCommand command)
		{
			this.command = command;
		}

		@Override
		public ApplicationCommand getCommand()
		{
			return command;
		}
	}
	
	public static class TestApplicationCommand
	implements ApplicationCommand
	{
		private TestApplicationCommand()
		{
		}
		
		@Override
		public void execute(ApplicationContext context)
		{
			throw new UnsupportedOperationException();
		}
	}
}
