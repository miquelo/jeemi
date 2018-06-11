package net.jeemi.ri.agent.gf4.spi.machinery;

import static java.util.Objects.requireNonNull;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.security.PublicKey;

/**
 * DAS machine specifications.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class DASMachineSpecs
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final PublicKey adminKey;
	
	@ConstructorProperties({
		"adminKey"
	})
	public DASMachineSpecs(PublicKey adminKey)
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
		DASMachineSpecs build();
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
		public DASMachineSpecs build()
		{
			return new DASMachineSpecs(adminKey);
		}
	}
}
