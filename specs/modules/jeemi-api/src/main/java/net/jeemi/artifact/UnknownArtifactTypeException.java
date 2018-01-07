package net.jeemi.artifact;

import java.util.Objects;

/**
 * Artifact version format exception.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class UnknownArtifactTypeException
extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private final String name;
	
	public UnknownArtifactTypeException(String name)
	{
		this.name = Objects.requireNonNull(name);
	}

	public String getName()
	{
		return name;
	}
}
