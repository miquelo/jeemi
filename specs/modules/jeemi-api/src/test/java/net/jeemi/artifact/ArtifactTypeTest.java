package net.jeemi.artifact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Condition;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ArtifactTypeTest
{
	private static final String UNKNOWN_TYPE_NAME = "unknown";
	private static final String KNOWN_TYPE_NAME = "jar";
	private static final ArtifactType KNOWN_TYPE = ArtifactType.JAR;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactTypeTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void lookupUnknown()
	throws Exception
	{
		assertThatThrownBy(() -> ArtifactType.lookup(UNKNOWN_TYPE_NAME))
			.isInstanceOf(UnknownArtifactTypeException.class)
			.has(new UnknownTypeNameCondition(UNKNOWN_TYPE_NAME));
	}
	
	@Test
	public void lookupKnown()
	throws Exception
	{
		ArtifactType type = ArtifactType.lookup(KNOWN_TYPE_NAME);
		
		assertThat(type).isEqualTo(KNOWN_TYPE);
	}
	
	private static class UnknownTypeNameCondition
	extends Condition<Throwable>
	{
		private final String typeName;
		
		public UnknownTypeNameCondition(String typeName)
		{
			this.typeName = typeName;
		}
		
		@Override
		public boolean matches(Throwable exception)
		{
			return typeName.equals(((UnknownArtifactTypeException)
					exception).getName());
		}
	}
}
