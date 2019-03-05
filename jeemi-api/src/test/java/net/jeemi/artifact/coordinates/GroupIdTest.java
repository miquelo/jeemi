package net.jeemi.artifact.coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.artifact.coordinates.GroupId;

public class GroupIdTest
{
	private static final String ANY_VALUE = "any-value";
	
	private static final String SOME_VALUE = "some-value";
	private static final String INVALID_VALUE = "invalid@value";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public GroupIdTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getValue()
	throws Exception
	{
		GroupId groupId = GroupId.of(SOME_VALUE);
		
		String value = groupId.getValue();
		
		assertThat(value).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void hashCodeBasedOnValue()
	throws Exception
	{
		GroupId groupId = GroupId.of(SOME_VALUE);
		
		int hashCode = groupId.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_VALUE.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		GroupId groupId = GroupId.of(SOME_VALUE);
		GroupId anotherGroupId = GroupId.of(SOME_VALUE);
		
		boolean equals = groupId.equals(anotherGroupId);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		GroupId groupId = GroupId.of(ANY_VALUE);
		GroupId anotherGroupId = groupId;
		
		boolean equals = groupId.equals(anotherGroupId);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		GroupId groupId = GroupId.of(SOME_VALUE);
		Object anotherGroupId = new Object();
		
		boolean equals = groupId.equals(anotherGroupId);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringFromValue()
	throws Exception
	{
		GroupId groupId = GroupId.of(SOME_VALUE);
		
		String str = groupId.toString();
		
		assertThat(str).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void ofInvalidValue()
	throws Exception
	{
		assertThatThrownBy(() -> GroupId.of(INVALID_VALUE))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_VALUE);
	}
}
