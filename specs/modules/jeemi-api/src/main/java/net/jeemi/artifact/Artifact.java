package net.jeemi.artifact;

import java.io.IOException;
import java.io.InputStream;

/**
 * Artifact.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface Artifact
{
	ArtifactCoordinates getCoordinates();
	
	InputStream open()
	throws IOException;
}
