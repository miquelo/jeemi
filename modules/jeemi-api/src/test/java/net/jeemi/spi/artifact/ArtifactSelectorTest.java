package net.jeemi.spi.artifact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.spi.artifact.coordinates.ArtifactId;
import net.jeemi.spi.artifact.coordinates.GroupId;
import net.jeemi.spi.artifact.coordinates.Packaging;
import net.jeemi.spi.artifact.coordinates.RelativeVersion;

public class ArtifactSelectorTest
{
	private static final GroupId ANY_GROUP_ID = GroupId.of("any-group-id");
	private static final ArtifactId ANY_ARTIFACT_ID = ArtifactId.of(
		"any-artifact-id");
	private static final RelativeVersion ANY_VERSION = RelativeVersion.of(
		"any-version");
	private static final Packaging ANY_PACKAGING = Packaging.JAR;
	
	private static final GroupId SOME_GROUP_ID = GroupId.of("some-group-id");
	private static final ArtifactId SOME_ARTIFACT_ID = ArtifactId.of(
		"some-artifact-id");
	private static final RelativeVersion SOME_VERSION = RelativeVersion.of(
		"some-version");
	private static final Packaging SOME_PACKAGING = Packaging.EAR;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public ArtifactSelectorTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getGroupId()
	throws Exception
	{
		ArtifactSelector selector = ArtifactSelector.builder()
			.withGroupId(SOME_GROUP_ID)
			.withArtifactId(ANY_ARTIFACT_ID)
			.withVersion(ANY_VERSION)
			.withPackaging(ANY_PACKAGING)
			.build();
		
		GroupId groupId = selector.getGroupId();
		
		assertThat(groupId).isEqualTo(SOME_GROUP_ID);
	}
	
	@Test
	public void getArtifactId()
	throws Exception
	{
		ArtifactSelector selector = ArtifactSelector.builder()
			.withGroupId(ANY_GROUP_ID)
			.withArtifactId(SOME_ARTIFACT_ID)
			.withVersion(ANY_VERSION)
			.withPackaging(ANY_PACKAGING)
			.build();
		
		ArtifactId artifactId = selector.getArtifactId();
		
		assertThat(artifactId).isEqualTo(SOME_ARTIFACT_ID);
	}
	
	@Test
	public void getVersion()
	throws Exception
	{
		ArtifactSelector selector = ArtifactSelector.builder()
			.withGroupId(ANY_GROUP_ID)
			.withArtifactId(ANY_ARTIFACT_ID)
			.withVersion(SOME_VERSION)
			.withPackaging(ANY_PACKAGING)
			.build();
		
		RelativeVersion version = selector.getVersion();
		
		assertThat(version).isEqualTo(SOME_VERSION);
	}
	
	@Test
	public void getPackaging()
	throws Exception
	{
		ArtifactSelector selector = ArtifactSelector.builder()
			.withGroupId(ANY_GROUP_ID)
			.withArtifactId(ANY_ARTIFACT_ID)
			.withVersion(ANY_VERSION)
			.withPackaging(SOME_PACKAGING)
			.build();
		
		Packaging packaging = selector.getPackaging();
		
		assertThat(packaging).isEqualTo(SOME_PACKAGING);
	}
	
	@Test
	public void toStringWithCoordinates()
	throws Exception
	{
		ArtifactSelector selector = ArtifactSelector.builder()
			.withGroupId(SOME_GROUP_ID)
			.withArtifactId(SOME_ARTIFACT_ID)
			.withVersion(SOME_VERSION)
			.withPackaging(SOME_PACKAGING)
			.build();
		
		String str = selector.toString();
		
		assertThat(str).contains(
			SOME_GROUP_ID.toString(),
			SOME_ARTIFACT_ID.toString(),
			SOME_VERSION.toString(),
			SOME_PACKAGING.toString());
	}
}
