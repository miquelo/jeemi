package net.jeemi.agent.assistant;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Objects;

/**
 * Resource connector info.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ResourceConnectorInfo
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final ResourceConnectorName name;
	
	@ConstructorProperties({
		"name"
	})
	public ResourceConnectorInfo(ResourceConnectorName name)
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
	
	private ResourceConnectorInfo(BuilderImpl builder)
	{
		name = builder.name;
	}
	
	public interface BuilderName
	{
		Builder withName(ResourceConnectorName name);
	}
	
	public interface Builder
	{
		ResourceConnectorInfo build();
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
		public ResourceConnectorInfo build()
		{
			return new ResourceConnectorInfo(this);
		}
	}
}
