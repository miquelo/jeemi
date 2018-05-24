package net.jeemi.artifact.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import net.jeemi.artifact.Artifact;
import net.jeemi.artifact.ArtifactCoordinates;

/**
 * Artifact repository hub.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactRepositoryHub
implements ArtifactRepository
{
	private Set<ArtifactRepositoryFactory> factorySet;
	
	public ArtifactRepositoryHub()
	{
		factorySet = new HashSet<>();
	}
	
	public ArtifactRepositoryHub(Set<ArtifactRepositoryFactory> factorySet)
	{
		this.factorySet = Objects.requireNonNull(factorySet);
	}
	
	@Override
	public Optional<Artifact> fetch(ArtifactCoordinates coords)
	{
		return factorySet.stream()
			.map(ArtifactRepositoryFactory::getRepository)
			.map(repo -> repo.fetch(coords))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.findAny();
	}
	
	public void addFactory(ArtifactRepositoryFactory factory)
	{
		factorySet.add(factory);
	}
	
	public boolean removeFactory(ArtifactRepositoryFactory factory)
	{
		return factorySet.remove(factory);
	}
}
