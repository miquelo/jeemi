package net.jeemi.ri.tool.util;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.generate;
import static net.jeemi.ri.tool.test.TestFiles.file;
import static net.jeemi.ri.tool.test.TestFiles.read;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class SharedBundleTest
{
	private static final Charset TEST_CHARSET = UTF_8;
	
	private static final String SOME_NAME = "some-name";
	
	private static final int SOME_SOURCE_CONTENT_LENGTH = 32 * 1024;
	private static final byte[] SOME_SOURCE_CONTENT = generate(() -> ".")
		.limit(SOME_SOURCE_CONTENT_LENGTH)
		.collect(joining())
		.getBytes(TEST_CHARSET);
	
	private static final int SOME_ODD_SOURCE_CONTENT_LENGTH = 32 * 1024 + 1;
	private static final byte[] SOME_ODD_SOURCE_CONTENT = generate(() -> ".")
		.limit(SOME_ODD_SOURCE_CONTENT_LENGTH)
		.collect(joining())
		.getBytes(TEST_CHARSET);
	
	private static final int MORE_THAN_SOME_SOURCE_CONTENT_LENGTH = 64 * 1024;
	
	private File cacheDir;

	@Rule
	public final MockitoRule mockitoRule;
	
	@Rule
	public final TemporaryFolder temporaryFolder;
	
	public SharedBundleTest()
	{
		mockitoRule = MockitoJUnit.rule();
		temporaryFolder = new TemporaryFolder();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		cacheDir = temporaryFolder.newFolder();
	}
	
	@Test
	public void openFirstTime()
	throws Exception
	{
		SharedBundle bundle = SharedBundle.builder()
			.withName(SOME_NAME)
			.withSource(file(temporaryFolder, SOME_SOURCE_CONTENT))
			.withCacheDir(cacheDir)
			.build();
		
		InputStream input = bundle.open();
		byte[] bytes = new byte[SOME_SOURCE_CONTENT_LENGTH];
		int len = input.read(bytes, 0, SOME_SOURCE_CONTENT_LENGTH);
		input.close();
		byte[] cacheBytes = read(
			new File(cacheDir, SOME_NAME),
			SOME_SOURCE_CONTENT_LENGTH);
		
		assertThat(len).isEqualTo(SOME_SOURCE_CONTENT_LENGTH);
		assertThat(bytes).isEqualTo(SOME_SOURCE_CONTENT);
		assertThat(cacheBytes).isEqualTo(SOME_SOURCE_CONTENT);
	}
	
	@Test
	public void openOddFirstTime()
	throws Exception
	{
		SharedBundle bundle = SharedBundle.builder()
			.withName(SOME_NAME)
			.withSource(file(temporaryFolder, SOME_ODD_SOURCE_CONTENT))
			.withCacheDir(cacheDir)
			.build();
		
		InputStream input = bundle.open();
		byte[] bytes = new byte[SOME_ODD_SOURCE_CONTENT_LENGTH];
		int len = input.read(bytes, 0, SOME_ODD_SOURCE_CONTENT_LENGTH);
		input.close();
		byte[] cacheBytes = read(
			new File(cacheDir, SOME_NAME),
			SOME_ODD_SOURCE_CONTENT_LENGTH);
		
		assertThat(len).isEqualTo(SOME_ODD_SOURCE_CONTENT_LENGTH);
		assertThat(bytes).isEqualTo(SOME_ODD_SOURCE_CONTENT);
		assertThat(cacheBytes).isEqualTo(SOME_ODD_SOURCE_CONTENT);
	}
	
	@Test
	public void openAlreadyInCache()
	throws Exception
	{
		SharedBundle bundle = SharedBundle.builder()
			.withName(SOME_NAME)
			.withSource(file(temporaryFolder, SOME_ODD_SOURCE_CONTENT))
			.withCacheDir(cacheDir)
			.build();
		File cacheFile = new File(cacheDir, SOME_NAME);
		cacheFile.createNewFile();
		
		InputStream input = bundle.open();
		input.close();
		
		assertThat(input).isInstanceOf(FileInputStream.class);
	}
	
	@Test
	public void openForReadMoreThanNeeded()
	throws Exception
	{
		SharedBundle bundle = SharedBundle.builder()
			.withName(SOME_NAME)
			.withSource(file(temporaryFolder, SOME_SOURCE_CONTENT))
			.withCacheDir(cacheDir)
			.build();
		
		InputStream input = bundle.open();
		byte[] bytes = new byte[MORE_THAN_SOME_SOURCE_CONTENT_LENGTH];
		int len = input.read(bytes, 0, MORE_THAN_SOME_SOURCE_CONTENT_LENGTH);
		input.close();
		
		assertThat(len).isEqualTo(SOME_SOURCE_CONTENT_LENGTH);
	}
}
