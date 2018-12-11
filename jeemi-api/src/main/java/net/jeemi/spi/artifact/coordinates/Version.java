package net.jeemi.spi.artifact.coordinates;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Artifact version.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class Version
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static Pattern VALUE_PATTERN = Pattern.compile("^[\\w-_\\.]+$");
	
	private final String value;
	
	@ConstructorProperties({
		"value"
	})
	public Version(String value)
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
			Version version = (Version) obj;
			return value.equals(version.value);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return value;
	}

	public static Version of(String value)
	{
		return new Version(value);
	}
}
