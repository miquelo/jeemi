package net.jeemi.ri.agent;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.regex.Pattern;

public class EnterpriseDomainName
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
private static Pattern VALUE_PATTERN = Pattern.compile("^[\\w-_]+$");
	
	private final String value;
	
	@ConstructorProperties({
		"value"
	})
	public EnterpriseDomainName(String value)
	{
		if (!VALUE_PATTERN.matcher(value).matches())
			throw new IllegalArgumentException(
				String.format("Invalid value '%s'", value));
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj.getClass() == getClass())
		{
			EnterpriseDomainName name = (EnterpriseDomainName) obj;
			return value.equals(name.value);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return value;
	}

	public static EnterpriseDomainName of(String value)
	{
		return new EnterpriseDomainName(value);
	}
}
