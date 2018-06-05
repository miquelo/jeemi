package net.jeemi.artifact;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.jeemi.artifact.coordinates.ArtifactId;
import net.jeemi.artifact.coordinates.GroupId;
import net.jeemi.artifact.coordinates.Packaging;
import net.jeemi.artifact.coordinates.VersionWants;

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
	private VersionWants versionWants;
	private Packaging packaging;
	
	@ConstructorProperties({
		"groupId",
		"artifactId",
		"versionWants",
		"packaging"
	})
	public ArtifactSelector(GroupId groupId, ArtifactId artifactId,
			VersionWants versionWants, Packaging packaging)
	{
		this.groupId = Objects.requireNonNull(groupId);
		this.artifactId = Objects.requireNonNull(artifactId);
		this.versionWants = Objects.requireNonNull(versionWants);
		this.packaging = Optional.ofNullable(packaging)
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
	
	public VersionWants getVersionWants()
	{
		return versionWants;
	}

	public Packaging getPackaging()
	{
		return packaging;
	}
	
	@Override
	public String toString()
	{
		return Stream.of(
			groupId.toString(),
			artifactId.toString(),
			versionWants.toString(),
			packaging.name())
		.collect(Collectors.joining(":"));
	}
	
	public static BuilderGroupId builder()
	{
		return new BuilderImpl();
	}
	
	private ArtifactSelector(BuilderImpl builder)
	{
		this(
			builder.groupId,
			builder.artifactId,
			builder.versionWants,
			builder.packaging);
	}
	
	public interface BuilderGroupId
	{
		BuilderArtifactId withGroupId(GroupId groupId);
	}
	
	public interface BuilderArtifactId
	{
		BuilderVersionWants withArtifactId(ArtifactId artifactId);
	}
	
	public interface BuilderVersionWants
	{
		Builder withVersionWants(VersionWants versionWants);
	}
	
	public interface Builder
	{
		Builder withPackaging(Packaging packaging);
		
		ArtifactSelector build();
	}
	
	private static class BuilderImpl
	implements BuilderGroupId, BuilderArtifactId, BuilderVersionWants, Builder
	{
		private GroupId groupId;
		private ArtifactId artifactId;
		private VersionWants versionWants;
		private Packaging packaging;
		
		public BuilderImpl()
		{
			groupId = null;
			artifactId = null;
			versionWants = null;
			packaging = null;
		}
		
		@Override
		public BuilderArtifactId withGroupId(GroupId groupId)
		{
			this.groupId = Objects.requireNonNull(groupId);
			return this;
		}
		
		@Override
		public BuilderVersionWants withArtifactId(ArtifactId artifactId)
		{
			this.artifactId = Objects.requireNonNull(artifactId);
			return this;
		}
		
		@Override
		public Builder withVersionWants(VersionWants versionWants)
		{
			this.versionWants = Objects.requireNonNull(versionWants);
			return this;
		}
		
		@Override
		public Builder withPackaging(Packaging packaging)
		{
			this.packaging = Objects.requireNonNull(packaging);
			return this;
		}

		@Override
		public ArtifactSelector build()
		{
			return new ArtifactSelector(this);
		}
	}
}
