package net.jeemi.artifact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Artifact repository hub.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public class ArtifactRepositoryHub
implements ArtifactRepository
{
	private List<ArtifactRepositoryFactory> factoryList;
	
	public ArtifactRepositoryHub()
	{
		factoryList = new ArrayList<>();
	}
	
	public ArtifactRepositoryHub(List<ArtifactRepositoryFactory> factoryList)
	{
		this.factoryList = Objects.requireNonNull(factoryList);
	}
	
	@Override
	public Optional<Artifact> fetch(ArtifactCoordinates coords)
	{
		return factories()
			.map(ArtifactRepositoryFactory::getRepository)
			.map(repo -> repo.fetch(coords))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.findAny();
	}
	
	public Stream<ArtifactRepositoryFactory> factories()
	{
		return factoryList.stream();
	}
	
	public void addFactory(ArtifactRepositoryFactory factory)
	{
		factoryList.add(factory);
	}
	
	public boolean removeFactory(ArtifactRepositoryFactory factory)
	{
		return factoryList.remove(factory);
	}
}
