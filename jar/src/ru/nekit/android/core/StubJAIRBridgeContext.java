package ru.nekit.android.core;

import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IJAIREventReceivable;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;

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
	public void registerStatusEventReceiver(IJAIREventReceivable receiver) 
	{

	}

	@Override
	public void unregisterStatusEventReceiver(IJAIREventReceivable receiver) 
	{

	}

	@Override
	public void publishValue(String name, View value) 
	{

	}

	@Override
	public void publishValue(String name, WebView value) 
	{
		
	}

}