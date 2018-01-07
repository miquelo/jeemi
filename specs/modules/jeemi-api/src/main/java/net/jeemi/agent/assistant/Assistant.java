package net.jeemi.agent.assistant;

import java.util.Set;

import net.jeemi.agent.Agent;

/**
 * Assistant.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public interface Assistant
{
	Agent getAgent();
	
	Set<String> getResourceFactoryClassNames(ResourceConnectorType type);
	
	ResourceConnectorManager getResourceConnectorManager();
	
	ResourceManager getResourceManager();
}
