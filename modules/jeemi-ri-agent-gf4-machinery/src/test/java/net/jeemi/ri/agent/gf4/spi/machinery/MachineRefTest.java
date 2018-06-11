package net.jeemi.ri.agent.gf4.spi.machinery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.gf4.spi.machinery.MachineRef;

public class MachineRefTest
{
	private static final String ANY_VALUE = "any-value";
	
	private static final String SOME_VALUE = "some-value";
	private static final String INVALID_VALUE = "-invalid-value";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public MachineRefTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getValue()
	throws Exception
	{
		MachineRef ref = MachineRef.of(SOME_VALUE);
		
		String value = ref.getValue();
		
		assertThat(value).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void hashCodeBasedOnValue()
	throws Exception
	{
		MachineRef ref = MachineRef.of(SOME_VALUE);
		
		int hashCode = ref.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_VALUE.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		MachineRef ref = MachineRef.of(SOME_VALUE);
		MachineRef anotherRef = MachineRef.of(SOME_VALUE);
		
		boolean equals = ref.equals(anotherRef);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		MachineRef ref = MachineRef.of(ANY_VALUE);
		MachineRef anotherRef = ref;
		
		boolean equals = ref.equals(anotherRef);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		MachineRef ref = MachineRef.of(SOME_VALUE);
		Object anotherRef = new Object();
		
		boolean equals = ref.equals(anotherRef);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringFromValue()
	throws Exception
	{
		MachineRef ref = MachineRef.of(SOME_VALUE);
		
		String str = ref.toString();
		
		assertThat(str).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void ofInvalidValue()
	throws Exception
	{
		assertThatThrownBy(() -> MachineRef.of(INVALID_VALUE))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_VALUE);
	}
}
