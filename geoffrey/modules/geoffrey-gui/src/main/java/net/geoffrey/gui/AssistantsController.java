package net.geoffrey.gui;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.geoffrey.gui.ApplicationResources.IconName;
import net.geoffrey.gui.ApplicationResources.IconSize;
import net.geoffrey.gui.model.AssistantHolder;
import net.geoffrey.gui.wizard.Wizard;
import net.geoffrey.gui.wizard.WizardComponent;

public class AssistantsController
{
	private static final double ADD_DIALOG_SCENE_MINIMUM_WIDTH = 500.;
	private static final double ADD_DIALOG_SCENE_MINIMUM_HEIGHT = 580.;
	
	private final Executor workerExecutor;
	
	private final Stage primaryStage;
	
	@FXML
	private TableView<AssistantHolder> assistantTableView;
	
	@FXML
	private Button configureButton;
	
	@FXML
	private Button newButton;
	
	@FXML
	private Button openButton;
	
	@FXML
	private Button dropButton;
	
	public AssistantsController(Executor workerExecutor, Stage primaryStage)
	{
		this.workerExecutor = Objects.requireNonNull(workerExecutor);
		this.primaryStage = Objects.requireNonNull(primaryStage);
		assistantTableView = null;
		configureButton = null;
		newButton = null;
		openButton = null;
		dropButton = null;
	}

	@FXML
	public void initialize()
	{
		ObservableList<AssistantHolder> items =
				FXCollections.observableArrayList();
		assistantTableView.setItems(items);
		
		configureButton.setGraphic(createMenuGraphic(
			IconName.ACTIONS_DOCUMENT_PROPERTIES));
		configureButton.setDisable(true);
		
		newButton.setGraphic(createMenuGraphic(IconName.ACTIONS_DOCUMENT_NEW));
		openButton.setGraphic(createMenuGraphic(
			IconName.ACTIONS_DOCUMENT_OPEN));
		
		dropButton.setGraphic(createMenuGraphic(IconName.ACTIONS_EDIT_DELETE));
		dropButton.setDisable(true);
	}
	
	@FXML
	public void handleConfigureAction(ActionEvent event)
	throws IOException
	{
	}
	
	@FXML
	public void handleNewAction(ActionEvent event)
	throws IOException
	{
		openAddDialog(
			"newActionText",
			"assistantNewTitle",
			Wizard::getAssistantNewComponent);
	}
	
	@FXML
	public void handleOpenAction(ActionEvent event)
	throws IOException
	{
		openAddDialog(
			"openActionText",
			"assistantOpenTitle",
			Wizard::getAssistantOpenComponent);
	}
	
	@FXML
	public void handleDropAction(ActionEvent event)
	throws IOException
	{
	}
	
	private void openAddDialog(String performButtonTextKey,
			String dialogTitleKey,
			Function<Wizard, WizardComponent> wizardComponent)
	throws IOException
	{
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		
		FXMLLoader loader = new FXMLLoader(
			ApplicationResources.getFXML("assistant-add"),
			ApplicationResources.getMessages());
		loader.setControllerFactory(type -> new AssistantAddController(
			workerExecutor,
			dialog,
			ApplicationResources.getMessages()
				.getString(performButtonTextKey),
			wizardComponent,
			this::assistantAdded));
		
		Scene scene = new Scene(
			loader.load(),
			ADD_DIALOG_SCENE_MINIMUM_WIDTH,
			ADD_DIALOG_SCENE_MINIMUM_HEIGHT);
		scene.getStylesheets().add(
			ApplicationResources.getStylesheet("default"));
		
		dialog.setTitle(
			ApplicationResources.getMessages()
				.getString(dialogTitleKey));
		dialog.setScene(scene);
		dialog.show();
	}
	
	private void assistantAdded(AssistantHolder holder)
	{
		// TODO ...
	}
	
	private static ImageView createMenuGraphic(IconName iconName)
	{
		ImageView imageView = new ImageView(
			ApplicationResources.getIcon(IconSize.MIDDLE, iconName));
		imageView.setFitWidth(24.);
		imageView.setFitHeight(24.);
		return imageView;
	}
}
