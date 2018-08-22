package net.jeemi.ri.tool.service.admin.command;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class AdminCommand<T>
{
	private final AdminCommandContext context;
	
	protected AdminCommand(AdminCommandContext context)
	{
		this.context = requireNonNull(context);
	}
	
	public final List<String> prepare(Random random)
	throws IOException
	{
		return Stream.of(
			context.prepare(random),
			singletonList(getName()),
			getArguments())
				.flatMap(List::stream)
				.collect(toList());
	}
	
	public final T processOutput(InputStream out)
	{
		throw new UnsupportedOperationException();
	}
	
	public final void processError(InputStream err)
	{
		throw new UnsupportedOperationException();
	}
	
	protected abstract String getName();
	
	protected abstract List<String> getArguments();
}
