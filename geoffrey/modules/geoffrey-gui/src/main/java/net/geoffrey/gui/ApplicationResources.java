package net.geoffrey.gui;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public interface ApplicationResources
{
	public enum IconSize {
		
		MIDDLE(32, "png");
		
		private final int size;
		private final String extension;
		
		private IconSize(int size, String extension)
		{
			this.size = size;
			this.extension = Objects.requireNonNull(extension);
		}
		
		public String getFolderName()
		{
			return String.format("%dx%d", size, size);
		}
		
		public String getExtension()
		{
			return extension;
		}
	}
	
	public enum IconName {
		
		ACTIONS_DOCUMENT_NEW("actions", "document-new"),
		ACTIONS_DOCUMENT_OPEN("actions", "document-open"),
		ACTIONS_DOCUMENT_PROPERTIES("actions", "document-properties"),
		ACTIONS_EDIT_DELETE("actions", "edit-delete"),
		ACTIONS_GO_NEXT("actions", "go-next");
		
		private final String folderName;
		private final String fileName;
		
		private IconName(String folderName, String fileName)
		{
			this.folderName = Objects.requireNonNull(folderName);
			this.fileName = Objects.requireNonNull(fileName);
		}

		public String getFolderName()
		{
			return folderName;
		}

		public String getFileName()
		{
			return fileName;
		}
	}
	
	static URL getFXML(String name)
	{
		return ApplicationResources.class.getResource(
			String.format("/net/geoffrey/gui/%s.fxml", name));
	}
	
	static ResourceBundle getMessages()
	{
		return ResourceBundle.getBundle("net.geoffrey.gui.Messages");
	}
	
	static String getIcon(IconSize size, IconName name)
	{
		return String.format(
			"org/tango-project/tango-icon-theme/%s/%s/%s.%s",
			size.getFolderName(),
			name.getFolderName(),
			name.getFileName(),
			size.getExtension());
	}
	
	static String getStylesheet(String name)
	{
		return ApplicationResources.class.getResource(
			String.format("%s.css", name)).toExternalForm();
	}
}
