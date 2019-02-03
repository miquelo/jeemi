package net.jeemi.ri.machine.integration.tools.platform;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlatformProvider
{
	public PlatformProvider()
	{
	}
	
	public void up()
	throws IOException, InterruptedException
	{
		ProcessBuilder pb = new ProcessBuilder(
			"/bin/bash",
			"-c",
			"vagrant");
		Process p = pb.start();
		p.waitFor(1, TimeUnit.MINUTES);
	}
}
