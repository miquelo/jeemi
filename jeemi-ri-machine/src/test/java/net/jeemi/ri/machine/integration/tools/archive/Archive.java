package net.jeemi.ri.machine.integration.tools.archive;

import static java.util.Objects.requireNonNull;

public class Archive
{
	private final ArchiveType type;
	private final String name;
	
	private Archive(BuilderImpl builder)
	{
		type = builder.type;
		name = builder.name;
	}
	
	public ArchiveType getType()
	{
		return type;
	}

	public String getName()
	{
		return name;
	}
	
	public static BuilderType builder()
	{
		return new BuilderImpl();
	}

	public interface BuilderType
	{
		BuilderName withType(ArchiveType type);
	}
	
	public interface BuilderName
	{
		Builder withName(String name);
	}
	
	public interface Builder
	{
		Archive build();
	}
	
	private static class BuilderImpl
	implements BuilderType, BuilderName, Builder
	{
		private ArchiveType type;
		private String name;
		
		public BuilderImpl()
		{
			type = null;
			name = null;
		}
		
		@Override
		public BuilderName withType(ArchiveType type)
		{
			this.type = requireNonNull(type);
			return this;
		}
		
		@Override
		public Builder withName(String name)
		{
			this.name = requireNonNull(name);
			return this;
		}
		
		@Override
		public Archive build()
		{
			return new Archive(this);
		}
	}
}
