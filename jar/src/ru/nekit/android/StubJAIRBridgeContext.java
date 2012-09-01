package ru.nekit.android;

import android.content.Context;
import android.view.View;

public class StubJAIRBridgeContext implements IJAIR
{

	private Context context;

	public StubJAIRBridgeContext(Context contxt)
	{
		this.context = contxt;
	}

	@Override
	public void dispatchServiceEvent(String description)
	{

	}

	@Override
	public void dispatchStatusEvent(String level, String code) 
	{

	}

	@Override
	public int getResourceId(String id) 
	{
		String[] values = id.split("\\.");
		return context.getResources().getIdentifier(values[1], values[0], context.getPackageName());
	}

	@Override
	public void publishValue(String name, int value) 
	{

	}

	@Override
	public void publishValue(String name, double value)
	{

	}

	@Override
	public void publishValue(String name, String value) 
	{

	}

	@Override
	public void publishValue(String name, boolean value) 
	{

	}

	@Override
	public void dispatchErrorEvent(String description)
	{

	}

	@Override
	public void registerEventReserver(IJAIREventReceivable receiver) 
	{

	}

	@Override
	public void unregisterEventReserver(IJAIREventReceivable receiver) 
	{

	}

	@Override
	public void publishValue(String name, View value) 
	{

	}

}