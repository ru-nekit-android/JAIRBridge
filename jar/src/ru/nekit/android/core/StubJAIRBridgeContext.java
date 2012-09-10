package ru.nekit.android.core;

import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IJAIREventReceivable;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;

public class StubJAIRBridgeContext implements IJAIR
{

	private static StubJAIRBridgeContext instance;
	private Context context;

	public static StubJAIRBridgeContext getInstance(Context context)
	{
		if( instance == null )
		{
			instance = new StubJAIRBridgeContext();
			instance.context = context;
		}
		return instance;
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

	@Override
	public void setCurrentActivity(Activity activity) 
	{

	}

	@Override
	public void restartBridge() 
	{

	}

	@Override
	public void check() 
	{

	}

	@Override
	public void destroyBridge() 
	{

	}

}