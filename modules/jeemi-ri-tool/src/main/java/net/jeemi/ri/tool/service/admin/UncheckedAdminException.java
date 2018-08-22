package net.jeemi.ri.tool.service.admin;

public class UncheckedAdminException
extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public UncheckedAdminException(AdminException cause)
	{
		super(cause);
	}
	
	@Override
	public AdminException getCause()
	{
		return (AdminException) super.getCause();
	}
}
