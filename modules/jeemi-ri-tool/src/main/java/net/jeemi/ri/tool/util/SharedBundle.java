package net.jeemi.ri.tool.util;

import static java.lang.System.getProperty;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class SharedBundle
{
	public static final File DEFAULT_CACHE_DIR = new File(
		getProperty("java.io.tmpdir"),
		"jeemi");
	public static final int DEFAULT_CACHE_BUFFER_SIZE = 32 * 1024;
	
	private final String name;
	private final URI source;
	private final File cacheDir;
	
	private SharedBundle(BuilderImpl builder)
	{
		name = builder.name;
		source = builder.source;
		cacheDir = builder.cacheDir;
	}
	
	public InputStream open()
	throws IOException
	{
		File cacheFile = new File(cacheDir, name);
		if (cacheFile.exists())
			return new FileInputStream(cacheFile);
		cacheDir.mkdirs();
		cacheFile.createNewFile();
		return new SourceInputStream(
			source.toURL().openStream(),
			cacheFile,
			DEFAULT_CACHE_BUFFER_SIZE);
	}
	
	public static BuilderName builder()
	{
		return new BuilderImpl();
	}
	
	public interface BuilderName
	{
		BuilderSource withName(String name);
	}
	
	public interface BuilderSource
	{
		Builder withSource(URI source);
	}
	
	public interface Builder
	{
		Builder withCacheDir(File cacheDir);
		
		SharedBundle build();
	}
	
	private static class BuilderImpl
	implements BuilderName, BuilderSource, Builder
	{
		private String name;
		private URI source;
		private File cacheDir;
		
		private BuilderImpl()
		{
			name = null;
			source = null;
			cacheDir = DEFAULT_CACHE_DIR;
		}
		
		@Override
		public BuilderSource withName(String name)
		{
			this.name = requireNonNull(name);
			return this;
		}
		
		@Override
		public Builder withSource(URI source)
		{
			this.source = requireNonNull(source);
			return this;
		}
		
		@Override
		public Builder withCacheDir(File cacheDir)
		{
			this.cacheDir = requireNonNull(cacheDir);
			return this;
		}
		
		@Override
		public SharedBundle build()
		{
			return new SharedBundle(this);
		}
	}
	
	private static class SourceInputStream
	extends InputStream
	{
		private final InputStream sourceStream;
		private final OutputStream cacheOutputStream;
		private final byte[] cacheBuffer;
		private int cacheBufferOffset;
		
		public SourceInputStream(InputStream sourceStream, File cacheFile,
				int cacheBufferSize)
		throws IOException
		{
			this.sourceStream = requireNonNull(sourceStream);
			cacheOutputStream = new FileOutputStream(requireNonNull(cacheFile));
			cacheBuffer = new byte[cacheBufferSize];
			cacheBufferOffset = 0;
		}
		
		@Override
		public int read()
		throws IOException
		{
			int res = sourceStream.read();
			if (res >= 0)
				writeToCache(res);
			return res;
		}
		
		@Override
		public void close()
		throws IOException
		{
			if (cacheBufferOffset > 0)
				cacheOutputStream.write(cacheBuffer, 0, cacheBufferOffset);
			sourceStream.close();
			cacheOutputStream.close();
		}
		
		private void writeToCache(int c)
		throws IOException
		{
			cacheBuffer[cacheBufferOffset++] = (byte) c;
			if (cacheBufferOffset == cacheBuffer.length)
			{
				cacheOutputStream.write(cacheBuffer, 0, cacheBuffer.length);
				cacheBufferOffset = 0;
			}
		}
	}
}
