package net.jeemi.ri.agent.gf4.spi.machinery;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import net.jeemi.ri.agent.gf4.spi.machinery.DASMachineSpecs;

public class DASMachineSpecsTest
{
	private static final PublicKey SOME_ADMIN_KEY;
	
	static
	{
		try
		{
			SOME_ADMIN_KEY = KeyPairGenerator.getInstance("DSA", "SUN")
				.generateKeyPair()
				.getPublic();
		}
		catch (NoSuchAlgorithmException | NoSuchProviderException exception)
		{
			throw new RuntimeException(exception);
		}
	}
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public DASMachineSpecsTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void getAdminKey()
	throws Exception
	{
		DASMachineSpecs specs = DASMachineSpecs.builder()
			.withAdminKey(SOME_ADMIN_KEY)
			.build();
		
		assertThat(specs.getAdminKey()).isEqualTo(SOME_ADMIN_KEY);
	}
}
