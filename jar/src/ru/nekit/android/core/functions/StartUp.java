package ru.nekit.android.core.functions;

import ru.nekit.android.activity.StartUpActivity;
import ru.nekit.android.core.JAIRBridgeContext;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class StartUp implements FREFunction {

	private JAIRBridgeContext context;

	public FREObject call(FREContext mContext, FREObject[] arg)
	{
		context = (JAIRBridgeContext)mContext;
		boolean backgroud = true;
		boolean nonRoot = false;
		boolean newTask = true;
		boolean waitingResult = true;
		Intent intent = new Intent(context.getActivity(), StartUpActivity.class); 
		try {
			backgroud = arg[0].getAsBool();
			nonRoot =  arg[1].getAsBool();
			newTask = arg[2].getAsBool();
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
		if( backgroud )
		{
			context.getActivity().moveTaskToBack(nonRoot);
		}
		if( newTask )
		{
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		if( waitingResult )
		{
			context.getActivity().startActivityForResult(intent, -1);
		}
		else
		{
			Log.v("ru.nekit.jair", "jair::start activity on startUp");
			context.getActivity().startActivity(intent);
		}
		try {
			context.sturtUp(context.getActivity());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREASErrorException e) {
			e.printStackTrace();
		} catch (FRENoSuchNameException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		context = null;
		return null;
	}
}