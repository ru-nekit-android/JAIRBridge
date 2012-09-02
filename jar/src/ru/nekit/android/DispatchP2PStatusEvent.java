package ru.nekit.android;

import java.util.List;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class DispatchP2PStatusEvent implements FREFunction {

	JAIRBridgeContext context;

	@Override
	public FREObject call(FREContext _context, FREObject[] args) 
	{
		context = (JAIRBridgeContext)_context;
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
		final int length = args.length - 1;
		FREObject[] arguments = new FREObject[length];
		for( int i = 0; i < length; i++)
		{
			arguments[i] = args[i+1];
		}
		List<IP2PStatusEventReceivable> list = context.getP2PStatusEventReceiverList();
		for( IP2PStatusEventReceivable receiver : list )
		{
			receiver.onP2PStatusEventReceive(name, arguments);
		}
		return null;
	}

}
