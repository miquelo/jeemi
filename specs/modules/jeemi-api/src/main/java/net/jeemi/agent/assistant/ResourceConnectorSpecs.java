package net.jeemi.agent.assistant;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;

/**
 * Resource connector specifications.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ResourceConnectorSpecs
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final ResourceConnectorName name;
	
	@ConstructorProperties({
		"name"
	})
	public ResourceConnectorSpecs(ResourceConnectorName name)
	{
		this.name = Objects.requireNonNull(name);
	}

	public ResourceConnectorName getName()
	{
		return name;
	}
	
	public static BuilderName builder()
	{
		return new BuilderImpl();
	}
	
	private ResourceConnectorSpecs(BuilderImpl builder)
	{
		name = builder.name;
	}
	
	public interface BuilderName
	{
		Builder withName(ResourceConnectorName name);
	}
	
	public interface Builder
	{
		ResourceConnectorSpecs build();
	}
	
	private static class BuilderImpl
	implements BuilderName, Builder
	{
		private ResourceConnectorName name;
		
		public BuilderImpl()
		{
			name = null;
		}

		@Override
		public Builder withName(ResourceConnectorName name)
		{
			this.name = Objects.requireNonNull(name);
			return this;
		}

		@Override
		public ResourceConnectorSpecs build()
		{
			return new ResourceConnectorSpecs(this);
		}
	}
}
