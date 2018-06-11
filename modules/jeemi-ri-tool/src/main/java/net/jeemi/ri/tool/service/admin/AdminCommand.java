package net.jeemi.ri.tool.service.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.jeemi.ri.tool.service.admin.commands.AdminCommnadDeleteDomain;

public abstract class AdminCommand<T>
{
	protected AdminCommand()
	{
	}
	
	public CompletableFuture<T> execute(AdminCommandContext context)
	throws IOException
	{
		return new ProcessBuilder(context.arguments(getName(), getParameters()))
			.start()
			.toHandle()
			.onExit()
			.handle(this::compute);
	}
	
	public static AdminCommnadDeleteDomain.BuilderDomainName deleteDomain()
	{
		return AdminCommnadDeleteDomain.builder();
	}
	
	protected abstract String getName();
	
	protected abstract List<String> getParameters();

	protected abstract Void compute(InputStream out);
	
	protected abstract RuntimeException compute(InputStream err, int exitValue);
	
	private T compute(ProcessHandle handle, Throwable throwable)
	{
		throw new UnsupportedOperationException();
	}
}
