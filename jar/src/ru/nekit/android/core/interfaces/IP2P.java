package ru.nekit.android.core.interfaces;

import ru.nekit.android.model.ClientProxy;

public interface IP2P 
{
	
	void connect(String groupSuffix);
	void disconnect();
	
	void setCurrentP2PClient(ClientProxy client);
	
	void registerP2PStatuEventReceiver(IP2PEventReceivable receiver);
	void unregisterP2PStatusEventReceiver(IP2PEventReceivable receiver);

}