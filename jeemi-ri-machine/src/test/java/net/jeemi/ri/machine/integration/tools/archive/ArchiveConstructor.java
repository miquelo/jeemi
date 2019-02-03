package net.jeemi.ri.machine.integration.tools.archive;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;

public class ArchiveConstructor
{
	private final File workingDir;
	
	public ArchiveConstructor(File workingDir)
	{
		this.workingDir = requireNonNull(workingDir);
	}
	
	public File construct(Archive archive)
	throws IOException, InterruptedException
	{
		ProcessBuilder pb = new ProcessBuilder(
			"/bin/bash",
			"-c",
			command(
				"javac",
				format("@%s", sourceFilesTextFile())));
		Process process = pb.start();
		process.waitFor();
		InputStream input = process.getErrorStream();
		byte[] buff = new byte[1024];
		int len = input.read(buff, 0, 1024);
		while (len > 0)
		{
			System.out.write(buff, 0, len);
			len = input.read(buff, 0, 1024);
		}
		throw new UnsupportedOperationException();
	}
	
	private String command(String... args)
	{
		return Arrays.stream(args)
			.collect(joining(" "));
	}
	
	private File classesDir(Archive archive)
	{
		return Paths.get(
			workingDir.getAbsolutePath(),
			archive.getName(),
			"src",
			"classes")
				.toFile();
	}
	
	private File sourceFilesTextFile()
	{
		throw new UnsupportedOperationException();
	}
}
