package net.jeemi.artifact.coordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.artifact.coordinates.ArtifactId;

public class ArtifactIdTest
{
	private static final String ANY_VALUE = "any-value";
	
	private static final String SOME_VALUE = "some-value";
	private static final String INVALID_VALUE = "invalid@value";
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactIdTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getValue()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(SOME_VALUE);
		
		String value = artifactId.getValue();
		
		assertThat(value).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void hashCodeBasedOnValue()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(SOME_VALUE);
		
		int hashCode = artifactId.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_VALUE.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(SOME_VALUE);
		ArtifactId anotherArtifactId = ArtifactId.of(SOME_VALUE);
		
		boolean equals = artifactId.equals(anotherArtifactId);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(ANY_VALUE);
		ArtifactId anotherArtifactId = artifactId;
		
		boolean equals = artifactId.equals(anotherArtifactId);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(SOME_VALUE);
		Object anotherArtifactId = new Object();
		
		boolean equals = artifactId.equals(anotherArtifactId);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringFromValue()
	throws Exception
	{
		ArtifactId artifactId = ArtifactId.of(SOME_VALUE);
		
		String str = artifactId.toString();
		
		assertThat(str).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void ofInvalidValue()
	throws Exception
	{
		assertThatThrownBy(() -> ArtifactId.of(INVALID_VALUE))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(INVALID_VALUE);
	}
}
