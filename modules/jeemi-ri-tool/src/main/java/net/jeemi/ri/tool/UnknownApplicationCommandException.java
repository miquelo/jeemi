package net.jeemi.ri.tool;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class UnknownApplicationCommandException
extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private final String commandName;
	
	public UnknownApplicationCommandException(String commandName)
	{
		super(format("Unknown command %s", requireNonNull(commandName)));
		this.commandName = commandName;
	}

	public String getCommandName()
	{
		return commandName;
	}
}