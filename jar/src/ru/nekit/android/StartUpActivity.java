package ru.nekit.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.adobe.fre.FREObject;

public class StartUpActivity extends SherlockJAIRContextActivity implements OnClickListener, IP2PStatusEventReceivable {

	private Button connect;
	private EditText suffix;
	private EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(getResourceId("layout.activity_start_up"));
		connect = (Button)findViewById(getResourceId("id.connect"));
		connect.setOnClickListener(this);
		suffix = (EditText)findViewById(getResourceId("id.suffix"));
		name = (EditText)findViewById(getResourceId("id.name"));
		suffix.setHint("none");
		name.setHint("mobile");
		registerP2PStatuEventReceiver(this);
	}

	@Override
	public void onClick(View v)
	{
		connectP2P(suffix.getText().toString());
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
			Intent intent = new Intent(StartUpActivity.this, UserListActivity.class);
			startActivity(intent);
		}
	}
}