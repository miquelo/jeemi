package net.jeemi.ri.tool.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.junit.rules.TemporaryFolder;

public interface TestFiles
{
	static URI file(TemporaryFolder folder, byte[] content)
	throws IOException
	{
		File file = folder.newFile();
		write(file, content);
		return file.toURI();
	}
	
	static byte[] read(File file, int length)
	throws IOException
	{
		try (InputStream input = new FileInputStream(file))
		{
			byte[] bytes = new byte[length];
			input.read(bytes, 0, length);
			return bytes;
		}
	}
	
	static void write(File file, byte[] content)
	throws IOException
	{
		try (OutputStream output = new FileOutputStream(file))
		{
			output.write(content);
		}
	}
}
