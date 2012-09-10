package ru.nekit.android.activity.base;

import ru.nekit.android.CONFIG;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.StubJAIRBridgeContext;
import ru.nekit.android.core.StubP2PContext;
import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IP2P;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;

public class SherlockJAIRContextActivity extends SherlockActivity {

	protected IJAIR jairContext;
	protected IP2P p2pContext;

	public SherlockJAIRContextActivity()
	{
		super();
	}

	protected void log(String msg)
	{
		msg = "jair::Activity " + getComponentName().getClassName() + " " + msg;
		Log.d("ru.nekit.jair", msg);
		try{
			jairContext.dispatchServiceEvent(msg);
		}catch(UnsatisfiedLinkError error){
			
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if( CONFIG.USE_JAIR )
		{
			jairContext = JAIRBridgeContext.getInstance();
			p2pContext = JAIRBridgeContext.getInstance();
		}
		else
		{
			jairContext = StubJAIRBridgeContext.getInstance(getApplicationContext());
			p2pContext = StubP2PContext.getInstance(this);
		}
		log("onCreate");
	}
	
	@Override
	public void onBackPressed() {
		jairContext.dispatchServiceEvent("onBackPressed");
		super.onBackPressed();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		jairContext.setCurrentActivity(this);
		log("onResume");
	}

	@Override
	protected void onRestart() 
	{
		super.onRestart();
		log("onRestart");
	}

	@Override
	protected void onStart() 
	{
		super.onRestart();
		log("onStart");
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		log("onStop");
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		log("onPause");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		log("onDestroy");
	}

	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		log("onLowMemory");
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		log("onTrimMemory");
	}
}