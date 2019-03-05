package net.jeemi.machinery.agent;

import javax.security.auth.callback.CallbackHandler;

public interface MachineAgentAuthentication
{
	CallbackHandler[] getCallbackHandlers();
}
