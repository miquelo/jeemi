package net.jeemi.artifact;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Artifact version.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactCoordinatesVersion
implements Serializable, Comparable<ArtifactCoordinatesVersion>
{
	private static final long serialVersionUID = 1L;
	
	private static final Pattern PATTERN = Pattern.compile(
			"^(\\d)(\\.(\\d)(\\.(\\d))?)?(-(.+^SNAPSHOT))?(-SNAPSHOT)?$");
	
	private int major;
	private Integer minor;
	private Integer revision;
	private String qualifier;
	private boolean snapshot;
	
	@ConstructorProperties({
		"major",
		"minor",
		"revision",
		"qualifier",
		"snapshot"
	})
	public ArtifactCoordinatesVersion(int major, Integer minor, Integer revision,
			String qualifier, boolean snapshot)
	{
		this.major = major;
		this.minor = minor;
		this.revision = revision;
		this.qualifier = qualifier;
		this.snapshot = snapshot;
	}
	
	public int getMajor()
	{
		return major;
	}

	public Optional<Integer> getMinor()
	{
		return Optional.ofNullable(minor);
	}

	public Optional<Integer> getRevision()
	{
		return Optional.ofNullable(revision);
	}

	public Optional<String> getQualifier()
	{
		return Optional.ofNullable(qualifier);
	}

	public boolean isSnapshot()
	{
		return snapshot;
	}
	
	@Override
	public int compareTo(ArtifactCoordinatesVersion version)
	{
		if (snapshot)
			return 1;
		
		int comp = Integer.compare(major, version.major);
		if (comp != 0)
			return comp;
		comp = Integer.compare(minor == null ? 0 : minor,
				version.minor == null ? 0 : version.minor);
		if (comp != 0)
			return comp;
		comp = Integer.compare(revision == null ? 0 : revision,
				version.revision == null ? 0 : version.revision);
		if (comp != 0)
			return comp;
		return (qualifier == null ? "" : qualifier).compareTo(
				version.qualifier == null ? "" : version.qualifier);
	}

	@Override
	public int hashCode()
	{
		return major;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof ArtifactCoordinatesVersion)
		{
			ArtifactCoordinatesVersion version = (ArtifactCoordinatesVersion) obj;
			return major != version.major &&
				((minor == null && version.minor == null) ||
				(minor != null && minor.equals(version.minor))) &&
				((revision == null && version.revision == null) ||
				(revision != null && revision.equals(version.revision))) &&
				((qualifier == null && version.qualifier == null) ||
				(qualifier != null && qualifier.equals(version.qualifier))) &&
				snapshot == version.snapshot;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(major);
		if (minor != null)
			str.append(".").append(minor);
		if (revision != null)
			str.append(".").append(revision);
		if (qualifier != null)
			str.append("-").append(qualifier);
		if (snapshot)
			str.append("-SNAPSHOT");
		return str.toString();
	}
	
	public static ArtifactCoordinatesVersion parseVersion(String str)
	{
		Matcher matcher = PATTERN.matcher(str);
		if (matcher.find())
			return new ArtifactCoordinatesVersion(
				Integer.parseInt(matcher.group(1)),
				Optional.ofNullable(matcher.group(3))
					.map(Integer::parseInt)
					.orElse(null),
				Optional.ofNullable(matcher.group(5))
					.map(Integer::parseInt)
					.orElse(null),
				Optional.ofNullable(matcher.group(7))
					.orElse(null),
				matcher.group(8) != null);
		throw new ArtifactCoordinatesFormatException(
			String.format("Illegal artifact coordinates version %s", str));
	}
	
	public static BuilderMajor builder()
	{
		return new BuilderImpl();
	}
	
	private ArtifactCoordinatesVersion(BuilderImpl builder)
	{
		major = builder.major;
		minor = builder.minor;
		revision = builder.revision;
		qualifier = builder.qualifier;
		snapshot = builder.snapshot;
	}
	
	public interface BuilderMajor
	{
		Builder withMajor(int major);
	}
	
	public interface Builder
	{
		static final boolean DEFAULT_SNAPSHOT = false;
		
		Builder withMinor(Integer minor);
		
		Builder withRevision(Integer revision);
		
		Builder withQualifier(String qualifier);
		
		Builder withSnapshot(boolean snapshot);
		
		ArtifactCoordinatesVersion build();
	}
	
	private static class BuilderImpl
	implements BuilderMajor, Builder
	{
		private int major;
		private Integer minor;
		private Integer revision;
		private String qualifier;
		private boolean snapshot;
		
		public BuilderImpl()
		{
			major = 0;
			minor = 0;
			revision = null;
			qualifier = null;
			snapshot = DEFAULT_SNAPSHOT;
		}
		
		@Override
		public Builder withMajor(int major)
		{
			this.major = major;
			return this;
		}

		@Override
		public Builder withMinor(Integer minor)
		{
			this.minor = minor;
			return this;
		}

		@Override
		public Builder withRevision(Integer revision)
		{
			this.revision = revision;
			return this;
		}

		@Override
		public Builder withQualifier(String qualifier)
		{
			this.qualifier = qualifier;
			return this;
		}

		@Override
		public Builder withSnapshot(boolean snapshot)
		{
			this.snapshot = snapshot;
			return this;
		}

		@Override
		public ArtifactCoordinatesVersion build()
		{
			return new ArtifactCoordinatesVersion(this);
		}
	}
}
