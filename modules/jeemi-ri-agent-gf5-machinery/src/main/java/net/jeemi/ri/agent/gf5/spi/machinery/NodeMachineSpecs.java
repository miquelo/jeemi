package net.jeemi.ri.agent.gf5.spi.machinery;

import static java.util.Objects.requireNonNull;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.security.PublicKey;

/**
 * Node machine specifications.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class NodeMachineSpecs
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final PublicKey adminKey;
	
	@ConstructorProperties({
		"adminKey"
	})
	public NodeMachineSpecs(PublicKey adminKey)
	{
		this.adminKey = requireNonNull(adminKey);
	}

	public PublicKey getAdminKey()
	{
		return adminKey;
	}
	
	public static BuilderAdminKey builder()
	{
		return new BuilderImpl();
	}
	
	public interface BuilderAdminKey
	{
		Builder withAdminKey(PublicKey adminKey);
	}
	
	public interface Builder
	{
		NodeMachineSpecs build();
	}
	
	private static class BuilderImpl implements BuilderAdminKey, Builder
	{
		private PublicKey adminKey;
		
		public BuilderImpl()
		{
			adminKey = null;
		}
		
		@Override
		public Builder withAdminKey(PublicKey adminKey)
		{
			this.adminKey = requireNonNull(adminKey);
			return this;
		}
		
		@Override
		public NodeMachineSpecs build()
		{
			return new NodeMachineSpecs(adminKey);
		}
	}
}
