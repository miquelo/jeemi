package net.jeemi.ri.tool.service;

public class InfrastructureServiceSettings
{
	public static final int DEFAULT_ADMIN_PORT = 5858;
	public static final int DEFAULT_MANAGEMENT_PORT = 4362;
	
	private final int adminPort;
	private final int managementPort;
	
	private InfrastructureServiceSettings(BuilderImpl builder)
	{
		adminPort = builder.adminPort;
		managementPort = builder.managementPort;
	}
	
	public int getAdminPort()
	{
		return adminPort;
	}

	public int getManagementPort()
	{
		return managementPort;
	}

	public static Builder builder()
	{
		return new BuilderImpl();
	}
	
	public interface Builder
	{
		Builder withAdminPort(int adminPort);
		
		Builder withManagementPort(int managementPort);
		
		InfrastructureServiceSettings build();
	}
	
	public static class BuilderImpl
	implements Builder
	{
		private int adminPort;
		private int managementPort;
		
		public BuilderImpl()
		{
			adminPort = 0;
			managementPort = 0;
		}
		
		@Override
		public InfrastructureServiceSettings build()
		{
			return new InfrastructureServiceSettings(this);
		}

		@Override
		public Builder withAdminPort(int adminPort)
		{
			this.adminPort = adminPort;
			return this;
		}

		@Override
		public Builder withManagementPort(int managementPort)
		{
			this.managementPort = managementPort;
			return this;
		}
	}
}
