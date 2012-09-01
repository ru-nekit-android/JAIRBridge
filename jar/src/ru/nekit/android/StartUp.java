package ru.nekit.android;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class StartUp implements FREFunction {


	public FREObject call(FREContext context, FREObject[] arg)
	{
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
		((JAIRBridgeContext)context).dispatchServiceEvent(JAIRBridgeContext.STARTUP);
		return null;
	}
}