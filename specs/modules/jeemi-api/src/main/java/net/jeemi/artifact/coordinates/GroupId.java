package net.jeemi.artifact.coordinates;

import java.io.Serializable;
import java.util.Objects;

/**
 * Artifact group identifier.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class GroupId
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String value;
	
	public GroupId(String value)
	{
		this.value = Objects.requireNonNull(value);
	}
}
