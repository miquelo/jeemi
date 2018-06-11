package net.jeemi.ri.tool.commands;

import net.jeemi.ri.tool.ApplicationCommand;
import net.jeemi.ri.tool.ApplicationCommandFactory;
import picocli.CommandLine.Command;

@Command(
	name="jeemi-tool prepare",
	description = "Prepare a JEEMI service",
	mixinStandardHelpOptions = true,
	version = "0.1")
public class PrepareApplicationCommandFactory
extends ApplicationCommandFactory
{
	public PrepareApplicationCommandFactory()
	{
	}
	
	@Override
	protected ApplicationCommand getCommand()
	{
		return new PrepareApplicationCommand();
	}
}