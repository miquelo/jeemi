package net.jeemi.agent.assistant;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;

/**
 * Resource info.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ResourceInfo
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final ResourceName name;
	private final String factoryClassName;
	
	@ConstructorProperties({
		"name",
		"factoryClassName"
	})
	public ResourceInfo(ResourceName name, String factoryClassName)
	{
		this.name = Objects.requireNonNull(name);
		this.factoryClassName = Objects.requireNonNull(factoryClassName);
	}

	public ResourceName getName()
	{
		return name;
	}

	public String getFactoryClassName()
	{
		return factoryClassName;
	}
	
	public static BuilderName builder()
	{
		return new BuilderImpl();
	}
	
	private ResourceInfo(BuilderImpl builder)
	{
		name = builder.name;
		factoryClassName = builder.factoryClassName;
	}
	
	public interface BuilderName
	{
		BuilderFactoryClassName withName(ResourceName name);
	}
	
	public interface BuilderFactoryClassName
	{
		Builder withFactoryClassName(String factoryClassName);
	}
	
	public interface Builder
	{
		ResourceInfo build();
	}
	
	private static class BuilderImpl
	implements BuilderName, BuilderFactoryClassName, Builder
	{
		private ResourceName name;
		private String factoryClassName;
		
		public BuilderImpl()
		{
			name = null;
			factoryClassName = null;
		}
		
		@Override
		public BuilderFactoryClassName withName(ResourceName name)
		{
			this.name = Objects.requireNonNull(name);
			return this;
		}
		
		@Override
		public Builder withFactoryClassName(String factoryClassName)
		{
			this.factoryClassName = Objects.requireNonNull(factoryClassName);
			return this;
		}
		
		@Override
		public ResourceInfo build()
		{
			return new ResourceInfo(this);
		}
	}
}
