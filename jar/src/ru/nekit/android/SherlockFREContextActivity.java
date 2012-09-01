package ru.nekit.android;

import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class SherlockFREContextActivity extends SherlockActivity implements IJAIR {

	private IJAIR context;

	public SherlockFREContextActivity()
	{
		super();
		if( CONFIG.USE_REVERSE_BRIDGE )
		{
			context = JAIRBridgeContext.getInstance();
		}
		else
		{
			context = new StubJAIRBridgeContext(this);
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
	public void registerEventReserver(IJAIREventReceivable receiver) 
	{
		context.registerEventReserver(receiver);
	}

	@Override
	public void unregisterEventReserver(IJAIREventReceivable receiver) 
	{
		context.unregisterEventReserver(receiver);
	}

	@Override
	public void publishValue(String name, View value) 
	{
		context.publishValue(name, value);
	}
}