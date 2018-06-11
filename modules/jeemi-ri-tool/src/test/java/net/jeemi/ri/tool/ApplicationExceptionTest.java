package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ApplicationExceptionTest
{
	private static final String SOME_MESSAGE = "some-message";
	private static final Throwable SOME_CAUSE = new Exception();
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationExceptionTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getMessage()
	throws Exception
	{
		ApplicationException exception = new ApplicationException(SOME_MESSAGE);
		
		assertThat(exception.getMessage()).isEqualTo(SOME_MESSAGE);
	}
	
	@Test
	public void getCause()
	throws Exception
	{
		ApplicationException exception = new ApplicationException(SOME_CAUSE);
		
		assertThat(exception.getCause()).isEqualTo(SOME_CAUSE);
	}
	
	@Test
	public void getMessageAndCause()
	throws Exception
	{
		ApplicationException exception = new ApplicationException(
			SOME_MESSAGE,
			SOME_CAUSE);
		
		assertThat(exception.getMessage()).isEqualTo(SOME_MESSAGE);
		assertThat(exception.getCause()).isEqualTo(SOME_CAUSE);
	}
}
