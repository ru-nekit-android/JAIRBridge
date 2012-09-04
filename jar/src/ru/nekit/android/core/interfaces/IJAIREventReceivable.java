package ru.nekit.android.core.interfaces;

import com.adobe.fre.FREObject;

public interface IJAIREventReceivable 
{

	void onStatusEventReceive(String name, FREObject[] args);
	
}