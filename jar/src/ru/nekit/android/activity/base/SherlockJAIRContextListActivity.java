package ru.nekit.android.activity.base;

import ru.nekit.android.CONFIG;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.StubJAIRBridgeContext;
import ru.nekit.android.core.StubP2PContext;
import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IP2P;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockListActivity;

public class SherlockJAIRContextListActivity extends SherlockListActivity{

	protected IJAIR jairContext;
	protected IP2P p2pContext;

	public SherlockJAIRContextListActivity()
	{
		super();
		if( CONFIG.USE_JAIR )
		{
			jairContext = JAIRBridgeContext.getInstance();
			p2pContext = JAIRBridgeContext.getInstance();
		}
		else
		{
			jairContext = StubJAIRBridgeContext.getInstance(this);
			p2pContext = StubP2PContext.getInstance(this);
		}
	}

	protected void log(String msg)
	{
		msg = "jair::Activity " + getComponentName().getClassName() + " " + msg;
		Log.d("ru.nekit.jair", msg);
		jairContext.dispatchServiceEvent(msg);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		log("onCreate");
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