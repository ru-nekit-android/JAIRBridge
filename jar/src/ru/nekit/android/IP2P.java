package ru.nekit.android;

public interface IP2P 
{
	
	void connectP2P(String groupSuffix);
	
	void registerP2PStatuEventReceiver(IP2PStatusEventReceivable receiver);
	void unregisterP2PStatusEventReceiver(IP2PStatusEventReceivable receiver);

}
