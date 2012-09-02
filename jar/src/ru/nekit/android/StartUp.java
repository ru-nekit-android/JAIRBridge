package ru.nekit.android;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class StartUp implements FREFunction {

	private JAIRBridgeContext context;

	public FREObject call(FREContext _context, FREObject[] arg)
	{
		context = (JAIRBridgeContext)_context;
		boolean backgroud = true;
		try {
			backgroud = arg[0].getAsBool();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		if( backgroud )
		{
			context.getActivity().moveTaskToBack(true);
		}
		
		Intent intent = new Intent(context.getActivity(), StartUpActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.getActivity().startActivity(intent);
		context.sturtUp();
		context = null;
		return null;
	}
}