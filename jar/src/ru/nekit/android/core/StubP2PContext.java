package ru.nekit.android.core;

import ru.nekit.android.core.interfaces.IP2P;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import ru.nekit.android.model.ClientProxy;
import android.content.Context;

public class StubP2PContext implements IP2P {


	private static StubP2PContext instance;

	public static StubP2PContext getInstance(Context context)
	{
		if( instance == null )
		{
			instance = new StubP2PContext();
		}
		return instance;
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