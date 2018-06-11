package net.jeemi.ri.tool;

import static java.util.Objects.requireNonNull;

import java.io.File;

import net.jeemi.ri.tool.service.InfrastructureServiceManager;

public class ApplicationContext
{
	private final File workingDir;
	
	private ApplicationContext(BuilderImpl builder)
	{
		workingDir = builder.workingDir;
	}
	
	public InfrastructureServiceManager getServiceManager()
	{
		return new InfrastructureServiceManager(workingDir);
	}
	
	public static BuilderWorkingDir builder()
	{
		return new BuilderImpl();
	}
	
	public interface BuilderWorkingDir
	{
		Builder withWorkingDir(File workingDir);
	}
	
	public interface Builder
	{
		ApplicationContext build();
	}
	
	private static class BuilderImpl
	implements BuilderWorkingDir, Builder
	{
		private File workingDir;
		
		public BuilderImpl()
		{
			workingDir = null;
		}

		@Override
		public Builder withWorkingDir(File workingDir)
		{
			this.workingDir = requireNonNull(workingDir);
			return this;
		}
		
		@Override
		public ApplicationContext build()
		{
			return new ApplicationContext(this);
		}
	}
}
