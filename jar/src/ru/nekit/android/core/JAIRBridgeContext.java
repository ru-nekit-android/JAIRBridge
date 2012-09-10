package ru.nekit.android.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.nekit.android.core.functions.DispatchEvent;
import ru.nekit.android.core.functions.GetPublishValue;
import ru.nekit.android.core.functions.StartUp;
import ru.nekit.android.core.functions.Test;
import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IJAIREventReceivable;
import ru.nekit.android.core.interfaces.IP2P;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import ru.nekit.android.model.ClientProxy;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.util.Log;
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

	private static String CURRENT_ACTIVITY = "current_activity";

	//event callback 
	private static final String RESTART = "restart";
	private static final String RESTORE = "restore";
	private static final String STARTUP = "startup";
	private static final String DESTROY = "destroy";
	private static final String MOVE_TO_BACK = "move_to_back";
	//p2p part
	public static final String CONNECT_TO_GROUP = "connect_to_group";
	public static final String CONNECT_CLIENT = "connect_client";

	private static JAIRBridgeContext instance;

	public static String appEntryName;

	private Map<String, Object> publishMap;

	private List<IJAIREventReceivable> eventListReceiverList;
	private List<IP2PEventReceivable> p2pEventReceiverList;

	private Map<String, ArgList> executeList;

	public static JAIRBridgeContext getInstance()
	{
		if( instance == null )
		{
			instance = new JAIRBridgeContext();
		}
		return instance;
	}

	public void setCurrentActivity(Activity activity)
	{
		Editor editor = PreferenceManager.getDefaultSharedPreferences(activity).edit();
		editor.putString(CURRENT_ACTIVITY, 
				activity != null ? activity.getComponentName().getClassName() : null);
		editor.commit();
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
		map.put("dispatchEvent", 			new DispatchEvent());
		map.put("destroyBridge", 			new DestroyBridge());
		map.put("restoreBridge", 			new RestoreBridge());
		map.put("moveBridgeToBack", 		new MoveBridgeToBack());
		map.put("memoryReport", 			new MemoryReport());
		return map;
	}

	private static class DestroyBridge implements FREFunction 
	{

		@Override
		public FREObject call(FREContext _context, FREObject[] args) 
		{
			JAIRBridgeContext.instance.destroyBridge();
			return null;
		}
	}

	private static class MoveBridgeToBack implements FREFunction 
	{

		@Override
		public FREObject call(FREContext _context, FREObject[] args) 
		{
			boolean nonRoot = false;
			try {
				nonRoot = args[0].getAsBool();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (FRETypeMismatchException e) {
				e.printStackTrace();
			} catch (FREInvalidObjectException e) {
				e.printStackTrace();
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			JAIRBridgeContext.instance.moveBridgeToBack(nonRoot);
			return null;
		}
	}

	private static class MemoryReport implements FREFunction 
	{

		@Override
		public FREObject call(FREContext _context, FREObject[] args) 
		{
			try {
				FREObject.newObject(JAIRBridgeContext.instance.memoryReport());
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static class RestoreBridge implements FREFunction 
	{

		@Override
		public FREObject call(FREContext _context, FREObject[] args) 
		{
			JAIRBridgeContext.instance.restoreBridge();
			return null;
		}
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
			JAIRBridgeContext.instance.execute(name);
			return null;
		}
	}

	private static class Version implements FREFunction 
	{
		public FREObject call(FREContext context, FREObject[] arg)
		{
			FREObject version =  null;
			try {
				version = FREObject.newObject("0.84");
			} catch (FREWrongThreadException e) 
			{
				JAIRBridgeContext.instance.dispatchStatusEvent(LEVEL_ERROR, e.getMessage());
			} 
			return version;
		}
	}

	@Override
	public void destroyBridge()
	{
		Activity appEntry = getActivity();
		if( appEntry != null )
		{	
			appEntry.finish();
			System.gc();
			dispatchServiceEvent(DESTROY);
		}
	}

	private void moveBridgeToBack(boolean root)
	{
		Activity appEntry = getActivity();
		if( appEntry != null )
		{	
			appEntry.moveTaskToBack(root);
			dispatchServiceEvent(MOVE_TO_BACK);
		}
	}

	private String memoryReport()
	{
		Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
		Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
		Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2); 
		return df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)\n"
		+ "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)";
	}

	private void restoreBridge()
	{
		String currentActivity =  PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(CURRENT_ACTIVITY, null);
		Log.d("ru.nekit.jair", "jair:Wait restore: " + currentActivity);
		if( currentActivity != null )
		{
			Intent intent = new Intent();
			intent.setClassName(getActivity(), currentActivity);
			intent.setFlags(Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			getActivity().startActivity(intent);
			dispatchServiceEvent(RESTORE);
			setCurrentActivity(null);
		}else{

		}
	}

	@Override
	public void restartBridge()
	{

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
		setCurrentActivity(null);
	}

	public static String AEPN = "eapn";
	public static String AECN = "eacn";

	public void sturtUp(Activity appEntry) throws IllegalStateException, FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
	{
		appEntryName = appEntry.getComponentName().getClassName();
		Editor e = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
		e.putString(AEPN, appEntry.getComponentName().getPackageName());
		e.putString(AECN, appEntry.getComponentName().getClassName());
		e.commit();
		Log.d("ru.nekit.jair",  "startUp jair::"+appEntry);
		dispatchServiceEvent(STARTUP);	
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
	public void check() 
	{
		dispatchServiceEvent("ru.nekit.check");
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