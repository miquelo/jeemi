package net.jeemi.spi.artifact.coordinates;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Artifact packaging.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public enum Packaging
{
	JAR,
	WAR,
	RAR,
	EAR;
	
	private Packaging()
	{
	}
	
	public static Optional<Packaging> lookup(String typeName)
	{
		return Stream.of(values())
			.filter(v -> typeName.equalsIgnoreCase(v.name()))
			.findAny();
	}
}
