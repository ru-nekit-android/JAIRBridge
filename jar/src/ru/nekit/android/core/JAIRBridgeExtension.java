package ru.nekit.android.core;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class JAIRBridgeExtension implements FREExtension {

	public static JAIRBridgeContext context;

	public FREContext createContext(String name)
	{
		return context;
	}

	public void dispose() 
	{
		context.dispose();
		context = null;
	}

	public void initialize()
	{
		context = JAIRBridgeContext.getInstance();
	}
}
