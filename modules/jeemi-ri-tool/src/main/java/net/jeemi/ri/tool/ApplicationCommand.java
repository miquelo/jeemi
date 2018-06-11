package net.jeemi.ri.tool;

public interface ApplicationCommand
{
	void execute(ApplicationContext context)
	throws ApplicationException;
}