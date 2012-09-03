package ru.nekit.android;

import android.os.Bundle;

import com.adobe.fre.FREObject;

public class UserListActivity extends SherlockJAIRContextListActivity implements IP2PStatusEventReceivable {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(getResourceId("layout.activity_user_list"));
		registerP2PStatuEventReceiver(this);
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

	}
}