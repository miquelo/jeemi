package net.jeemi.agent.assistant;

import java.util.stream.Stream;

import net.jeemi.artifact.Artifact;

/**
 * Resource connector manager.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ResourceConnectorManager
{
	Stream<ResourceConnectorInfo> all();
	
	void deploy(ResourceConnectorSpecs specs, Artifact artifact);
	
	void undeploy(ResourceConnectorName name);
}
