package net.jeemi.spi.artifact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.spi.artifact.coordinates.ArtifactId;
import net.jeemi.spi.artifact.coordinates.GroupId;
import net.jeemi.spi.artifact.coordinates.Packaging;
import net.jeemi.spi.artifact.coordinates.Version;

public class ArtifactTest
{
	private static final GroupId ANY_GROUP_ID = GroupId.of("any-group-id");
	private static final ArtifactId ANY_ARTIFACT_ID = ArtifactId.of(
		"any-artifact-id");
	private static final Version ANY_VERSION = Version.of("any-version");
	private static final Packaging ANY_PACKAGING = Packaging.JAR;
	
	private static final GroupId SOME_GROUP_ID = GroupId.of("some-group-id");
	private static final ArtifactId SOME_ARTIFACT_ID = ArtifactId.of(
		"some-artifact-id");
	private static final Version SOME_VERSION = Version.of("some-version");
	private static final Packaging SOME_PACKAGING = Packaging.EAR;
	
	private static final GroupId ANOTHER_GROUP_ID = GroupId.of(
		"another-group-id");
	private static final ArtifactId ANOTHER_ARTIFACT_ID = ArtifactId.of(
		"another-packaging");
	private static final Version ANOTHER_VERSION = Version.of(
		"another-version");
	private static final Packaging ANOTHER_PACKAGING = Packaging.RAR;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getGroupId()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			ANY_ARTIFACT_ID,
			ANY_VERSION,
			ANY_PACKAGING);
		
		GroupId groupId = artifact.getGroupId();
		
		assertThat(groupId).isEqualTo(SOME_GROUP_ID);
	}
	
	@Test
	public void getArtifactId()
	throws Exception
	{
		Artifact artifact = new Artifact(
			ANY_GROUP_ID,
			SOME_ARTIFACT_ID,
			ANY_VERSION,
			ANY_PACKAGING);
		
		ArtifactId artifactId = artifact.getArtifactId();
		
		assertThat(artifactId).isEqualTo(SOME_ARTIFACT_ID);
	}
	
	@Test
	public void getVersion()
	throws Exception
	{
		Artifact artifact = new Artifact(
			ANY_GROUP_ID,
			ANY_ARTIFACT_ID,
			SOME_VERSION,
			ANY_PACKAGING);
		
		Version version = artifact.getVersion();
		
		assertThat(version).isEqualTo(SOME_VERSION);
	}
	
	@Test
	public void getPackaging()
	throws Exception
	{
		Artifact artifact = new Artifact(
			ANY_GROUP_ID,
			ANY_ARTIFACT_ID,
			ANY_VERSION,
			SOME_PACKAGING);
		
		Packaging packaging = artifact.getPackaging();
		
		assertThat(packaging).isEqualTo(SOME_PACKAGING);
	}
	
	@Test
	public void hashCodeBasedOnGroupId()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			ANY_ARTIFACT_ID,
			ANY_VERSION,
			ANY_PACKAGING);
		
		int hashCode = artifact.hashCode();
		
		assertThat(hashCode).isEqualTo(SOME_GROUP_ID.hashCode());
	}
	
	@Test
	public void equals()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Artifact anotherArtifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void equalsByObjectRef()
	throws Exception
	{
		Artifact artifact = new Artifact(
			ANY_GROUP_ID,
			ANY_ARTIFACT_ID,
			ANY_VERSION,
			ANY_PACKAGING);
		Artifact anotherArtifact = artifact;
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isTrue();
	}
	
	@Test
	public void notEqualsByObjectType()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Object anotherArtifact = new Object();
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByGroupId()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Artifact anotherArtifact = new Artifact(
			ANOTHER_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByArtifactId()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Artifact anotherArtifact = new Artifact(
			SOME_GROUP_ID,
			ANOTHER_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByVersion()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Artifact anotherArtifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			ANOTHER_VERSION,
			SOME_PACKAGING);
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void notEqualsByPackaing()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		Artifact anotherArtifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			ANOTHER_PACKAGING);
		
		boolean equals = artifact.equals(anotherArtifact);
		
		assertThat(equals).isFalse();
	}
	
	@Test
	public void toStringWithCoordinates()
	throws Exception
	{
		Artifact artifact = new Artifact(
			SOME_GROUP_ID,
			SOME_ARTIFACT_ID,
			SOME_VERSION,
			SOME_PACKAGING);
		
		String str = artifact.toString();
		
		assertThat(str).contains(
			SOME_GROUP_ID.toString(),
			SOME_ARTIFACT_ID.toString(),
			SOME_VERSION.toString(),
			SOME_PACKAGING.toString());
	}
}
