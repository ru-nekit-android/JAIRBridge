package ru.nekit.android;

import com.adobe.fre.FREObject;

public interface IJAIRStatusEventReceivable {

	void onStatusEventReceive(String name, FREObject[] args);
	
}
