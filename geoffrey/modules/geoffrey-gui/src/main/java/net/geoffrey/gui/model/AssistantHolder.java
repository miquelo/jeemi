package net.geoffrey.gui.model;

import java.util.Objects;

import javafx.scene.image.Image;
import net.jeemi.agent.assistant.Assistant;

public class AssistantHolder
{
	private final Assistant assistant;
	private final Image logo;
	private final String name;
	
	public AssistantHolder(Assistant assistant, Image logo, String name)
	{
		this.assistant = Objects.requireNonNull(assistant);
		this.logo = Objects.requireNonNull(logo);
		this.name = Objects.requireNonNull(name);
	}

	public Assistant getAssistant()
	{
		return assistant;
	}

	public Image getLogo()
	{
		return logo;
	}

	public String getName()
	{
		return name;
	}
}
