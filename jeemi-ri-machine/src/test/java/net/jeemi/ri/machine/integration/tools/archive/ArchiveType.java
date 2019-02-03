package net.jeemi.ri.machine.integration.tools.archive;

import static java.util.Objects.requireNonNull;

public enum ArchiveType
{
	WAR("war"),
	MAR("mar");
	
	private String extension;
	
	private ArchiveType(String extension)
	{
		this.extension = requireNonNull(extension);
	}
	
	public String getExtension()
	{
		return extension;
	}
}
