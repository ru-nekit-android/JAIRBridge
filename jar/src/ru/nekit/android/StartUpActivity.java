package ru.nekit.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.adobe.fre.FREObject;

public class StartUpActivity extends SherlockFREContextActivity implements OnClickListener, IP2PStatusEventReceivable {

	public Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(getResourceId("layout.activity_start_up"));
		next = (Button)findViewById(getResourceId("id.button"));
		next.setOnClickListener(this);
		registerP2PStatuEventReceiver(this);
	}

	@Override
	public void onClick(View v)
	{
		dispatchServiceEvent("CLICK");
		connectP2P("none");
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		unregisterP2PStatusEventReceiver(this);
	}

	@Override
	public void onP2PStatusEventReceive(String name, FREObject[] args)
	{
		if( name.equals(JAIRBridgeContext.CONNECT_P2P) )
		{
			Intent intent = new Intent(StartUpActivity.this, NextActivity.class);
			startActivity(intent);
		}

	}
}
