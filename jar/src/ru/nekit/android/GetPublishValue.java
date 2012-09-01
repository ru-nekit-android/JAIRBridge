package ru.nekit.android;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class GetPublishValue implements FREFunction {

	private JAIRBridgeContext context;

	@Override
	public FREObject call(FREContext _context, FREObject[] args) 
	{
		String id = null;
		try {
			id = args[0].getAsString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		String type = id.split("\\.")[0];
		context = (JAIRBridgeContext)_context;
		if( id != null )
		{
			if( context.getPublishMap().containsKey(id) )
			{
				try {
					if( type.equals("int") )
					{
						return FREObject.newObject(((Integer)context.getPublishMap().remove(id)).intValue());
					}
					if( type.equals("boolean") )
					{
						return FREObject.newObject(((Boolean)context.getPublishMap().remove(id)).booleanValue());
					}
					if( type.equals("string") )
					{
						return FREObject.newObject((String)context.getPublishMap().remove(id));
					}
					if( type.equals("double") )
					{
						return FREObject.newObject(((Double)context.getPublishMap().remove(id)).doubleValue());
					}
				} catch (FREWrongThreadException e) {
					e.printStackTrace();
					context.dispatchErrorEvent("Error get value for key: " + id);
				}
			}
			else
			{
				context.dispatchErrorEvent("No value for key: " + id);
			}
		}
		return null;
	}
}