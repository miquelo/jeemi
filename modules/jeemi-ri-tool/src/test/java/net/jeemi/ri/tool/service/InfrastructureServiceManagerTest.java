package net.jeemi.ri.tool.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.jeemi.ri.tool.test.TestFiles.write;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class InfrastructureServiceManagerTest
{
	private static final Charset TEST_CHARSET = UTF_8;
	
	private static final InfrastructureServiceSettings ANY_SETTINGS =
			InfrastructureServiceSettings.builder()
		.build();
	
	private static final byte[] ANY_PROPERTIES_CONTENT = (
		"version=0.1\n").getBytes(TEST_CHARSET);
	
	private static final byte[] UNSUPPORTED_VERSION_PROPERTIES_CONTENT = (
		"version=unsuported\n").getBytes(TEST_CHARSET);

	private static final String BAD_URI_STR = "\\";
	
	private File workingDir;
	
	private InfrastructureServiceManager serviceMgr;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	@Rule
	public final TemporaryFolder temporaryFolder;
	
	public InfrastructureServiceManagerTest()
	{
		mockitoRule = MockitoJUnit.rule();
		temporaryFolder = new TemporaryFolder();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		workingDir = temporaryFolder.newFolder();
		serviceMgr = spy(new InfrastructureServiceManager(workingDir));
	}
	
	@Test
	public void prepare()
	throws Exception
	{
		serviceMgr.prepare(ANY_SETTINGS);
	}
	
	@Test(expected = InfrastructureServiceManagerException.class)
	public void prepareWithPropertiesError()
	throws Exception
	{
		doThrow(IOException.class)
			.when(serviceMgr).storeProperties(any());
		
		serviceMgr.prepare(ANY_SETTINGS);
	}
	
	@Test
	public void restore()
	throws Exception
	{
		createPropertiesFile(ANY_PROPERTIES_CONTENT);
		
		InfrastructureService service = serviceMgr.restore();
		
		assertThat(service).isNotNull();
	}
	
	@Test(expected = InfrastructureServiceManagerException.class)
	public void restoreWithPropertiesError()
	throws Exception
	{
		doThrow(FileNotFoundException.class)
			.when(serviceMgr).loadProperties();
		
		serviceMgr.restore();
	}
	
	@Test(expected = InfrastructureServiceManagerException.class)
	public void restoreFromUnsupportedVersion()
	throws Exception
	{
		createPropertiesFile(UNSUPPORTED_VERSION_PROPERTIES_CONTENT);
		
		serviceMgr.restore();
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseBadURI()
	throws Exception
	{
		serviceMgr.parseURI(BAD_URI_STR);
	}
	
	private void createPropertiesFile(byte[] content)
	throws IOException
	{
		File propertiesFile = new File(workingDir, "jeemi-service.properties");
		propertiesFile.createNewFile();
		write(propertiesFile, content);
	}
}
