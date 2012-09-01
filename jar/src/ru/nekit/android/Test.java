package ru.nekit.android;

import android.content.Context;
import android.os.Vibrator;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class Test implements FREFunction {

	JAIRBridgeContext context;

	public FREObject call(FREContext context, FREObject[] arg)
	{
		this.context = (JAIRBridgeContext) context;
		vibrate();
		return null;
	}

	public void vibrate()
	{
		long time = 500;
		Vibrator vibrator = (Vibrator) context.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(time);
	}

}