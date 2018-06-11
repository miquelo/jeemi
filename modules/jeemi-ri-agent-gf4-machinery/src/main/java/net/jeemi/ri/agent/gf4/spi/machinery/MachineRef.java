package net.jeemi.ri.agent.gf4.spi.machinery;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Machine reference.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class MachineRef
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static Pattern VALUE_PATTERN = Pattern.compile("^\\w[\\w-_]+$");
	
	private final String value;
	
	@ConstructorProperties({
		"value"
	})
	public MachineRef(String value)
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
			MachineRef ref = (MachineRef) obj;
			return value.equals(ref.value);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return value;
	}

	public static MachineRef of(String value)
	{
		return new MachineRef(value);
	}
}
