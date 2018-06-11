package net.jeemi.ri.tool;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static picocli.CommandLine.call;

import java.io.File;
import java.io.PrintStream;

public class Application
{
    private final String commandName;
    private String[] args;
    private final File workingDir;
	
	public Application(String commandName, String[] args, File workingDir)
	{
		this.commandName = requireNonNull(commandName);
		this.args = requireNonNull(args);
		this.workingDir = workingDir;
	}
	
	public void execute(ApplicationCommandRegistry cmdReg, PrintStream err)
	throws ApplicationException
	{
		try
		{
			call(cmdReg.getCommandFactory(commandName), args)
				.execute(ApplicationContext.builder()
					.withWorkingDir(ofNullable(workingDir)
						.orElse(new File(getProperty("user.dir"))))
					.build());
		}
		catch (UnknownApplicationCommandException exception)
		{
			err.println(
				format("Unknown command %s", exception.getCommandName()));
		}
	}
}