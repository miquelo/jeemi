package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ApplicationFactoryTest
{
	private static final String ANY_COMMAND = "any-command";
	private static final String[] ANY_ARGS = {};
	
	private static final String[] EMPTY_ARGS = {};
	
	private static final String[] SOME_COMMAND_ARGS = {
		"some-command"
	};
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationFactoryTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void call()
	throws Exception
	{
		ApplicationFactory factory = new ApplicationFactory();
		factory.commandName = ANY_COMMAND;
		factory.args = ANY_ARGS;
		
		Application application = factory.call();
		
		assertThat(application).isNotNull();
	}
	
	@Test
	public void mainWithEmptyArgs()
	throws Exception
	{
		ApplicationFactory.main(EMPTY_ARGS);
	}
	
	@Test
	public void mainWithSomeCommandArgs()
	throws Exception
	{
		ApplicationFactory.main(SOME_COMMAND_ARGS);
	}
}
