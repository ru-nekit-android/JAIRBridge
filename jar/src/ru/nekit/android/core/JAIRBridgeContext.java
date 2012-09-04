package ru.nekit.android.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nekit.android.core.functions.DispatchP2PStatusEvent;
import ru.nekit.android.core.functions.DispatchStatusEvent;
import ru.nekit.android.core.functions.GetPublishValue;
import ru.nekit.android.core.functions.StartUp;
import ru.nekit.android.core.functions.Test;
import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IJAIREventReceivable;
import ru.nekit.android.core.interfaces.IP2P;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import ru.nekit.android.model.ClientProxy;
import android.view.View;
import android.webkit.WebView;

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
	@SuppressWarnings("unused")
	private static final String LEVEL_P2P = "ru.nekit.p2p";

	private static final String STARTUP = "startup";

	public static final String CONNECT_TO_GROUP = "connect_to_group";
	public static final String CONNECT_CLIENT = "connect_client";

	private static JAIRBridgeContext context;
	private Map<String, Object> publishMap;
	private List<IJAIREventReceivable> eventListReceiverList;
	private List<IP2PEventReceivable> p2pEventReceiverList;
	private Map<String, ArgList> executeList;

	public static JAIRBridgeContext getInstance()
	{
		if( context == null )
		{
			context = new JAIRBridgeContext();
		}
		return context;
	}

	private JAIRBridgeContext()
	{
		publishMap = new HashMap<String, Object>();
		eventListReceiverList = new ArrayList<IJAIREventReceivable>();
		p2pEventReceiverList = new ArrayList<IP2PEventReceivable>();
		executeList = new HashMap<String, ArgList>();
	}

	public Map<String, Object> getPublishMap()
	{
		return publishMap;
	}

	public List<IJAIREventReceivable> getEventReceiverList()
	{
		return eventListReceiverList;
	}

	public List<IP2PEventReceivable> getP2PEventReceiverList()
	{
		return p2pEventReceiverList;
	}

	public FREObject getP2PConnectionEntry() throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
	{
		return FREObject.newObject("ru.nekit.ane.P2PConnectionEntry", null);
	}

	@Override
	public Map<String, FREFunction> getFunctions() 
	{
		Map<String, FREFunction> map = 		new HashMap<String, FREFunction>();
		map.put("test", 					new Test());
		map.put("startUp", 					new StartUp());
		map.put("version", 					new Version());
		map.put("execute", 					new Execute());
		map.put("getPublishValue", 			new GetPublishValue());
		map.put("dispatchStatusEvent", 		new DispatchStatusEvent());
		map.put("dispatchP2PStatusEvent", 	new DispatchP2PStatusEvent());
		return map;
	}

	private static class Execute implements FREFunction {

		@Override
		public FREObject call(FREContext _context, FREObject[] args) 
		{
			String name = null;
			try {
				name = args[0].getAsString();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			JAIRBridgeContext.context.execute(name);
			return null;
		}
	}

	private static class Version implements FREFunction 
	{
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
		String id = "bitmapData::view." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	@Override
	public void publishValue(String name, WebView value)
	{
		String id = "bitmapData::webView." + name;
		publishMap.put(id, value);
		dispatchStatusEvent(LEVEL_PUBLISH, id);
	}

	public void registerStatusEventReceiver(IJAIREventReceivable receiver)
	{
		if( !eventListReceiverList.contains(receiver) )
		{
			eventListReceiverList.add(receiver);
		}
	}

	public void unregisterStatusEventReceiver(IJAIREventReceivable receiver)
	{
		eventListReceiverList.remove(receiver);
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
		eventListReceiverList.clear();
		p2pEventReceiverList.clear();
		executeList.clear();
	}

	public void sturtUp() throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
	{
		dispatchServiceEvent(JAIRBridgeContext.STARTUP);	
		getP2PConnectionEntry().callMethod("init", null);
	}

	private void waitExecute(String name, ArgList args)
	{
		executeList.put(name, args);
		dispatchStatusEvent(LEVEL_EXECUTE, name);
	}

	public void execute(String name)
	{
		ArgList sargs = executeList.remove(name);
		FREObject[] args = null;
		if( sargs != null )
		{
			int length = sargs.size();
			args = new FREObject[length];
			for( int i = 0; i < length; i++)
			{
				ArgItem item = sargs.get(i);
				try {
					if( item.type == "string" )
					{
						args[i] = FREObject.newObject((String)item.value);
					}
				} catch (FREWrongThreadException e) {
					e.printStackTrace();
				}
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
	}

	@Override
	public void connect(String groupSuffix) 
	{
		waitExecute("connect", (new ArgList()).add("string", groupSuffix));
	}

	@Override
	public void setCurrentP2PClient(ClientProxy client) 
	{
		waitExecute("setCurrentClient", (new ArgList()).add("string", client.getValue().peerID));
	}

	@Override
	public void disconnect() 
	{
		execute("disconnect");	
	}

	@Override
	public void registerP2PStatuEventReceiver(IP2PEventReceivable receiver) {
		if( !p2pEventReceiverList.contains(receiver) )
		{
			p2pEventReceiverList.add(receiver);
		}
	}

	@Override
	public void unregisterP2PStatusEventReceiver(IP2PEventReceivable receiver)
	{
		p2pEventReceiverList.remove(receiver);
	}

	private class ArgList
	{

		private ArrayList<ArgItem> args;

		public ArgList()
		{
			args = new ArrayList<ArgItem>();
		}

		public int size()
		{
			return args.size();
		}

		public ArgItem get(int position)
		{
			return args.get(position);
		}

		public ArgList add(String name, Object value)
		{
			args.add(new ArgItem(name, value));
			return this;
		}
	}

	private class ArgItem
	{

		public String type;
		public Object value;

		public ArgItem(String type, Object value)
		{
			this.type = type;
			this.value = value;
		}
	}
}