package net.jeemi.artifact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

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
	public void factoriesEmpty()
	{
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub();
		
		Stream<ArtifactRepositoryFactory> factories = hub.factories();
		
		assertThat(factories).isEmpty();
	}
	
	@Test
	public void factoriesFull()
	{
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(Stream.of(
			fooFactory,
			barFactory).collect(Collectors.toList()));
		
		Stream<ArtifactRepositoryFactory> factories = hub.factories();
		
		assertThat(factories).contains(fooFactory, barFactory);
	}
	
	@Test
	public void fetchWithFactories()
	throws Exception
	{
		ArtifactRepositoryHub hub = spy(ArtifactRepositoryHub.class);
		
		hub.fetch(ANY_COORDINATES);
		
		verify(hub).factories();
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
			barFactory).collect(Collectors.toList()));
		
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
			barFactory).collect(Collectors.toList()));
		
		Optional<Artifact> artifact = hub.fetch(FOO_COORDINATES);
		
		verify(bazRepository).fetch(FOO_COORDINATES);
		verify(barRepository, times(0)).fetch(any());
		assertThat(artifact).hasValue(fooArtifact);
	}
	
	@Test
	public void addFactory()
	{
		List<ArtifactRepositoryFactory> factoryList = new ArrayList<>();
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factoryList);
		
		hub.addFactory(fooFactory);
		
		assertThat(factoryList).contains(fooFactory);
	}
	
	@Test
	public void removeFactoryMiss()
	{
		List<ArtifactRepositoryFactory> factoryList = Stream.of(
			fooFactory,
			barFactory).collect(Collectors.toList());
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factoryList);
		
		boolean removed = hub.removeFactory(bazFactory);
		
		assertThat(removed).isFalse();
	}
	
	@Test
	public void removeFactoryHit()
	{
		List<ArtifactRepositoryFactory> factoryList = Stream.of(
			fooFactory,
			barFactory,
			bazFactory).collect(Collectors.toList());
		ArtifactRepositoryHub hub = new ArtifactRepositoryHub(factoryList);
		
		boolean removed = hub.removeFactory(bazFactory);
		
		assertThat(removed).isTrue();
		assertThat(factoryList).doesNotContain(bazFactory);
	}
}
