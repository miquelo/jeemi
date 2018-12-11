package net.jeemi.spi.artifact;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.stream.Stream;

import net.jeemi.spi.artifact.coordinates.ArtifactId;
import net.jeemi.spi.artifact.coordinates.GroupId;
import net.jeemi.spi.artifact.coordinates.Packaging;
import net.jeemi.spi.artifact.coordinates.RelativeVersion;

/**
 * Artifact selector.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactSelector
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final Packaging DEFAULT_PACKAGING = Packaging.JAR;
	
	private GroupId groupId;
	private ArtifactId artifactId;
	private RelativeVersion version;
	private Packaging packaging;
	
	@ConstructorProperties({
		"groupId",
		"artifactId",
		"version",
		"packaging"
	})
	public ArtifactSelector(GroupId groupId, ArtifactId artifactId,
			RelativeVersion version, Packaging packaging)
	{
		this.groupId = requireNonNull(groupId);
		this.artifactId = requireNonNull(artifactId);
		this.version = requireNonNull(version);
		this.packaging = ofNullable(packaging)
			.orElse(DEFAULT_PACKAGING);
	}

	public GroupId getGroupId()
	{
		return groupId;
	}

	public ArtifactId getArtifactId()
	{
		return artifactId;
	}
	
	public RelativeVersion getVersion()
	{
		return version;
	}

	public Packaging getPackaging()
	{
		return packaging;
	}
	
	@Override
	public String toString()
	{
		return Stream.of(groupId, artifactId, version, packaging)
			.map(Object::toString)
			.collect(joining(":"));
	}
	
	public static BuilderGroupId builder()
	{
		return new BuilderImpl();
	}
	
	public interface BuilderGroupId
	{
		BuilderArtifactId withGroupId(GroupId groupId);
	}
	
	public interface BuilderArtifactId
	{
		BuilderVersion withArtifactId(ArtifactId artifactId);
	}
	
	public interface BuilderVersion
	{
		Builder withVersion(RelativeVersion version);
	}
	
	public interface Builder
	{
		Builder withPackaging(Packaging packaging);
		
		ArtifactSelector build();
	}
	
	private static class BuilderImpl
	implements BuilderGroupId, BuilderArtifactId, BuilderVersion, Builder
	{
		private GroupId groupId;
		private ArtifactId artifactId;
		private RelativeVersion version;
		private Packaging packaging;
		
		public BuilderImpl()
		{
			groupId = null;
			artifactId = null;
			version = null;
			packaging = null;
		}
		
		@Override
		public BuilderArtifactId withGroupId(GroupId groupId)
		{
			this.groupId = requireNonNull(groupId);
			return this;
		}
		
		@Override
		public BuilderVersion withArtifactId(ArtifactId artifactId)
		{
			this.artifactId = requireNonNull(artifactId);
			return this;
		}
		
		@Override
		public Builder withVersion(RelativeVersion version)
		{
			this.version = requireNonNull(version);
			return this;
		}
		
		@Override
		public Builder withPackaging(Packaging packaging)
		{
			this.packaging = requireNonNull(packaging);
			return this;
		}

		@Override
		public ArtifactSelector build()
		{
			return new ArtifactSelector(
				groupId,
				artifactId,
				version,
				packaging);
		}
	}
}
