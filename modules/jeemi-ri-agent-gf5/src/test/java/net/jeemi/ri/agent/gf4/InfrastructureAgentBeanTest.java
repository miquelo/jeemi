package net.jeemi.ri.agent.gf4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.InfrastructureAgent;
import net.jeemi.ri.agent.gf5.InfrastructureAgentBean;

public class InfrastructureAgentBeanTest
{
	private InfrastructureAgent agent;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public InfrastructureAgentBeanTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Before
	public void setUp()
	throws Exception
	{
		agent = new InfrastructureAgentBean();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void getDomains()
	throws Exception
	{
		agent.getDomains();
	}
}
