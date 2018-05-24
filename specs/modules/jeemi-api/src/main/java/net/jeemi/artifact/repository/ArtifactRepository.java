package net.jeemi.artifact.repository;

import java.util.Optional;

import net.jeemi.artifact.Artifact;
import net.jeemi.artifact.ArtifactCoordinates;

/**
 * Artifact repository.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ArtifactRepository
{
	Optional<Artifact> fetch(ArtifactCoordinates coords);
}
