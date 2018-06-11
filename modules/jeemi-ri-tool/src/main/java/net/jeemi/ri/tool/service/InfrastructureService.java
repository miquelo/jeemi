package net.jeemi.ri.tool.service;

import static java.util.Objects.requireNonNull;

import java.io.File;

public class InfrastructureService
{
	private final File serverDir;
	
	public InfrastructureService(File serverDir)
	{
		this.serverDir = requireNonNull(serverDir);
	}
}
