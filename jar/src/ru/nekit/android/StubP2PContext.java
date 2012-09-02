package ru.nekit.android;

import android.content.Context;

public class StubP2PContext implements IP2P {

	private Context context;
	
	public StubP2PContext(Context context)
	{
		this.context = context;
	}

	@Override
	public void connectP2P(String groupSuffix) 
	{

	}

	@Override
	public void registerP2PStatuEventReceiver(IP2PStatusEventReceivable receiver) 
	{

	}

	@Override
	public void unregisterP2PStatusEventReceiver(IP2PStatusEventReceivable receiver) 
	{

	}

}
