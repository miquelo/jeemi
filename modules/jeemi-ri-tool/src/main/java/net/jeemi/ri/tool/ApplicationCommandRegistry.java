package net.jeemi.ri.tool;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import net.jeemi.ri.tool.commands.PrepareApplicationCommandFactory;

public class ApplicationCommandRegistry
{
	private final Map<String, ApplicationCommandFactory> commandFactoryMap;
	
	public ApplicationCommandRegistry()
	{
		commandFactoryMap = Stream.of(
			new SimpleEntry<>(
				"prepare",
				new PrepareApplicationCommandFactory()))
			.collect(toMap(Entry::getKey, Entry::getValue));
	}
	
	public ApplicationCommandFactory getCommandFactory(String commandName)
	{
		return ofNullable(commandFactoryMap.get(commandName))
			.orElseThrow(() ->
				new UnknownApplicationCommandException(commandName));
	}
}
