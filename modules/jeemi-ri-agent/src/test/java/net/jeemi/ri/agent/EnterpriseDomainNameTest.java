package net.jeemi.ri.agent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.EnterpriseDomainName;

public class EnterpriseDomainNameTest
{
	private static final String ANY_VALUE = "any-value";
	
	private static final String SOME_VALUE = "some-value";
	private static final String INVALID_VALUE = "invalid@value";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public EnterpriseDomainNameTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getValue()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(SOME_VALUE);
		
		String value = name.getValue();
		
		assertThat(value).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void hashCodeBasedOnValue()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(SOME_VALUE);
		
		int hashCode = name.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_VALUE.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(SOME_VALUE);
		EnterpriseDomainName anotherName = EnterpriseDomainName.of(SOME_VALUE);
		
		boolean equals = name.equals(anotherName);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(ANY_VALUE);
		EnterpriseDomainName anotherName = name;
		
		boolean equals = name.equals(anotherName);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(SOME_VALUE);
		Object anotherName = new Object();
		
		boolean equals = name.equals(anotherName);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringFromValue()
	throws Exception
	{
		EnterpriseDomainName name = EnterpriseDomainName.of(SOME_VALUE);
		
		String str = name.toString();
		
		assertThat(str).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void ofInvalidValue()
	throws Exception
	{
		assertThatThrownBy(() -> EnterpriseDomainName.of(INVALID_VALUE))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_VALUE);
	}
}
