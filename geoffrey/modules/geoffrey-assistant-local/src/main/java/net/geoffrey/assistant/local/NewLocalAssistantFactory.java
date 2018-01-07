package net.geoffrey.assistant.local;

import java.util.Optional;

import net.jeemi.agent.assistant.Assistant;
import net.jeemi.agent.assistant.AssistantFactory;

public class NewLocalAssistantFactory
implements AssistantFactory
{
	public static final int DEFAULT_ADMINISTRATION_PORT = 4848;
	public static final int DEFAULT_AGENT_SERVICE_PORT = 3434;
	
	private final int administrationPort;
	private final int agentServicePort;
	
	public NewLocalAssistantFactory(Integer administrationPort,
			Integer agentServicePort)
	{
		this.administrationPort= Optional.ofNullable(administrationPort)
			.orElse(DEFAULT_ADMINISTRATION_PORT);
		this.agentServicePort = Optional.ofNullable(agentServicePort)
			.orElse(DEFAULT_AGENT_SERVICE_PORT);
	}
	
	@Override
	public Assistant getAssistant()
	{
		throw new UnsupportedOperationException();
	}
}
