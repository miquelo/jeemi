package net.jeemi.ri.tool.commands;

import static java.util.Optional.ofNullable;

import net.jeemi.ri.tool.ApplicationCommand;
import net.jeemi.ri.tool.ApplicationContext;
import net.jeemi.ri.tool.ApplicationException;
import net.jeemi.ri.tool.service.InfrastructureServiceManagerException;
import net.jeemi.ri.tool.service.InfrastructureServiceSettings;

public class PrepareApplicationCommand
implements ApplicationCommand
{
	private final Integer adminPort;
	private final Integer managementPort;
	
	public PrepareApplicationCommand()
	{
		adminPort = null;
		managementPort = null;
	}
	
	@Override
	public void execute(ApplicationContext context)
	throws ApplicationException
	{
		try
		{
			InfrastructureServiceSettings.Builder settingsBuilder =
					InfrastructureServiceSettings.builder();
			ofNullable(adminPort)
				.ifPresent(settingsBuilder::withAdminPort);
			ofNullable(managementPort)
				.ifPresent(settingsBuilder::withManagementPort);
			context.getServiceManager()
				.prepare(settingsBuilder.build());
		}
		catch (InfrastructureServiceManagerException exception)
		{
			throw new ApplicationException(exception);
		}
	}
}