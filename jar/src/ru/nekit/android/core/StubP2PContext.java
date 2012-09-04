package ru.nekit.android.core;

import ru.nekit.android.core.interfaces.IP2P;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import ru.nekit.android.model.ClientProxy;
import android.content.Context;

public class StubP2PContext implements IP2P {

	@SuppressWarnings("unused")
	private Context context;
	
	public StubP2PContext(Context context)
	{
		this.context = context;
	}

	@Override
	public void connect(String groupSuffix) 
	{

	}

	@Override
	public void registerP2PStatuEventReceiver(IP2PEventReceivable receiver) 
	{

	}

	@Override
	public void unregisterP2PStatusEventReceiver(IP2PEventReceivable receiver) 
	{

	}

	@Override
	public void setCurrentP2PClient(ClientProxy client) 
	{
		
	}

	@Override
	public void disconnect()
	{
		
	}

}