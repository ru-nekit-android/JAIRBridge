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
		boolean newTask = true;
		boolean waitingResult = true;
		Intent intent = new Intent(context.getActivity(), StartUpActivity.class); 
		try {
			backgroud = arg[0].getAsBool();
			if( backgroud )
			{
				context.getActivity().moveTaskToBack(arg[1].getAsBool());
			}
			newTask = arg[2].getAsBool();
			if( newTask )
			{
				intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
			}
			waitingResult = arg[3].getAsBool();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		if( waitingResult )
		{
			context.getActivity().startActivityForResult(intent, -1);
		}
		else
		{
			context.getActivity().startActivity(intent);
		}
		context.sturtUp();
		context = null;
		return null;
	}
}