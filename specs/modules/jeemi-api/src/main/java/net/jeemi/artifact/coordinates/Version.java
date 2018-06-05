package net.jeemi.artifact.coordinates;

import java.io.Serializable;
import java.util.Objects;

/**
 * Artifact version.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class Version
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String value;
	
	public Version(String value)
	{
		this.value = Objects.requireNonNull(value);
	}
}
