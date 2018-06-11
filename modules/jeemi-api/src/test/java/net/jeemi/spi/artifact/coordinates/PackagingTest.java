package net.jeemi.spi.artifact.coordinates;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class PackagingTest
{
	private static final String UNKNOWN_TYPE_NAME = "unknown";
	private static final String KNOWN_TYPE_NAME = "jar";
	private static final Packaging KNOWN_TYPE = Packaging.JAR;
	
	@Rule
	public final MockitoRule mockitoRule;
	
	public PackagingTest()
	{
		mockitoRule = MockitoJUnit.rule();
	}
	
	@Test
	public void lookupUnknown()
	throws Exception
	{
		Optional<Packaging> packaging = Packaging.lookup(UNKNOWN_TYPE_NAME);
		
		assertThat(packaging).isEmpty();
	}
	
	@Test
	public void lookupKnown()
	throws Exception
	{
		Optional<Packaging> packaging = Packaging.lookup(KNOWN_TYPE_NAME);
		
		assertThat(packaging).hasValue(KNOWN_TYPE);
	}
}
