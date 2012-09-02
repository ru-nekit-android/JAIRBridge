package ru.nekit.android;

import com.adobe.fre.FREObject;

public interface IP2PStatusEventReceivable {

	
	void onP2PStatusEventReceive(String name, FREObject[] args);
	
}
