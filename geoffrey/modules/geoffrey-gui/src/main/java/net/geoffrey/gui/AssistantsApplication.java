package net.geoffrey.gui;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AssistantsApplication
extends Application
{
	private static final double MAIN_SCENE_MINIMUM_WIDTH = 480.;
	private static final double MAIN_SCENE_MINIMUM_HEIGHT = 520.;
	
	private final Executor workerExecutor;
	
	public AssistantsApplication()
	{
		workerExecutor = Executors.newCachedThreadPool(
			AssistantsApplication::threadFactory);
	}
	
	@Override
	public void start(Stage primaryStage)
	throws Exception
	{
		FXMLLoader loader = new FXMLLoader(
			ApplicationResources.getFXML("assistants"),
			ApplicationResources.getMessages());
		loader.setControllerFactory(type -> new AssistantsController(
			workerExecutor,
			primaryStage));
		
		Scene scene = new Scene(
				loader.load(),
			MAIN_SCENE_MINIMUM_WIDTH,
			MAIN_SCENE_MINIMUM_HEIGHT);
		scene.getStylesheets().add(
			ApplicationResources.getStylesheet("default"));
		
		File applicationDir = getApplicationDir();
		File assistantDir = new File(applicationDir, "assistant");
		assistantDir.mkdirs();
		
		primaryStage.setTitle(
			ApplicationResources.getMessages()
				.getString("assistantsStageTitle"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private static File getApplicationDir()
	{
		return new File(System.getProperty("user.home"), ".geoffrey");
	}
	
	private static Thread threadFactory(Runnable runnable)
	{
		Thread t = new Thread(runnable);
		t.setDaemon(true);
		return t;
	}
}
