package net.geoffrey.gui;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.geoffrey.gui.beans.binding.TextFieldBindings;
import net.geoffrey.gui.model.AssistantHolder;
import net.geoffrey.gui.scene.control.WizardCellFactory;
import net.geoffrey.gui.scene.control.WizardListCell;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardComponent;
import net.geoffrey.gui.wizard.WizardFactory;

public class AssistantAddController
{
	private static final Logger logger = Logger.getLogger(
			AssistantAddController.class.getName());
	
	private final Executor workerExecutor;
	private final Stage dialog;
	private final String performButtonText;
	private final Function<Wizard, WizardComponent> wizardComponent;
	private final Consumer<AssistantHolder> assistantAdded;
	
	@FXML
	private TextField assistantNameField;
	
	@FXML
	private ComboBox<Wizard> wizardCompoBox;
	
	@FXML
	private ScrollPane wizardContentPane;
	
	@FXML
	private Button performButton;
	
	private BooleanProperty wizardValid;
	
	public AssistantAddController(Executor workerExecutor, Stage dialog,
			String performButtonText,
			Function<Wizard, WizardComponent> wizardComponent,
			Consumer<AssistantHolder> assistantAdded)
	{
		this.workerExecutor = Objects.requireNonNull(workerExecutor);
		this.dialog = Objects.requireNonNull(dialog);
		this.performButtonText = Objects.requireNonNull(performButtonText);
		this.wizardComponent = Objects.requireNonNull(wizardComponent);
		this.assistantAdded = Objects.requireNonNull(assistantAdded);
		assistantNameField = null;
		wizardCompoBox = null;
		wizardContentPane = null;
		performButton = null;
		wizardValid = new SimpleBooleanProperty(false);
	}
	
	@FXML
	public void initialize()
	{
		wizardCompoBox.setButtonCell(new WizardListCell());
		wizardCompoBox.setCellFactory(new WizardCellFactory());
		
		wizardContentPane.getStyleClass().add("noborder");
		wizardContentPane.contentProperty().bind(
			Bindings.createObjectBinding(
				() -> Optional.ofNullable(wizardCompoBox.getValue())
					.map(item -> wizardComponent.apply(item).getNode())
					.orElse(null),
				wizardCompoBox.valueProperty()));
		
		performButton.setText(performButtonText);
		performButton.disableProperty().bind(Bindings.not(
			wizardCompoBox.valueProperty()
				.isNotNull()
				.and(TextFieldBindings.hasValue(assistantNameField))
				.and(wizardValid)));
		
		workerExecutor.execute(this::loadWizards);
	}
	
	@FXML
	public void handlePerformAction()
	{
		try
		{
			Wizard wizard = wizardCompoBox.valueProperty().get();
			assistantAdded.accept(new AssistantHolder(
				wizardComponent.apply(wizard).getAssistant(),
				new Image(wizard.getSmallIcon()),
				assistantNameField.getText()));
		}
		finally
		{
			dialog.close();
		}
	}
	
	@FXML
	public void handleCancelAction()
	{
		dialog.close();
	}
	
	private void loadWizards()
	{
		ObservableList<Wizard> wizards = wizardCompoBox.getItems();
		wizards.clear();
		serviceLoaders(WizardFactory.class)
			.map(this::getFactoryWizard)
			.filter(Objects::nonNull)
			.forEach(wizards::add);
	}
	
	private Wizard getFactoryWizard(WizardFactory factory)
	{
		try
		{
			return factory.getWizard();
		}
		catch (IOException exception)
		{
			logger.log(Level.SEVERE, "Unable to get wizard", exception);
			return null;
		}
	}
	
	private static <T> Stream<T> serviceLoaders(Class<T> type)
	{
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
			ServiceLoader.load(type).iterator(),
			Spliterator.ORDERED),
			false);
	}
}
