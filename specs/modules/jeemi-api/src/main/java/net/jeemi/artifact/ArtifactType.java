package net.jeemi.artifact;

import java.util.stream.Stream;

/**
 * Artifact type.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public enum ArtifactType
{
	JAR,
	WAR,
	RAR,
	EAR;
	
	private ArtifactType()
	{
	}
	
	public static ArtifactType lookup(String typeName)
	throws UnknownArtifactTypeException
	{
		return Stream.of(values())
			.filter(v -> typeName.equalsIgnoreCase(v.name()))
			.findAny()
			.orElseThrow(() -> new UnknownArtifactTypeException(typeName));
	}
}
