package ru.nekit.android;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class Execute implements FREFunction {

	private JAIRBridgeContext context;
	
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
		context.execute(name);
		return null;
	}

}
