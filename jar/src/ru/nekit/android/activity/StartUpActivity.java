package ru.nekit.android.activity;

import ru.nekit.android.activity.base.SherlockJAIRContextActivity;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.adobe.fre.FREObject;

public class StartUpActivity extends SherlockJAIRContextActivity implements OnClickListener, IP2PEventReceivable {

	private Button connect;
	private EditText suffix;
	//private EditText name;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(jairContext.getResourceId("layout.activity_start_up"));
		connect = (Button)findViewById(jairContext.getResourceId("id.connect"));
		connect.setOnClickListener(this);
		suffix = (EditText)findViewById(jairContext.getResourceId("id.suffix"));
		//name = (EditText)findViewById(getResourceId("id.name"));
		suffix.setHint("none");
		//name.setHint("mobile");
		p2pContext.registerP2PStatuEventReceiver(this);
		getSupportActionBar().setTitle("JAIR: Step 1");
		getSupportActionBar().setSubtitle("Enter invite key for create p2p connction");
	}

	@Override
	public void onClick(View v)
	{
		connect.setEnabled(false);
		connect.setText("Connection...");
		p2pContext.connect(suffix.getText().toString());
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