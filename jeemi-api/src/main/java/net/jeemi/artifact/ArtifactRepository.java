package net.jeemi.artifact;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Artifact repository.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ArtifactRepository
{
	Optional<Artifact> find(ArtifactSelector selector);
	
	InputStream fetch(Artifact artifact)
	throws IOException;
}
