package net.jeemi.ri.tool.service.admin.command;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

import java.util.List;

public class AdminDeleteDomainCommand
extends AdminCommand<Void>
{
	private final String domainName;
	
	private AdminDeleteDomainCommand(AdminCommandContext context,
			BuilderImpl builder)
	{
		super(context);
		domainName = builder.domainName;
	}
	
	public static BuilderDomainName builder()
	{
		return new BuilderImpl();
	}
	
	@Override
	protected String getName()
	{
		return "delete-domain";
	}
	
	@Override
	protected List<String> getArguments()
	{
		return singletonList(domainName);
	}
	
	public interface BuilderDomainName
	{
		Builder withDomainName(String domainName);
	}
	
	public interface Builder
	{
		AdminDeleteDomainCommand build(AdminCommandContext context);
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
		public AdminDeleteDomainCommand build(AdminCommandContext context)
		{
			return new AdminDeleteDomainCommand(context, this);
		}
	}
}
