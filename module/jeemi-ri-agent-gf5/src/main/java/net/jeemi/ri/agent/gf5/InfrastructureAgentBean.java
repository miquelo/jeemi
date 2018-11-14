package net.jeemi.ri.agent.gf5;

import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;

import net.jeemi.ri.agent.EnterpriseDomainDef;
import net.jeemi.ri.agent.InfrastructureAgent;

@Local
@Stateless
public class InfrastructureAgentBean
implements InfrastructureAgent
{
	public InfrastructureAgentBean()
	{
	}
	
	@Override
	public Set<EnterpriseDomainDef> getDomains()
	{
		throw new UnsupportedOperationException();
	}
}
