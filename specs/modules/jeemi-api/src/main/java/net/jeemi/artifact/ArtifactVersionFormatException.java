package net.jeemi.artifact;

/**
 * Artifact version format exception.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactVersionFormatException
extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;
	
	public ArtifactVersionFormatException()
	{
	}
	
	public ArtifactVersionFormatException(String message)
	{
		super(message);
	}
	
	public ArtifactVersionFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
