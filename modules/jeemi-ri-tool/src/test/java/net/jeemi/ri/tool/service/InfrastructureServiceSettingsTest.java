package net.jeemi.ri.tool.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class InfrastructureServiceSettingsTest
{
	private static final int SOME_ADMIN_PORT = 1;
	private static final int SOME_MANAGEMENT_PORT = 2;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public InfrastructureServiceSettingsTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getAdminPort()
	throws Exception
	{
		InfrastructureServiceSettings settings =
				InfrastructureServiceSettings.builder()
			.withAdminPort(SOME_ADMIN_PORT)
			.build();
		
		int adminPort = settings.getAdminPort();
		
		assertThat(adminPort).isEqualTo(SOME_ADMIN_PORT);
	}
	
	@Test
	public void getManagementPort()
	throws Exception
	{
		InfrastructureServiceSettings settings =
				InfrastructureServiceSettings.builder()
			.withManagementPort(SOME_MANAGEMENT_PORT)
			.build();
		
		int managementPort = settings.getManagementPort();
		
		assertThat(managementPort).isEqualTo(SOME_MANAGEMENT_PORT);
	}
}
