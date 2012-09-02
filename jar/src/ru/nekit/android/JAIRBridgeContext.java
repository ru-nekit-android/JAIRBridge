package ru.nekit.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class JAIRBridgeContext extends FREContext implements IJAIR, IP2P {

	private static final String LEVEL_ERROR = "ru.nekit.error";
	private static final String LEVEL_SERVICE = "ru.nekit.service";
	private static final String LEVEL_PUBLISH = "ru.nekit.publish";
	private static final String LEVEL_EXECUTE = "ru.nekit.execute";
	private static final String LEVEL_P2P = "ru.nekit.p2p";

	private static final String STARTUP = "startup";
	public static final String CONNECT_P2P = "connectP2P";

	private static JAIRBridgeContext context;
	private Map<String, Object> publishMap;
	private List<IJAIRStatusEventReceivable> statusEventListReceiver;
	private List<IP2PStatusEventReceivable> p2pStatusEventReceiverList;
	private static HashMap<String, ArrayList<Item>> executeList;

	private JAIRBridgeContext()
	{
		publishMap = new HashMap<String, Object>();
		statusEventListReceiver = new ArrayList<IJAIRStatusEventReceivable>();
		p2pStatusEventReceiverList = new ArrayList<IP2PStatusEventReceivable>();
		executeList = new HashMap<String, ArrayList<Item>>();
	};

	public Map<String, Object> getPublishMap()
	{
		return publishMap;
	}

	public List<IJAIRStatusEventReceivable> getStatusEventReceiverList()
	{
		return statusEventListReceiver;
	}

	public List<IP2PStatusEventReceivable> getP2PStatusEventReceiverList()
	{
		return p2pStatusEventReceiverList;
	}

	public FREObject getP2PConnectionEntry() throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
	{
		return FREObject.newObject("ru.nekit.ane.P2PConnectionEntry", null);
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
		map.put("execute", 				new Execute());
		map.put("getPublishValue", 		new GetPublishValue());
		map.put("dispatchStatusEvent", 	new DispatchStatusEvent());
		map.put("dispatchP2PStatusEvent", 	new DispatchP2PStatusEvent());
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

	public void registerStatusEventReceiver(IJAIRStatusEventReceivable receiver)
	{
		if( !statusEventListReceiver.contains(receiver) )
		{
			statusEventListReceiver.add(receiver);
		}
	}

	public void unregisterStatusEventReceiver(IJAIRStatusEventReceivable receiver)
	{
		statusEventListReceiver.remove(receiver);
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
		statusEventListReceiver.clear();
		p2pStatusEventReceiverList.clear();
		executeList.clear();
	}

	public void sturtUp()
	{
		dispatchServiceEvent(JAIRBridgeContext.STARTUP);	
		try {
			getP2PConnectionEntry().callMethod("init", null);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREASErrorException e) {
			e.printStackTrace();
		} catch (FRENoSuchNameException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
	}

	private void waitExecute(String name, ArrayList<Item> args)
	{
		executeList.put(name, args);
		dispatchStatusEvent(LEVEL_EXECUTE, name);
	}

	public void execute(String name)
	{
		ArrayList<Item> sargs = executeList.remove(name);
		int length = sargs.size();
		FREObject[] args = new FREObject[length];
		for( int i = 0; i < length; i++)
		{
			Item item = sargs.get(i);
			try {
				if( item.type == "string" )
				{
					args[i] = FREObject.newObject((String)item.value);
				}
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
		}
		try {
			getP2PConnectionEntry().callMethod(name, args);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREASErrorException e) {
			e.printStackTrace();
		} catch (FRENoSuchNameException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		dispatchServiceEvent("end");
	}

	@Override
	public void connectP2P(String groupSuffix) 
	{
		ArrayList<Item> args = new ArrayList<Item>();
		args.add(new Item("string", groupSuffix));
		waitExecute("connect", args);
	}

	private class Item
	{
		public String type;
		public Object value;

		public Item(String type, Object value)
		{
			this.type = type;
			this.value = value;
		}

	}

	@Override
	public void registerP2PStatuEventReceiver(IP2PStatusEventReceivable receiver) {
		if( !p2pStatusEventReceiverList.contains(receiver) )
		{
			p2pStatusEventReceiverList.add(receiver);
		}
	}

	@Override
	public void unregisterP2PStatusEventReceiver(IP2PStatusEventReceivable receiver)
	{
		p2pStatusEventReceiverList.remove(receiver);
	}
}