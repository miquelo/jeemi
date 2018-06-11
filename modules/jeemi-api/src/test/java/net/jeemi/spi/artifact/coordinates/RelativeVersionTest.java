package net.jeemi.spi.artifact.coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class RelativeVersionTest
{
	private static final String ANY_VALUE = "any-value";
	
	private static final String SOME_VALUE = "some-value";
	private static final String INVALID_VALUE = "invalid@value";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public RelativeVersionTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getValue()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(SOME_VALUE);
		
		String value = version.getValue();
		
		assertThat(value).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void hashCodeBasedOnValue()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(SOME_VALUE);
		
		int hashCode = version.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_VALUE.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(SOME_VALUE);
		RelativeVersion anotherVersion = RelativeVersion.of(SOME_VALUE);
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(ANY_VALUE);
		RelativeVersion anotherVersion = version;
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(SOME_VALUE);
		Object anotherVersion = new Object();
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringFromValue()
	throws Exception
	{
		RelativeVersion version = RelativeVersion.of(SOME_VALUE);
		
		String str = version.toString();
		
		assertThat(str).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void ofInvalidValue()
	throws Exception
	{
		assertThatThrownBy(() -> RelativeVersion.of(INVALID_VALUE))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_VALUE);
	}
}
