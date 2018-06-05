package net.jeemi.artifact;

import java.io.Serializable;
import java.util.Objects;

import net.jeemi.artifact.coordinates.ArtifactId;
import net.jeemi.artifact.coordinates.GroupId;
import net.jeemi.artifact.coordinates.Packaging;
import net.jeemi.artifact.coordinates.Version;

/**
 * Artifact.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class Artifact
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final GroupId groupId;
	private final ArtifactId artifactId;
	private final Version version;
	private final Packaging packaging;
	
	public Artifact(ArtifactId artifactId, GroupId groupId, Version version,
			Packaging packaging)
	{
		this.groupId = Objects.requireNonNull(groupId);
		this.artifactId = Objects.requireNonNull(artifactId);
		this.version = Objects.requireNonNull(version);
		this.packaging = Objects.requireNonNull(packaging);
	}
}
