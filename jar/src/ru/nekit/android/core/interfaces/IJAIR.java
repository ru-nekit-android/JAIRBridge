package ru.nekit.android.core.interfaces;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;

public interface IJAIR
{

	void dispatchServiceEvent(String description);
	void dispatchErrorEvent(String description);
	void dispatchStatusEvent(String level, String code);
	
	int getResourceId(String id);
	
	void check();
	void destroyBridge();
	void restartBridge();
	void setCurrentActivity(Activity activity);
	
	
	void publishValue(String name, int value);
	void publishValue(String name, double value);
	void publishValue(String name, String value);
	void publishValue(String name, boolean value);
	void publishValue(String name, View value);
	void publishValue(String name, WebView value);
	
	void registerStatusEventReceiver(IJAIREventReceivable receiver);
	void unregisterStatusEventReceiver(IJAIREventReceivable receiver);
	
	
}