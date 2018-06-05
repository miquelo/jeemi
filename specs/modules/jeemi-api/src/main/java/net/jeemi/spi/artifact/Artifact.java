package net.jeemi.spi.artifact;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.stream.Stream;

import net.jeemi.spi.artifact.coordinates.ArtifactId;
import net.jeemi.spi.artifact.coordinates.GroupId;
import net.jeemi.spi.artifact.coordinates.Packaging;
import net.jeemi.spi.artifact.coordinates.Version;

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
	
	@ConstructorProperties({
		"groupId",
		"artifactId",
		"version",
		"packaging"
	})
	public Artifact(GroupId groupId, ArtifactId artifactId, Version version,
			Packaging packaging)
	{
		this.groupId = requireNonNull(groupId);
		this.artifactId = requireNonNull(artifactId);
		this.version = requireNonNull(version);
		this.packaging = requireNonNull(packaging);
	}

	public GroupId getGroupId()
	{
		return groupId;
	}

	public ArtifactId getArtifactId()
	{
		return artifactId;
	}

	public Version getVersion()
	{
		return version;
	}

	public Packaging getPackaging()
	{
		return packaging;
	}
	
	@Override
	public int hashCode()
	{
		return groupId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj.getClass() == getClass())
		{
			Artifact artifact = (Artifact) obj;
			return groupId.equals(artifact.groupId)
					&& artifactId.equals(artifact.artifactId)
					&& version.equals(artifact.version)
					&& packaging.equals(artifact.packaging);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return Stream.of(groupId, artifactId, version, packaging)
			.map(Object::toString)
			.collect(joining(":"));
	}
}
