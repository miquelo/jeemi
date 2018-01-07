package net.geoffrey.gui.wizard.local;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.geoffrey.assistant.local.NewLocalAssistantFactory;
import net.geoffrey.gui.wizard.WizardConfiguration;
import net.geoffrey.gui.wizard.WizardController;
import net.jeemi.agent.assistant.AssistantFactory;

public class LocalWizardNewController
implements WizardController
{
	@FXML
	private TextField administrationPortField;
	
	@FXML
	private TextField agentServicePortField;
	
	public LocalWizardNewController()
	{
		administrationPortField = null;
		agentServicePortField = null;
	}
	
	@Override
	public void configure(WizardConfiguration config)
	{
		config.validProperty().bind(validInteger(administrationPortField)
				.and(validInteger(agentServicePortField)));
	}
	
	@Override
	public AssistantFactory getFactory()
	{
		return new NewLocalAssistantFactory(
			parseTextFieldInt(administrationPortField),
			parseTextFieldInt(agentServicePortField));
	}
	
	private static BooleanBinding validInteger(TextField textField)
	{
		return Bindings.createBooleanBinding(
			() -> validIntegerValue(textField),
			textField.textProperty());
	}
	
	private static boolean validIntegerValue(TextField textField) 
	{
		try
		{
			return parseTextFieldInt(textField) != null;
		}
		catch (NumberFormatException exception)
		{
			return false;
		}
	}
	
	private static Integer parseTextFieldInt(TextField textField)
	{
		return normalizedText(textField.getText())
			.map(Integer::parseInt)
			.orElse(null);
	}
	
	private static Optional<String> normalizedText(String text)
	{
		if (text == null)
			return Optional.empty();
		text = text.trim();
		return text.isEmpty() ? Optional.empty() : Optional.of(text);
	}
}
