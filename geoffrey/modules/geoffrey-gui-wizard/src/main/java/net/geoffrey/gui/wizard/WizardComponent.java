package net.geoffrey.gui.wizard;

import java.util.Objects;

import javafx.scene.Node;
import net.jeemi.agent.assistant.Assistant;

public class WizardComponent
{
	private final Node node;
	private final WizardController controller;
	
	public WizardComponent(Node node, WizardController controller)
	{
		this.node = Objects.requireNonNull(node);
		this.controller = Objects.requireNonNull(controller);
	}
	
	public Node getNode()
	{
		return node;
	}
	
	public Assistant getAssistant()
	{
		return controller.getFactory().getAssistant();
	}
}
