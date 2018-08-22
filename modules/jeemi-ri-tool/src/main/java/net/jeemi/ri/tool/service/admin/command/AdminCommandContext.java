package net.jeemi.ri.tool.service.admin.command;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

public class AdminCommandContext
{
	private final String host;
	private final Integer port;
	private final String user;
	private final Map<String, char[]> passwords;
	
	private AdminCommandContext(BuilderImpl builder)
	{
		host = builder.host;
		port = builder.port;
		user = builder.user;
		passwords = unmodifiableMap(builder.passwords);
	}

	public static Builder builder()
	{
		return new BuilderImpl();
	}
	
	protected final List<String> prepare(Random random)
	throws IOException
	{
		return Stream.of(
			ofNullable(host)
				.map(value -> Stream.of("--host", value)
					.collect(toList()))
				.orElseGet(Collections::emptyList),
			ofNullable(port)
				.map(value -> Stream.of("--port", value)
					.collect(toList()))
				.orElseGet(Collections::emptyList),
			ofNullable(user)
				.map(value -> Stream.of("--user", value)
					.collect(toList()))
				.orElseGet(Collections::emptyList),
			passwords.isEmpty()
				? emptyList()
				: Stream.of(
					"--passwordfile",
					createPasswordFile(random).getAbsolutePath())
						.collect(toList()))
			.flatMap(List::stream)
			.map(Object::toString)
			.collect(toList());
	}
	
	private File createPasswordFile(Random random)
	throws IOException
	{
		byte[] passwordsFileSuffix = new byte[20];
	    random.nextBytes(passwordsFileSuffix);
		
		File passwordFile = new File(
			getProperty("java.io.tmpdir"),
			format("jeemi-passwordfile-%s", new String(
		    	passwordsFileSuffix,
		    	StandardCharsets.US_ASCII)));
		passwordFile.deleteOnExit();
		
		writePasswordFile(passwordFile);
		return passwordFile;
	}
	
	private void writePasswordFile(File passwordFile)
	throws IOException
	{
		try (OutputStream output = new FileOutputStream(passwordFile))
		{
			Properties properties = new Properties();
			properties.putAll(passwords);
			properties.store(output, null);
		}
	}
	
	public interface Builder
	{
		Builder withHost(String host);
		
		Builder withPort(Integer port);
		
		Builder withUser(String user);
		
		Builder withMasterPassword(char[] masterPassword);
		
		AdminCommandContext build();
	}
	
	private static class BuilderImpl
	implements Builder
	{
		private String host;
		private Integer port;
		private String user;
		private Map<String, char[]> passwords;
		
		private BuilderImpl()
		{
			host = null;
			port = null;
			user = null;
			passwords = new HashMap<>();
		}
		
		@Override
		public Builder withHost(String host)
		{
			this.host = host;
			return this;
		}
		
		@Override
		public Builder withPort(Integer port)
		{
			this.port = port;
			return this;
		}
		
		@Override
		public Builder withUser(String user)
		{
			this.user = user;
			return this;
		}
		
		@Override
		public Builder withMasterPassword(char[] masterPassword)
		{
			passwords.put(
				"AS_ADMIN_MASTER_PASSWORD",
				requireNonNull(masterPassword));
			return this;
		}
		
		@Override
		public AdminCommandContext build()
		{
			return new AdminCommandContext(this);
		}
	}
}
