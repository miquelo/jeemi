package net.jeemi.ri.tool.util;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ProcessFuture<T>
extends CompletableFuture<T>
{
	private static final int SUCCESS_EXIT_VALUE = 0;
	
	private final Process process;
	private final String[] args;
	private Throwable processCreationFailure;
	
	protected ProcessFuture(Runtime runtime, String[] args)
	{
		process = exec(requireNonNull(runtime));
		this.args = requireNonNull(args);
	}
	
	@Override
	public T get(long timeout, TimeUnit unit)
	throws InterruptedException, ExecutionException, TimeoutException
	{
		try
		{
			return ofNullable(process)
				.map(process -> waitFor(process, timeout, unit))
				.orElseThrow(() ->
					new ExecutionException(processCreationFailure));
		}
		catch (UncheckedInterruptedException exception)
		{
			throw exception.getCause();
		}
		catch (UncheckedTimeoutException exception)
		{
			throw exception.getCause();
		}
		catch (RuntimeException exception)
		{
			throw new ExecutionException(exception.getCause());
		}
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return (mayInterruptIfRunning
				? ofNullable(process)
					.map(ProcessFuture::cancel)
					.orElse(true)
				: true) &&
			super.cancel(mayInterruptIfRunning);
	}
	
	protected abstract T compute(InputStream out);
	
	protected abstract RuntimeException compute(InputStream err, int exitValue);
	
	private Process exec(Runtime runtime)
	{
		try
		{
			processCreationFailure = null;
			return runtime.exec(args);
		}
		catch (Throwable exception)
		{
			processCreationFailure = exception;
			completeExceptionally(processCreationFailure);
			return null;
		}
	}
	
	private T waitFor(Process process, long timeout, TimeUnit unit)
	{
		try
		{
			if (!process.waitFor(timeout, unit))
			{
				process.destroy();
				throw new UncheckedTimeoutException(new TimeoutException(
					format(
						"Process was not completed before %d%s",
						timeout,
						unit)));
			}
			if (process.exitValue() == SUCCESS_EXIT_VALUE)
				return compute(process.getInputStream());
			RuntimeException exception = compute(
				process.getErrorStream(),
				process.exitValue());
			completeExceptionally(exception);
			throw exception;
		}
		catch (InterruptedException exception)
		{
			throw new UncheckedInterruptedException(exception);
		}
	}
	
	private static boolean cancel(Process process)
	{
		process.destroy();
		return !process.isAlive();
	}
	
	private static class UncheckedInterruptedException
	extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public UncheckedInterruptedException(InterruptedException cause)
		{
			super(cause);
		}
		
		@Override
		public InterruptedException getCause()
		{
			return (InterruptedException) super.getCause();
		}
	}
	
	private static class UncheckedTimeoutException
	extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public UncheckedTimeoutException(TimeoutException cause)
		{
			super(cause);
		}
		
		@Override
		public TimeoutException getCause()
		{
			return (TimeoutException) super.getCause();
		}
	}
}
