package ru.nekit.android;

import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class SherlockFREContextActivity extends SherlockActivity implements IJAIR, IP2P {

	protected IJAIR context;
	protected IP2P p2pContext;

	public SherlockFREContextActivity()
	{
		super();
		if( CONFIG.USE_REVERSE_BRIDGE )
		{
			context = JAIRBridgeContext.getInstance();
			p2pContext = JAIRBridgeContext.getInstance();
		}
		else
		{
			context = new StubJAIRBridgeContext(this);
			p2pContext = new StubP2PContext(this);
		}
	}

	@Override
	public void dispatchServiceEvent(String description) 
	{
		context.dispatchServiceEvent(description);
	}

	@Override
	public void dispatchStatusEvent(String level, String code)
	{
		context.dispatchStatusEvent(level, code);
	}

	@Override
	public int getResourceId(String id) 
	{	
		return context.getResourceId(id);
	}

	@Override
	public void publishValue(String name, int value) 
	{
		context.publishValue(name, value);
	}

	@Override
	public void publishValue(String name, double value) 
	{
		context.publishValue(name, value);
	}

	@Override
	public void publishValue(String name, String value)
	{
		context.publishValue(name, value);	
	}

	@Override
	public void publishValue(String name, boolean value) 
	{
		context.publishValue(name, value);
	}

	@Override
	public void dispatchErrorEvent(String description)
	{
		context.dispatchErrorEvent(description);
	}

	@Override
	public void registerStatusEventReceiver(IJAIRStatusEventReceivable receiver) 
	{
		context.registerStatusEventReceiver(receiver);
	}

	@Override
	public void unregisterStatusEventReceiver(IJAIRStatusEventReceivable receiver) 
	{
		context.unregisterStatusEventReceiver(receiver);
	}

	@Override
	public void publishValue(String name, View value) 
	{
		context.publishValue(name, value);
	}

	@Override
	public void connectP2P(String groupSuffix) 
	{
		p2pContext.connectP2P(groupSuffix);
	}

	@Override
	public void registerP2PStatuEventReceiver(IP2PStatusEventReceivable receiver) 
	{
		p2pContext.registerP2PStatuEventReceiver(receiver);
	}

	@Override
	public void unregisterP2PStatusEventReceiver(IP2PStatusEventReceivable receiver) 
	{
		p2pContext.unregisterP2PStatusEventReceiver(receiver);
	}
}