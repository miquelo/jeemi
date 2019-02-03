package net.jeemi.ri.machine.integration.test;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.jeemi.ri.machine.integration.Integration;
import net.jeemi.ri.machine.integration.tools.ToolsFactory;
import net.jeemi.ri.machine.integration.tools.archive.Archive;
import net.jeemi.ri.machine.integration.tools.archive.ArchiveType;

@Category(Integration.class)
public class BasicClusterTest
{
	private final ToolsFactory toolsFactory;
	
	public BasicClusterTest()
	{
		toolsFactory = new ToolsFactory();
	}
	
	@Test
	@Ignore
	public void domainAdministrationServerUp()
	throws Exception
	{
		File archiveFile = toolsFactory.getArchiveConstructor()
			.construct(Archive.builder()
				.withType(ArchiveType.MAR)
				.withName("machine")
				.build());
	}
}
