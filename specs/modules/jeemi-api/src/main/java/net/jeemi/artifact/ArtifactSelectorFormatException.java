package net.jeemi.artifact;

/**
 * Artifact selector format exception.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactSelectorFormatException
extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;
	
	public ArtifactSelectorFormatException()
	{
	}
	
	public ArtifactSelectorFormatException(String message)
	{
		super(message);
	}
	
	public ArtifactSelectorFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
