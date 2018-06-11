package net.jeemi.ri.tool.service;

public class InfrastructureServiceManagerException
extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public InfrastructureServiceManagerException(String message)
	{
		super(message);
	}
	
	public InfrastructureServiceManagerException(Throwable cause)
	{
		super(cause);
	}
	
	public InfrastructureServiceManagerException(String message, Throwable cause)
	{
		super(message, cause);
	}
}