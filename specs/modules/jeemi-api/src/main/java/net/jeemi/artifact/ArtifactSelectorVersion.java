package net.jeemi.artifact;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Artifact selector version.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactSelectorVersion
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final Pattern PATTERN = Pattern.compile(
			"^(\\d)(\\.(\\d)(\\.(\\d))?)?(-(.+))?$");
	
	public static final String SNAPSHOT_QUALIFIER = "SNAPSHOT";
	
	private int major;
	private ArtifactSelectorVersionMinor minor;
	private String qualifier;
	
	@ConstructorProperties({
		"major",
		"minor",
		"qualifier"
	})
	public ArtifactSelectorVersion(int major,
			ArtifactSelectorVersionMinor minor, String qualifier)
	{
		this.major = major;
		this.minor = minor;
		this.qualifier = qualifier;
	}
	
	public int getMajor()
	{
		return major;
	}

	public Optional<ArtifactSelectorVersionMinor> getMinor()
	{
		return Optional.ofNullable(minor);
	}

	public Optional<String> getQualifier()
	{
		return Optional.ofNullable(qualifier);
	}

	public boolean isSnapshot()
	{
		return getQualifier()
			.map(SNAPSHOT_QUALIFIER::equals)
			.orElse(false);
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
		if (obj instanceof ArtifactSelectorVersion)
		{
			ArtifactSelectorVersion version = (ArtifactSelectorVersion) obj;
			return major == version.major &&
				getMinor().equals(version.getMinor()) &&
				getQualifier().equals(version.getQualifier());
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
		if (qualifier != null)
			str.append("-").append(qualifier);
		return str.toString();
	}
	
	public static ArtifactSelectorVersion parseVersion(String str)
	{
		Matcher matcher = PATTERN.matcher(str);
		if (matcher.find())
			return new ArtifactSelectorVersion(
				Integer.parseInt(matcher.group(1)),
				Optional.ofNullable(matcher.group(3))
					.map(ArtifactSelectorVersionMinor::parseVersionMinor)
					.orElse(null),
				Optional.ofNullable(matcher.group(7))
					.orElse(null));
		throw new ArtifactSelectorFormatException(
			String.format("Illegal artifact selector version %s", str));
	}
	
	public static BuilderMajor builder()
	{
		return new BuilderImpl();
	}
	
	private ArtifactSelectorVersion(BuilderImpl builder)
	{
		major = builder.major;
		minor = builder.minor;
		qualifier = builder.qualifier;
	}
	
	public interface BuilderMajor
	{
		Builder withMajor(int major);
	}
	
	public interface Builder
	{
		Builder withMinor(ArtifactSelectorVersionMinor minor);
		
		Builder withQualifier(String qualifier);
		
		ArtifactSelectorVersion build();
	}
	
	private static class BuilderImpl
	implements BuilderMajor, Builder
	{
		private int major;
		private ArtifactSelectorVersionMinor minor;
		private String qualifier;
		
		public BuilderImpl()
		{
			major = 0;
			minor = null;
			qualifier = null;
		}
		
		@Override
		public Builder withMajor(int major)
		{
			this.major = major;
			return this;
		}

		@Override
		public Builder withMinor(ArtifactSelectorVersionMinor minor)
		{
			this.minor = minor;
			return this;
		}

		@Override
		public Builder withQualifier(String qualifier)
		{
			this.qualifier = qualifier;
			return this;
		}

		@Override
		public ArtifactSelectorVersion build()
		{
			return new ArtifactSelectorVersion(this);
		}
	}
}
