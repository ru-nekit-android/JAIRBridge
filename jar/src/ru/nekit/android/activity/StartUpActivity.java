package ru.nekit.android.activity;

import ru.nekit.android.activity.base.SherlockJAIRContextActivity;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.adobe.fre.FREObject;

public class StartUpActivity extends SherlockJAIRContextActivity implements OnClickListener, IP2PEventReceivable {

	private Button connect;
	private Button destroy;
	private EditText suffix;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		try{
			jairContext.check();
		}catch ( UnsatisfiedLinkError error)
		{
			Log.d("ru.nekit.jair", "jair::FATAL ERROR - to be or not to be");
			//jairContext.restartBridge();

			String appEntryClassName = PreferenceManager.getDefaultSharedPreferences(this).getString(JAIRBridgeContext.AECN, "");
			//String appEntryClassName = PreferenceManager.getDefaultSharedPreferences(this).getString(JAIRBridgeContext.AECN, "");
			Log.d("ru.nekit.jair", "jair::restart_start " + appEntryClassName);
			if( appEntryClassName!= null )
			{
				Log.d("ru.nekit.jair", "restart");
				//appEntry.finish();
				Log.d("ru.nekit.jair", "restart");
				//System.gc();
				Log.d("ru.nekit.jair", "restart");
				Intent intent = new Intent();
				intent.setClassName(this, appEntryClassName);
				Log.d("ru.nekit.jair", "restart");
				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Log.d("ru.nekit.jair", "restart");
				this.startActivity(intent);
				Log.d("ru.nekit.jair", "restart");
				//dispatchServiceEvent(RESTART);
			}
			finish();
			return;
		}
		setContentView(jairContext.getResourceId("layout.activity_start_up"));
		connect = (Button)findViewById(jairContext.getResourceId("id.connect"));
		connect.setOnClickListener(this);
		destroy = (Button)findViewById(jairContext.getResourceId("id.destroy"));
		destroy.setOnClickListener(this);
		suffix = (EditText)findViewById(jairContext.getResourceId("id.suffix"));
		suffix.setHint("none");
		p2pContext.registerP2PStatuEventReceiver(this);
		getSupportActionBar().setTitle("JAIR: Step 1");
		getSupportActionBar().setSubtitle("Enter invite key for create p2p connction");
	}

	@Override
	public void onClick(View v)
	{
		if( v.getId() == jairContext.getResourceId("id.connect"))
		{
			connect.setEnabled(false);
			connect.setText("Connection...");
			p2pContext.connect(suffix.getText().toString());
		}else if( v.getId() == jairContext.getResourceId("id.destroy") )
		{
			//jairContext.destroyBridge();

			try{
				throw new UnsatisfiedLinkError();
			}catch ( UnsatisfiedLinkError error)
			{
				Log.d("ru.nekit.jair", "jair::FATAL ERROR - to be or not to be");
				jairContext.restartBridge();
				finish();
				return;
			}
		}
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		p2pContext.unregisterP2PStatusEventReceiver(this);
	}

	@Override
	public void onP2PStatusEventReceive(String name, FREObject[] args)
	{
		if( name.equals(JAIRBridgeContext.CONNECT_TO_GROUP) )
		{
			Intent intent = new Intent(StartUpActivity.this, ClientListActivity.class);
			startActivity(intent);
		}
	}
}