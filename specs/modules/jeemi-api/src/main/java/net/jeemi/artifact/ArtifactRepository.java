package net.jeemi.artifact;

import java.util.Optional;

/**
 * Artifact repository.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ArtifactRepository
{
	Optional<Artifact> fetch(ArtifactCoordinates coords);
}
