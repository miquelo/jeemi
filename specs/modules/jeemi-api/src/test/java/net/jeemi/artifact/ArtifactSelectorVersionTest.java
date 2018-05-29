package net.jeemi.artifact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ArtifactSelectorVersionTest
{
	private static final int ANY_MAJOR = 0;
	
	private static final Object ANOTHER_OBJECT_TYPE_INSTANCE = new Object();
	
	private static final int SOME_MAJOR = 1;
	private static final String SOME_MAJOR_STR = String.valueOf(SOME_MAJOR);
	
	private static final ArtifactSelectorVersionMinor SOME_MINOR =
			ArtifactSelectorVersionMinor.builder()
		.withValue(1)
		.build();
	private static final String SOME_MINOR_STR = String.valueOf(
			SOME_MINOR);
	
	private static final String SOME_QUALIFIER = "some-qualifier";
	
	private static final int ANOTHER_MAJOR = 2;

	private static final String BAD_STRING = "#";
	private static final String STRING_MAJOR = String.valueOf(SOME_MAJOR);
	private static final String STRING_MAJOR_SNAPSHOT = String.format(
			"%d-SNAPSHOT", SOME_MAJOR);
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactSelectorVersionTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void serializable()
	{
		ArtifactSelectorVersion version = new ArtifactSelectorVersion(
			SOME_MAJOR,
			SOME_MINOR,
			SOME_QUALIFIER);
		
		assertThat(version.getMajor()).isEqualTo(SOME_MAJOR);
		assertThat(version.getMinor()).hasValue(SOME_MINOR);
		assertThat(version.getQualifier()).hasValue(SOME_QUALIFIER);
	}
	
	@Test
	public void withMajor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		
		assertThat(version.getMajor()).isEqualTo(SOME_MAJOR);
	}
	
	@Test
	public void withEmptyMinor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.build();
		
		assertThat(version.getMinor()).isEmpty();
	}
	
	@Test
	public void withMinor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.withMinor(SOME_MINOR)
			.build();
		
		assertThat(version.getMinor()).hasValue(SOME_MINOR);
	}
	
	@Test
	public void withEmptyQualifier()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.build();
		
		assertThat(version.getQualifier()).isEmpty();
	}
	
	@Test
	public void withQualifier()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.withQualifier(SOME_QUALIFIER)
			.build();
		
		assertThat(version.getQualifier()).hasValue(SOME_QUALIFIER);
	}
	
	@Test
	public void notAnSnaphsotByDefault()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.build();
		
		assertThat(version.isSnapshot()).isFalse();
	}
	
	@Test
	public void anSnapshotByQualifier()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.withQualifier(ArtifactSelectorVersion.SNAPSHOT_QUALIFIER)
			.build();
		
		assertThat(version.isSnapshot()).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.build();
		ArtifactSelectorVersion objectRef = version;
		
		boolean equals = version.equals(objectRef);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByContent()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		ArtifactSelectorVersion anotherVersion =
				ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(ANY_MAJOR)
			.build();
		
		boolean equals = version.equals(ANOTHER_OBJECT_TYPE_INSTANCE);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByMajor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		ArtifactSelectorVersion anotherVersion =
				ArtifactSelectorVersion.builder()
			.withMajor(ANOTHER_MAJOR)
			.build();
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByMinor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.withMinor(SOME_MINOR)
			.build();
		ArtifactSelectorVersion anotherVersion =
				ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByQualifier()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.withMinor(SOME_MINOR)
			.withQualifier(SOME_QUALIFIER)
			.build();
		ArtifactSelectorVersion anotherVersion =
				ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.withMinor(SOME_MINOR)
			.build();
		
		boolean equals = version.equals(anotherVersion);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void majorAsHashCode()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		
		assertThat(version.hashCode()).isEqualTo(SOME_MAJOR);
	}
	
	@Test
	public void haveStringWithMajor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.build();
		
		String str = version.toString();
		
		assertThat(str).contains(SOME_MAJOR_STR);
	}
	
	@Test
	public void haveStringWithMajorMinor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.withMinor(SOME_MINOR)
			.build();
		
		String str = version.toString();
		
		assertThat(str).contains(SOME_MAJOR_STR, SOME_MINOR_STR);
	}
	
	@Test
	public void haveStringWithMajorQualifier()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.builder()
			.withMajor(SOME_MAJOR)
			.withQualifier(SOME_QUALIFIER)
			.build();
		
		String str = version.toString();
		
		assertThat(str).contains(SOME_MAJOR_STR, SOME_QUALIFIER);
	}
	
	@Test
	public void parseVersionMajor()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.parseVersion(
				STRING_MAJOR);
		
		assertThat(version.getMajor()).isEqualTo(SOME_MAJOR);
		assertThat(version.getMinor()).isEmpty();
		assertThat(version.getQualifier()).isEmpty();
		assertThat(version.isSnapshot()).isFalse();
	}
	
	@Test
	public void parseVersionMajorSnapshot()
	{
		ArtifactSelectorVersion version = ArtifactSelectorVersion.parseVersion(
				STRING_MAJOR_SNAPSHOT);
		
		assertThat(version.getMajor()).isEqualTo(SOME_MAJOR);
		assertThat(version.getMinor()).isEmpty();
		assertThat(version.isSnapshot()).isTrue();
	}
	
	@Test(expected = ArtifactSelectorFormatException.class)
	public void failParsingBadVersionString()
	{
		ArtifactSelectorVersion.parseVersion(BAD_STRING);
	}
}
