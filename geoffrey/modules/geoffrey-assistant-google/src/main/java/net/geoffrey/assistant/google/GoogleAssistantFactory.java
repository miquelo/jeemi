package net.geoffrey.assistant.google;

import java.io.File;

import net.jeemi.agent.assistant.Assistant;
import net.jeemi.agent.assistant.AssistantFactory;

public class GoogleAssistantFactory
implements AssistantFactory
{
	private File workingDir;
	
	public GoogleAssistantFactory()
	{
		workingDir = null;
	}
	
	@Override
	public Assistant getAssistant()
	{
		return null;
	}

	public File getWorkingDir()
	{
		return workingDir;
	}

	public void setWorkingDir(File workingDir)
	{
		this.workingDir = workingDir;
	}
}
