package net.jeemi.artifact;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Artifact selector version minor that can come or not with a revision.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactSelectorVersionMinor
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final Pattern PATTERN = Pattern.compile(
			"^(\\d)(\\.(\\d))?$");
	
	private int value;
	private Integer revision;
	
	@ConstructorProperties({
		"value",
		"revision"
	})
	public ArtifactSelectorVersionMinor(int value, Integer revision)
	{
		this.value = value;
		this.revision = revision;
	}
	
	public int getValue()
	{
		return value;
	}

	public Optional<Integer> getRevision()
	{
		return Optional.ofNullable(revision);
	}

	@Override
	public int hashCode()
	{
		return value;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof ArtifactSelectorVersionMinor)
		{
			ArtifactSelectorVersionMinor minor =
					(ArtifactSelectorVersionMinor) obj;
			return value == minor.value &&
				getRevision().equals(minor.getRevision());
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(value);
		if (revision != null)
			str.append(".").append(revision);
		return str.toString();
	}
	
	public static ArtifactSelectorVersionMinor parseVersionMinor(String str)
	{
		Matcher matcher = PATTERN.matcher(str);
		if (matcher.find())
			return new ArtifactSelectorVersionMinor(
				Integer.parseInt(matcher.group(1)),
				Optional.ofNullable(matcher.group(3))
					.map(Integer::parseInt)
					.orElse(null));
		throw new ArtifactSelectorFormatException(
			String.format("Illegal artifact selector version minor %s", str));
	}
	
	public static BuilderValue builder()
	{
		return new BuilderImpl();
	}
	
	private ArtifactSelectorVersionMinor(BuilderImpl builder)
	{
		value = builder.value;
		revision = builder.revision;
	}
	
	public interface BuilderValue
	{
		Builder withValue(int value);
	}
	
	public interface Builder
	{
		Builder withRevision(Integer revision);
		
		ArtifactSelectorVersionMinor build();
	}
	
	private static class BuilderImpl
	implements BuilderValue, Builder
	{
		private int value;
		private Integer revision;
		
		public BuilderImpl()
		{
			value = 0;
			revision = null;
		}
		
		@Override
		public Builder withValue(int value)
		{
			this.value = value;
			return this;
		}

		@Override
		public Builder withRevision(Integer revision)
		{
			this.revision = revision;
			return this;
		}

		@Override
		public ArtifactSelectorVersionMinor build()
		{
			return new ArtifactSelectorVersionMinor(this);
		}
	}
}
