package net.jeemi.ri.tool.service.admin;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

public class AdminCommandContext
{
	private final Random random;
	private final File binDir;
	private final String host;
	private final Integer port;
	private final String user;
	private final Properties passwords;
	
	private AdminCommandContext(BuilderImpl builder)
	{
		random = builder.random;
		binDir = builder.binDir;
		host = builder.host;
		port = builder.port;
		user = builder.user;
		passwords = builder.passwords;
	}
	
	public List<String> arguments(String name, List<String> params)
	{
		List<String> args = new ArrayList<>();
		
		// TODO OS checking for Windows (asadmin.bat)
		File asadminFile = new File(binDir, "asadmin");
		args.add(asadminFile.getAbsolutePath());
		
		ofNullable(host)
			.ifPresent(host -> args.addAll(Stream.of("--host", host)
				.collect(toList())));
		ofNullable(port)
			.ifPresent(port -> args.addAll(Stream.of("--port", port.toString())
				.collect(toList())));
		ofNullable(user)
			.ifPresent(user -> args.addAll(Stream.of("--user", user)
				.collect(toList())));
		if (!passwords.isEmpty())
		{
			File passwordFile = createPasswordFile();
			args.add("--passwordfile");
			args.add(passwordFile.getAbsolutePath());
		}
		
		args.add(name);
		args.addAll(params);
		return args;
	}
	
	public static BuilderRandom builder()
	{
		return new BuilderImpl();
	}
	
	private File createPasswordFile()
	{
		byte[] passwordsFileSuffix = new byte[20];
	    random.nextBytes(passwordsFileSuffix);
		
		File passwordFile = new File(
			getProperty("java.io.tmpdir"),
			format("jeemi-passwordfile-%s", new String(
		    	passwordsFileSuffix,
		    	StandardCharsets.US_ASCII)));
		passwordFile.deleteOnExit();
		
		return passwordFile;
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
		Builder withHost(String host);
		
		Builder withPort(Integer port);
		
		Builder withUser(String user);
		
		Builder withPasswords(Properties passwords);
		
		AdminCommandContext build();
	}
	
	private static class BuilderImpl
	implements BuilderRandom, BuilderBinDir, Builder
	{
		private Random random;
		private File binDir;
		private String host;
		private Integer port;
		private String user;
		private Properties passwords;
		
		private BuilderImpl()
		{
			random = null;
			binDir = null;
			host = null;
			port = null;
			user = null;
			passwords = null;
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
		public Builder withPasswords(Properties passwords)
		{
			this.passwords = requireNonNull(passwords);
			return this;
		}

		@Override
		public AdminCommandContext build()
		{
			return new AdminCommandContext(this);
		}
	}
}
