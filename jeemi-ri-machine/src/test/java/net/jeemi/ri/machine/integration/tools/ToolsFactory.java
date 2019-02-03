package net.jeemi.ri.machine.integration.tools;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

import net.jeemi.ri.machine.integration.tools.archive.ArchiveConstructor;

public class ToolsFactory
{
	private final ArchiveConstructor archiveConstructor;
	
	public ToolsFactory()
	{
		try
		{
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("factory.properties"));
			
			archiveConstructor = new ArchiveConstructor(
				new File(properties.getProperty("archiveWorkingDir")));
		}
		catch (IOException exception)
		{
			throw new UncheckedIOException(exception);
		}
	}
	
	public ArchiveConstructor getArchiveConstructor()
	{
		return archiveConstructor;
	}
}
