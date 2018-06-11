package net.jeemi.ri.tool;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.tool.service.InfrastructureServiceManager;

public class ApplicationContextTest
{
	private static final File ANY_WORKING_DIR = new File("/dev/null");

	@Mock
	private File workingDir;
	
	private ApplicationContext context;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ApplicationContextTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		context = ApplicationContext.builder()
			.withWorkingDir(ANY_WORKING_DIR)
			.build();
	}
	
	@Test
	public void getServiceManager()
	throws Exception
	{
		InfrastructureServiceManager serviceMgr = context.getServiceManager();
		
		assertThat(serviceMgr).isNotNull();
	}
}
