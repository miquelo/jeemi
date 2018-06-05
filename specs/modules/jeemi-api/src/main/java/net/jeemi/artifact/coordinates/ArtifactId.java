package net.jeemi.artifact.coordinates;

import java.io.Serializable;
import java.util.Objects;

/**
 * Artifact identifier.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactId
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String value;
	
	public ArtifactId(String value)
	{
		this.value = Objects.requireNonNull(value);
	}
}
