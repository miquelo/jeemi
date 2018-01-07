package net.jeemi.agent.assistant;

/**
 * Resource connector type.
 * 
 * @author Miquel A. Ferran Gonzalez &lt;miquel.ferran.gonzalez@gmail.com&gt;
 */
public enum ResourceConnectorType
{
	ARTIFACT_REPOSITORY("JEEMI-Artifact"),
	MACHINERY("JEEMI-Machinery");
	
	private String eisType;
	
	private ResourceConnectorType(String eisType)
	{
		this.eisType = eisType;
	}
	
	public String getEISType()
	{
		return eisType;
	}
}
