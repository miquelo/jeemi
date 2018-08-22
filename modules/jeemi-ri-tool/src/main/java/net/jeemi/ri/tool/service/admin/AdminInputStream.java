package net.jeemi.ri.tool.service.admin;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;

public class AdminInputStream
extends InputStream
{
	private final InputStream input;
	
	public AdminInputStream(InputStream input)
	{
		this.input = requireNonNull(input);
	}
	
	@Override
	public int read()
	throws IOException
	{
		return 0;
	}
}
