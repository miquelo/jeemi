package net.jeemi.ri.tool.util;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.StreamSupport.stream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper
{
	private final int bufferSize;
	
	public Unzipper(int bufferSize)
	{
		this.bufferSize = bufferSize;
	}
	
	public void unzip(InputStream input, File targetDir, int estimatedSize)
	throws IOException
	{
		try (ZipInputStream zipInput = new ZipInputStream(input))
		{
			stream(new ZipEntrySpliterator(zipInput, estimatedSize), false)
				.forEach(entry -> unzip(zipInput, entry, targetDir));
		}
		catch (UncheckedIOException exception)
		{
			throw exception.getCause();
		}
	}
	
	void unzip(ZipInputStream input, ZipEntry entry, File targetDir)
	{
		try
		{
			File targetFile = new File(targetDir, entry.getName());
			if (entry.isDirectory())
				targetFile.mkdirs();
			else
				unzip(input, targetFile);
		}
		catch (IOException exception)
		{
			throw new UncheckedIOException(exception);
		}
	}
	
	private void unzip(ZipInputStream input, File targetFile)
	throws IOException
	{
		try (OutputStream output = new FileOutputStream(targetFile))
		{
			byte[] buffer = new byte[bufferSize];
			int len = input.read(buffer, 0, bufferSize);
			while (len > 0)
			{
				output.write(buffer, 0, len);
				len = input.read(buffer, 0, bufferSize);
			}
		}
	}
	
	private static class ZipEntrySpliterator
	extends AbstractSpliterator<ZipEntry>
	{
		private ZipInputStream input;
		
		public ZipEntrySpliterator(ZipInputStream input, int estimatedSize)
		{
			super(estimatedSize, 0);
			this.input = requireNonNull(input);
		}
		
		@Override
		public boolean tryAdvance(Consumer<? super ZipEntry> action)
		{
			try
			{
				return ofNullable(input.getNextEntry())
					.map(entry -> {
						action.accept(entry);
						return true;
					})
					.orElse(false);
			}
			catch (IOException exception)
			{
				throw new UncheckedIOException(exception);
			}
		}
	}
}
