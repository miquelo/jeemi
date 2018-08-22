package net.jeemi.ri.tool.service.admin;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.delayedExecutor;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import net.jeemi.ri.tool.service.admin.command.AdminCommand;

public final class AdminApplication
{
	private final Random random;
	private final File binDir;
	private final Executor executor;
	
	public AdminApplication(BuilderImpl builder)
	{
		random = builder.random;
		binDir = builder.binDir;
		executor = ofNullable(builder.executor)
			.orElse(delayedExecutor(0L, MILLISECONDS));
	}
	
	public <T> CompletableFuture<T> execute(AdminCommand<T> command)
	throws IOException
	{
		Process process = new ProcessBuilder(Stream.of(
			singletonList(getBinFile().getAbsolutePath()),
			command.prepare(random))
				.flatMap(List::stream)
				.collect(toList()))
			.start();
		return process.toHandle()
			.onExit()
			.thenCombine(
				supplyAsync(
					() -> command.processOutput(
						process.getInputStream()),
					executor)
					.thenCombine(
						runAsync(
							() -> command.processError(
								process.getErrorStream()),
							executor),
						(outValue, errValue) -> outValue),
				(processValue, outValue) -> outValue);
	}
	
	public static BuilderRandom builder()
	{
		return new BuilderImpl();
	}
	
	private File getBinFile()
	{
		// TODO OS checking for using ".bat" when running on Windows
		File binFile = new File(binDir, "asadmin");
		binFile.setExecutable(true);
		return binFile;
	}
	
	public interface BuilderRandom
	{
		BuilderBinDir withRandom(Random random);
	}
	
	public interface BuilderBinDir
	{
		Builder withBinDir(File binDir);
	}
	
	public interface Builder
	{
		Builder withExecutor(Executor executor);
		
		AdminApplication build();
	}
	
	private static class BuilderImpl
	implements BuilderRandom, BuilderBinDir, Builder
	{
		private Random random;
		private File binDir;
		private Executor executor;
		
		private BuilderImpl()
		{
			random = null;
			binDir = null;
			executor = null;
		}
		
		@Override
		public BuilderBinDir withRandom(Random random)
		{
			this.random = requireNonNull(random);
			return this;
		}
		
		@Override
		public Builder withBinDir(File binDir)
		{
			this.binDir = requireNonNull(binDir);
			return this;
		}
		
		@Override
		public Builder withExecutor(Executor executor)
		{
			this.executor = executor;
			return this;
		}
		
		@Override
		public AdminApplication build()
		{
			return new AdminApplication(this);
		}
	}
}
