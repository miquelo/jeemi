package net.jeemi.agent.assistant;

import java.util.stream.Stream;

/**
 * Resource manager.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface ResourceManager
{
	Stream<ResourceInfo> resources();
	
	void create(ResourceSpecs specs);
	
	void remove(ResourceName name);
}
