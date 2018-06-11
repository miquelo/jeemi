package net.jeemi.ri.agent;

import static java.util.Objects.requireNonNull;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class EnterpriseDomainDef
implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final EnterpriseDomainName name;
	
	@ConstructorProperties({
		"name"
	})
	public EnterpriseDomainDef(EnterpriseDomainName name)
	{
		this.name = requireNonNull(name);
	}

	public EnterpriseDomainName getName()
	{
		return name;
	}
}
