package ru.nekit.android.core.functions;

import java.util.List;

import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.interfaces.IJAIREventReceivable;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class DispatchEvent implements FREFunction
{

	@Override
	public FREObject call(FREContext mContext, FREObject[] args) 
	{
		JAIRBridgeContext context;
		context = (JAIRBridgeContext)mContext;
		String type = null;
		String name = null;
		try {
			type = args[0].getAsString();
			name = args[1].getAsString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		final int length = args.length - 2;
		FREObject[] arguments = new FREObject[length];
		for( int i = 0; i < length; i++)
		{
			arguments[i] = args[i+2];
		}
		if( "status".equals(type) )
		{
			List<IJAIREventReceivable> list = context.getEventReceiverList();
			for( IJAIREventReceivable receiver : list )
			{
				receiver.onStatusEventReceive(name, arguments);
			}
		}else if( "p2p".equals(type) )
		{
			List<IP2PEventReceivable> list = context.getP2PEventReceiverList();
			for( IP2PEventReceivable receiver : list )
			{
				receiver.onP2PStatusEventReceive(name, arguments);
			}
		}
		return null;
	}
}