package net.jeemi.ri.tool;

import static java.util.Optional.ofNullable;

import java.io.File;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
	name="jeemi-tool",
	description = "JEEMI Tool",
	mixinStandardHelpOptions = true,
	version = "0.1")
public class ApplicationFactory
implements Callable<Application>
{
	@Parameters(
		index="0",
		arity = "1",
		paramLabel = "COMMAND",
		description = "Command to be executed.")
    String commandName;
	
	@Parameters(
		index="1",
		arity = "*",
		paramLabel = "ARG",
		description = "Command arguments.")
    String[] args;
	
	@Option(
		names = { "-w", "--workingDir" },
		description = "Working directory",
		arity="1")
	private File workingDir;
	
	public ApplicationFactory()
	{
		commandName = null;
		args = new String[0];
		workingDir = null;
	}
	
	@Override
	public Application call()
	throws Exception
	{
		return new Application(commandName, args, workingDir);
	}
	
	public static void main(String[] args)
	throws Exception
	{
		ofNullable(CommandLine.call(new ApplicationFactory(), args))
			.ifPresent(application -> application.execute(
				new ApplicationCommandRegistry(),
				System.err));
	}
}