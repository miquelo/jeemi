package net.jeemi.agent;

public interface Application
{
	ApplicationName getName();
	
	void start();
	
	void stop();
	
	void exit();
}
