package net.jeemi.ri.tool.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.tool.ApplicationCommand;
import net.jeemi.ri.tool.ApplicationContext;
import net.jeemi.ri.tool.ApplicationException;
import net.jeemi.ri.tool.service.InfrastructureServiceManager;
import net.jeemi.ri.tool.service.InfrastructureServiceManagerException;

public class PrepareApplicationCommandTest
{
	private static final String ANY_EXCEPTION_MESSAGE = "any-exception-message";

	@Mock
	private ApplicationContext context;
	
	@Mock
	private InfrastructureServiceManager serviceMgr;
	
	private ApplicationCommand command;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public PrepareApplicationCommandTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		when(context.getServiceManager())
			.thenReturn(serviceMgr);
		command = new PrepareApplicationCommand();
	}
	
	@Test
	public void execute()
	throws Exception
	{
		command.execute(context);
		
		verify(serviceMgr).prepare(any());
	}
	
	@Test(expected = ApplicationException.class)
	public void executeAndFail()
	throws Exception
	{
		doThrow(new InfrastructureServiceManagerException(
				ANY_EXCEPTION_MESSAGE))
			.when(serviceMgr).prepare(any());
		
		command.execute(context);
		
		verify(serviceMgr).prepare(any());
	}
}
