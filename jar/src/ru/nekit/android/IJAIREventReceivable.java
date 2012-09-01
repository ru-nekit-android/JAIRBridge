package ru.nekit.android;

import com.adobe.fre.FREObject;

public interface IJAIREventReceivable {

	void onEventReceive(String name, FREObject[] args);
	
}
