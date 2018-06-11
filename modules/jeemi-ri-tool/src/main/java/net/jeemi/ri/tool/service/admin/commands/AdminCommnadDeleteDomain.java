package net.jeemi.ri.tool.service.admin.commands;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

import java.io.InputStream;
import java.util.List;

import net.jeemi.ri.tool.service.admin.AdminCommand;

public class AdminCommnadDeleteDomain
extends AdminCommand<Void>
{
	public static final String NAME = "delete-domain";
	
	private final String domainName;

	private AdminCommnadDeleteDomain(BuilderImpl builder)
	{
		domainName = builder.domainName;
	}
	
	@Override
	protected String getName()
	{
		return NAME;
	}

	@Override
	protected List<String> getParameters()
	{
		return singletonList(domainName);
	}

	@Override
	protected Void compute(InputStream out)
	{
		return null;
	}

	@Override
	protected RuntimeException compute(InputStream err, int exitValue)
	{
		return new RuntimeException();
	}
	
	public static BuilderDomainName builder()
	{
		return new BuilderImpl();
	}
	
	public interface BuilderDomainName
	{
		Builder withDomainName(String name);
	}
	
	public interface Builder
	{
		AdminCommnadDeleteDomain build();
	}
	
	private static class BuilderImpl
	implements BuilderDomainName, Builder
	{
		private String domainName;
		
		public BuilderImpl()
		{
			domainName = null;
		}

		@Override
		public Builder withDomainName(String domainName)
		{
			this.domainName = requireNonNull(domainName);
			return this;
		}

		@Override
		public AdminCommnadDeleteDomain build()
		{
			return new AdminCommnadDeleteDomain(this);
		}
	}
}
