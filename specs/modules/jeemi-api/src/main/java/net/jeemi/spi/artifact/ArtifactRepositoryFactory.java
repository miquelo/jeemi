package net.jeemi.spi.artifact;

/**
 * Artifact repository factory.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ArtifactRepositoryFactory
{
	ArtifactRepository getRepository();
}
