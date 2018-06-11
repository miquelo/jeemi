package net.jeemi.ri.tool.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class UnzipperTest
{
	private static final int UNZIPPER_BUFFER_SIZE = 8 * 1024;
	
	private static final int ANY_ESTIMATED_SIZE = 100;
	private static final ZipEntry ANY_ZIP_ENTRY = new ZipEntry("any-zip-entry");

	private File targetDir;
	
	private Unzipper unzipper;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	@Rule
	public final TemporaryFolder temporaryFolder;
	
	public UnzipperTest()
	{
		mockitoRule = MockitoJUnit.rule();
		temporaryFolder = new TemporaryFolder();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		unzipper = new Unzipper(UNZIPPER_BUFFER_SIZE);
		targetDir = temporaryFolder.newFolder();
	}
	
	@Test(expected = IOException.class)
	public void unzipBadBundle()
	throws Exception
	{
		InputStream input = mock(InputStream.class);
		when(input.read(any(), anyInt(), anyInt()))
			.thenThrow(IOException.class);
		
		unzipper.unzip(input, targetDir, ANY_ESTIMATED_SIZE);
	}
	
	@Test(expected = UncheckedIOException.class)
	public void unzipBadEntry()
	throws Exception
	{
		ZipInputStream input = mock(ZipInputStream.class);
		when(input.read(any(), anyInt(), anyInt()))
			.thenThrow(IOException.class);
		
		unzipper.unzip(input, ANY_ZIP_ENTRY, targetDir);
	}
}
