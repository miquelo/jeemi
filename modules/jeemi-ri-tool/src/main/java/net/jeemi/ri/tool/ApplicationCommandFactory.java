package net.jeemi.ri.tool;

import java.util.concurrent.Callable;

public abstract class ApplicationCommandFactory
implements Callable<ApplicationCommand>
{
	protected ApplicationCommandFactory()
	{
	}
	
	@Override
	public ApplicationCommand call()
	throws Exception
	{
		return getCommand();
	}
	
	protected abstract ApplicationCommand getCommand();
}