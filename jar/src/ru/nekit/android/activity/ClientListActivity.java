package ru.nekit.android.activity;

import java.util.List;

import ru.nekit.android.ClientListAdapter;
import ru.nekit.android.activity.base.SherlockJAIRContextListActivity;
import ru.nekit.android.core.JAIRApplication;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import ru.nekit.android.model.ClientProxy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.adobe.fre.FREObject;

public class ClientListActivity extends SherlockJAIRContextListActivity implements IP2PEventReceivable 
{

	private List<ClientProxy> clientList;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(jairContext.getResourceId("layout.activity_client_list"));
		p2pContext.registerP2PStatuEventReceiver(this);
		clientList = ((JAIRApplication)getApplication()).clientList;
		setListAdapter(new ClientListAdapter(this, clientList));
		getSupportActionBar().setTitle("JAIR: Step 2");
		getSupportActionBar().setSubtitle("Choose p2p client");
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) 
	{
		ClientProxy client = clientList.get(position);
		Intent intent = new Intent(ClientListActivity.this, ActionActivity.class);
		p2pContext.setCurrentP2PClient(client);
		intent.putExtra(ClientProxy.CURRENT, client);
		startActivity(intent);
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
		if( name.equals(JAIRBridgeContext.CONNECT_CLIENT))
		{
			ClientProxy client = new ClientProxy(args[0]);
			clientList.add(client);
			((ClientListAdapter)getListAdapter()).notifyDataSetChanged();
		}
	}
}