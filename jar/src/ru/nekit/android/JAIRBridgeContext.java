package ru.nekit.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class JAIRBridgeContext extends FREContext implements IJAIR {

	public static final String LEVEL_ERROR = "ru.nekit.error";
	public static final String LEVEL_SERVICE = "ru.nekit.service";
	public static final String LEVEL_PUBLISH = "ru.nekit.publish";

	public static final String STARTUP = "startup";

	private static JAIRBridgeContext context;
	private Map<String, Object> publishMap;
	private List<IJAIREventReceivable> receiverList;

	private JAIRBridgeContext()
	{
		publishMap = new HashMap<String, Object>();
		receiverList = new ArrayList<IJAIREventReceivable>();
	};

	public Map<String, Object> getPublishMap()
	{
		return publishMap;
	}

	public List<IJAIREventReceivable> getReceiverList()
	{
		return receiverList;
	}

	public static JAIRBridgeContext getInstance()
	{
		if( context == null )
		{
			context = new JAIRBridgeContext();
		}
		return context;
	};

	@Override
	public Map<String, FREFunction> getFunctions() 
	{
		Map<String, FREFunction> map = 	new HashMap<String, FREFunction>();
		map.put("test", 				new Test());
		map.put("startUp", 				new StartUp());
		map.put("version", 				new Version());
		map.put("getPublishValue", 		new GetPublishValue());
		map.put("dispatchStatusEvent", 	new DispatchStatusEvent());
		return map;
	}

	private static class Version implements FREFunction {

		public FREObject call(FREContext context, FREObject[] arg)
		{
			FREObject version =  null;
			try {
				version = FREObject.newObject("0.1");
			} catch (FREWrongThreadException e) 
			{
				JAIRBridgeContext.context.dispatchStatusEvent(LEVEL_ERROR, e.getMessage());
			} 
			return version;
		}
	}

	public void dispatchServiceEvent(String description)
	{
		dispatchStatusEventAsync(description, LEVEL_SERVICE);
	}

	public void dispatchErrorEvent(String description)
	{
		dispatchStatusEventAsync(description, LEVEL_ERROR);
	}

	public void publishValue(String name, int value)
	{
		String id = "int." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	public void publishValue(String name, double value)
	{
		String id = "double." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	public void publishValue(String name, String value)
	{
		String id = "string." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	public void publishValue(String name, boolean value)
	{
		String id = "boolean." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}
	
	public void publishValue(String name, View value)
	{
		String id = "view::bitmapData." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	public void registerEventReserver(IJAIREventReceivable receiver)
	{
		if( !receiverList.contains(receiver) )
		{
			receiverList.add(receiver);
		}
	}

	public void unregisterEventReserver(IJAIREventReceivable receiver)
	{
		receiverList.remove(receiver);
	}

	public void dispatchStatusEvent(String level, String code)
	{
		if( code == null )
		{
			code = "";
		}
		if( level == null )
		{
			level = "";
		}
		dispatchStatusEventAsync(code, level);
	}

	@Override
	public void dispose() 
	{
		publishMap.clear();
		receiverList.clear();
	}
}