package ru.nekit.android.core.interfaces;

import com.adobe.fre.FREObject;

public interface IP2PEventReceivable 
{

	void onP2PStatusEventReceive(String name, FREObject[] args);
	
}
