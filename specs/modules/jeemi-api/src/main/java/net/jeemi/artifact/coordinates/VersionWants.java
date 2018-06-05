package net.jeemi.artifact.coordinates;

import java.io.Serializable;
import java.util.Objects;

/**
 * Artifact version wants.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class VersionWants
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String value;
	
	public VersionWants(String value)
	{
		this.value = Objects.requireNonNull(value);
	}
}
