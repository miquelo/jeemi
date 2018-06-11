package net.jeemi.ri.tool;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import picocli.CommandLine.Command;

public class ApplicationTest
{
	private static final File ANY_WORKING_DIR = new File("any-working-dir");
	private static final String[] ANY_ARGS = {};
	private static final PrintStream ANY_ERR =
			new PrintStream(new ByteArrayOutputStream());
	
	private static final String SOME_COMMAND_NAME = "some-command-name";
	
	private static final String UNKNOWN_COMMAND_NAME = "unknown-command-name";
	
	@Mock
	private ApplicationCommandRegistry cmdReg;
	
	@Mock
	private PrintStream err;
	
	@Mock
	private ApplicationCommand someCmd;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void executeSomeCommand()
	throws Exception
	{
		when(cmdReg.getCommandFactory(SOME_COMMAND_NAME))
			.thenReturn(new TestCommandFactory(someCmd));
		Application application = new Application(
			SOME_COMMAND_NAME,
			ANY_ARGS,
			ANY_WORKING_DIR);
		
		application.execute(cmdReg, ANY_ERR);
		
		verify(someCmd).execute(any());
	}
	
	@Test
	public void executeUnknownCommand()
	throws Exception
	{
		when(cmdReg.getCommandFactory(UNKNOWN_COMMAND_NAME))
			.thenThrow(new UnknownApplicationCommandException(
				UNKNOWN_COMMAND_NAME));
		Application application = new Application(
			UNKNOWN_COMMAND_NAME,
			ANY_ARGS,
			ANY_WORKING_DIR);
		
		application.execute(cmdReg, err);
		
		verify(err).println(contains(UNKNOWN_COMMAND_NAME));
	}
	
	@Command(
		name="jeemi-tool test",
		description = "Test a JEEMI tool command",
		mixinStandardHelpOptions = true)
	private static class TestCommandFactory
	extends ApplicationCommandFactory
	{
		private ApplicationCommand command;
		
		private TestCommandFactory(ApplicationCommand command)
		{
			this.command = command;
		}

		@Override
		protected ApplicationCommand getCommand()
		{
			return command;
		}
	}
}
