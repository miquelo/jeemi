package net.jeemi.artifact;

/**
 * Artifact coordinates format exception.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactCoordinatesFormatException
extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;
	
	public ArtifactCoordinatesFormatException()
	{
	}
	
	public ArtifactCoordinatesFormatException(String message)
	{
		super(message);
	}
	
	public ArtifactCoordinatesFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
