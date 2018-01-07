package net.jeemi.artifact;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Artifact coordinates.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactCoordinates
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final Pattern PATTERN = Pattern.compile(
			"^(.+):(.+):(.+)(:(.+))?$");
	
	public static final ArtifactType DEFAULT_TYPE = ArtifactType.JAR;
	
	private String groupId;
	private String artifactId;
	private ArtifactVersion version;
	private ArtifactType type;
	
	@ConstructorProperties({
		"groupId",
		"artifactId",
		"version",
		"type"
	})
	public ArtifactCoordinates(String groupId, String artifactId,
			ArtifactVersion version, ArtifactType type)
	{
		this.groupId = Objects.requireNonNull(groupId);
		this.artifactId = Objects.requireNonNull(artifactId);
		this.version = Objects.requireNonNull(version);
		this.type = type == null ? DEFAULT_TYPE : type;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public String getArtifactId()
	{
		return artifactId;
	}
	
	public ArtifactVersion getVersion()
	{
		return version;
	}

	public ArtifactType getType()
	{
		return type;
	}
	
	@Override
	public int hashCode()
	{
		return groupId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ArtifactCoordinates)
		{
			ArtifactCoordinates coords = (ArtifactCoordinates) obj;
			return groupId.equals(coords.groupId)
					&& artifactId.equals(coords.artifactId)
					&& version.equals(coords.version)
					&& type.equals(coords.type);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return Stream.of(groupId, artifactId, version.toString(), type.name())
			.collect(Collectors.joining(":"));
	}
	
	public static BuilderGroupId builder()
	{
		return new BuilderImpl();
	}
	
	public static ArtifactCoordinates parseCoordinates(String str)
	{
		Matcher matcher = PATTERN.matcher(str);
		if (matcher.find())
		{
			String groupId = matcher.group(1);
			String artifactId = matcher.group(2);
			ArtifactVersion version = parseVersion(matcher.group(3));
			String typeName = matcher.group(5);
			return new ArtifactCoordinates(
				groupId,
				artifactId,
				version,
				typeName == null ? null : lookupType(str, typeName));
		}
		throw new ArtifactCoordinatesFormatException(
				formatExceptionMessage(str));
	}
	
	private ArtifactCoordinates(BuilderImpl builder)
	{
		groupId = builder.groupId;
		artifactId = builder.artifactId;
		version = builder.version;
		type = builder.type;
	}
	
	private static ArtifactVersion parseVersion(String str)
	{
		try
		{
			return ArtifactVersion.parseVersion(str);
		}
		catch (ArtifactVersionFormatException exception)
		{
			throw new ArtifactCoordinatesFormatException(
				formatExceptionMessage(str),
				exception);
		}
	}
	
	private static ArtifactType lookupType(String str, String typeName)
	{
		try
		{
			return ArtifactType.lookup(typeName);
		}
		catch (UnknownArtifactTypeException exception)
		{
			throw new ArtifactCoordinatesFormatException(
					formatExceptionMessage(str),
					exception);
		}
	}
	
	private static String formatExceptionMessage(String str)
	{
		return String.format("Illegal artifact coordinates %s", str);
	}
	
	public interface BuilderGroupId
	{
		BuilderArtifactId withGroupId(String groupId);
	}
	
	public interface BuilderArtifactId
	{
		BuilderVersion withArtifactId(String artifactId);
	}
	
	public interface BuilderVersion
	{
		Builder withVersion(ArtifactVersion version);
	}
	
	public interface Builder
	{
		Builder withType(ArtifactType type);
		
		ArtifactCoordinates build();
	}
	
	private static class BuilderImpl
	implements BuilderGroupId, BuilderArtifactId, BuilderVersion, Builder
	{
		private String groupId;
		private String artifactId;
		private ArtifactVersion version;
		private ArtifactType type;
		
		public BuilderImpl()
		{
			groupId = null;
			artifactId = null;
			version = null;
			type = DEFAULT_TYPE;
		}
		
		@Override
		public BuilderArtifactId withGroupId(String groupId)
		{
			this.groupId = Objects.requireNonNull(groupId);
			return this;
		}
		
		@Override
		public BuilderVersion withArtifactId(String artifactId)
		{
			this.artifactId = Objects.requireNonNull(artifactId);
			return this;
		}
		
		@Override
		public Builder withVersion(ArtifactVersion version)
		{
			this.version = Objects.requireNonNull(version);
			return this;
		}
		
		@Override
		public Builder withType(ArtifactType type)
		{
			this.type = Objects.requireNonNull(type);
			return this;
		}

		@Override
		public ArtifactCoordinates build()
		{
			return new ArtifactCoordinates(this);
		}
	}
}
