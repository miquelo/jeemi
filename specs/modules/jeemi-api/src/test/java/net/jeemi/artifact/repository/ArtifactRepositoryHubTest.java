package net.jeemi.artifact.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.artifact.Artifact;
import net.jeemi.artifact.ArtifactCoordinates;
import net.jeemi.artifact.ArtifactVersion;

public class ArtifactRepositoryHubTest
{
	private static final ArtifactCoordinates ANY_COORDINATES =
			ArtifactCoordinates.builder()
		.withGroupId("any.group.id")
		.withArtifactId("any-artifact-id")
		.withVersion(ArtifactVersion.builder()
			.withMajor(1)
			.build())
		.build();
	
	private static final ArtifactCoordinates FOO_COORDINATES =
			ArtifactCoordinates.builder()
		.withGroupId("foo.group.id")
		.withArtifactId("foo-artifact-id")
		.withVersion(ArtifactVersion.builder()
			.withMajor(1)
			.build())
		.build();
	
	private static final ArtifactCoordinates BAR_COORDINATES =
			ArtifactCoordinates.builder()
		.withGroupId("bar.group.id")
		.withArtifactId("bar-artifact-id")
		.withVersion(ArtifactVersion.builder()
			.withMajor(1)
			.build())
		.build();
	
	@Rule
	public final MockitoRule mockitoRule;
	
	@Mock
	private ArtifactRepositoryFactory fooFactory;
	
	@Mock
	private ArtifactRepository fooRepository;
	
	@Mock
	private Artifact fooArtifact;
	
	@Mock
	private ArtifactRepositoryFactory barFactory;
	
	@Mock
	private ArtifactRepository barRepository;
	
	@Mock
	private Artifact barArtifact;
	
	@Mock
	private ArtifactRepositoryFactory bazFactory;
	
	@Mock
	private ArtifactRepository bazRepository;
	
	public ArtifactRepositoryHubTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Before
	public void setUp()
	{
		when(fooFactory.getRepository())
			.thenReturn(fooRepository);
		when(barFactory.getRepository())
			.thenReturn(barRepository);
		when(bazFactory.getRepository())
			.thenReturn(bazRepository);
		
		when(fooRepository.fetch(any()))
			.thenReturn(Optional.empty());
		when(fooRepository.fetch(FOO_COORDINATES))
			.thenReturn(Optional.of(fooArtifact));
		when(barRepository.fetch(any()))
			.thenReturn(Optional.empty());
		when(barRepository.fetch(BAR_COORDINATES))
			.thenReturn(Optional.of(barArtifact));
		when(bazRepository.fetch(any()))
			.thenReturn(Optional.empty());
	}
	
	@Test
	public void fetchEmpty()
	throws Exception
	{
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub();
		
		Optional<Artifact> artifact = hub.fetch(ANY_COORDINATES);
		
		assertThat(artifact).isEmpty();
	}
	
	@Test
	public void fetchNotFound()
	throws Exception
	{
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(Stream.of(
			bazFactory,
			barFactory).collect(Collectors.toSet()));
		
		Optional<Artifact> artifact = hub.fetch(FOO_COORDINATES);
		
		verify(bazRepository).fetch(FOO_COORDINATES);
		assertThat(artifact).isEmpty();
	}
	
	@Test
	public void fetchFound()
	throws Exception
	{
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(Stream.of(
			bazFactory,
			fooFactory,
			barFactory).collect(Collectors.toSet()));
		
		Optional<Artifact> artifact = hub.fetch(FOO_COORDINATES);
		
		assertThat(artifact).hasValue(fooArtifact);
	}
	
	@Test
	public void addFactory()
	{
		Set<ArtifactRepositoryFactory> factorySet = new HashSet<>();
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factorySet);
		
		hub.addFactory(fooFactory);
		
		assertThat(factorySet).contains(fooFactory);
	}
	
	@Test
	public void removeFactoryMiss()
	{
		Set<ArtifactRepositoryFactory> factorySet = Stream.of(
			fooFactory,
			barFactory).collect(Collectors.toSet());
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factorySet);
		
		boolean removed = hub.removeFactory(bazFactory);
		
		assertThat(removed).isFalse();
	}
	
	@Test
	public void removeFactoryHit()
	{
		Set<ArtifactRepositoryFactory> factorySet = Stream.of(
			fooFactory,
			barFactory,
			bazFactory).collect(Collectors.toSet());
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factorySet);
		
		boolean removed = hub.removeFactory(bazFactory);
		
		assertThat(removed).isTrue();
		assertThat(factorySet).doesNotContain(bazFactory);
	}
}
