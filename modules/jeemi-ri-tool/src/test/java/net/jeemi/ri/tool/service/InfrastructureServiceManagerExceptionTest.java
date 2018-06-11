package net.jeemi.ri.tool.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class InfrastructureServiceManagerExceptionTest
{
	private static final String SOME_MESSAGE = "some-message";
	private static final Throwable SOME_CAUSE = new Exception();
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public InfrastructureServiceManagerExceptionTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getMessage()
	throws Exception
	{
		InfrastructureServiceManagerException exception =
				new InfrastructureServiceManagerException(SOME_MESSAGE);
		
		assertThat(exception.getMessage()).isEqualTo(SOME_MESSAGE);
	}
	
	@Test
	public void getCause()
	throws Exception
	{
		InfrastructureServiceManagerException exception =
				new InfrastructureServiceManagerException(SOME_CAUSE);
		
		assertThat(exception.getCause()).isEqualTo(SOME_CAUSE);
	}
	
	@Test
	public void getMessageAndCause()
	throws Exception
	{
		InfrastructureServiceManagerException exception =
				new InfrastructureServiceManagerException(
			SOME_MESSAGE,
			SOME_CAUSE);
		
		assertThat(exception.getMessage()).isEqualTo(SOME_MESSAGE);
		assertThat(exception.getCause()).isEqualTo(SOME_CAUSE);
	}
}
