package net.jeemi.artifact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ArtifactSelectorVersionMinorTest
{
	private static final int SOME_VALUE = 1;
	
	private static final int SOME_REVISION = 1;

	private static final int ANOTHER_VALUE = 2;

	private static final String BAD_STRING = "#";
	private static final String STRING_VALUE = String.valueOf(SOME_VALUE);
	private static final String STRING_VALUE_REVISION = String.format("%d.%d",
			SOME_VALUE, SOME_REVISION);
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactSelectorVersionMinorTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void serializable()
	{
		ArtifactSelectorVersionMinor minor = new ArtifactSelectorVersionMinor(
			SOME_VALUE,
			SOME_REVISION);
		
		assertThat(minor.getValue()).isEqualTo(SOME_VALUE);
		assertThat(minor.getRevision()).hasValue(SOME_REVISION);
	}
	
	@Test
	public void valueAsHashCode()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.build();
		
		assertThat(minor.hashCode()).isEqualTo(SOME_VALUE);
	}
	
	@Test
	public void equalsByContent()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.withRevision(SOME_REVISION)
			.build();
		ArtifactSelectorVersionMinor anotherMinor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.withRevision(SOME_REVISION)
			.build();
		
		boolean equals = minor.equals(anotherMinor);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByValue()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.build();
		ArtifactSelectorVersionMinor anotherMinor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(ANOTHER_VALUE)
			.build();
		
		boolean equals = minor.equals(anotherMinor);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByRevision()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.withRevision(SOME_REVISION)
			.build();
		ArtifactSelectorVersionMinor anotherMinor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.build();
		
		boolean equals = minor.equals(anotherMinor);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void haveStringWithoutRevision()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.builder()
			.withValue(SOME_VALUE)
			.withRevision(SOME_REVISION)
			.build();
		
		String str = minor.toString();
		
		assertThat(str).isEqualTo(STRING_VALUE_REVISION);
	}
	
	@Test
	public void parseMinorValue()
	{
		ArtifactSelectorVersionMinor minor =
				ArtifactSelectorVersionMinor.parseVersionMinor(STRING_VALUE);
		
		assertThat(minor.getValue()).isEqualTo(SOME_VALUE);
		assertThat(minor.getRevision()).isEmpty();
	}
	
	@Test(expected = ArtifactSelectorFormatException.class)
	public void failParsingBadVersionMinorString()
	{
		ArtifactSelectorVersionMinor.parseVersionMinor(BAD_STRING);
	}
}
