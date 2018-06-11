package net.jeemi.ri.agent;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.EnterpriseDomainDef;
import net.jeemi.ri.agent.EnterpriseDomainName;

public class EnterpriseDomainDefTest
{
	private static final EnterpriseDomainName SOME_NAME =
			EnterpriseDomainName.of("some-name");
	@Rule
	public final MockitoRule mockitoRule;
	
	public EnterpriseDomainDefTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getName()
	throws Exception
	{
		EnterpriseDomainDef specs = new EnterpriseDomainDef(
			SOME_NAME);
		
		assertThat(specs.getName()).isEqualTo(SOME_NAME);
	}
}
