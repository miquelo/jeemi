package net.jeemi.spi.artifact;

/**
 * Artifact selector format exception.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactSelectorFormatException
extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;
	
	public ArtifactSelectorFormatException(String message)
	{
		super(message);
	}
}
