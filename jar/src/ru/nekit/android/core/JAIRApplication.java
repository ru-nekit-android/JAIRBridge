package ru.nekit.android.core;

import java.util.ArrayList;
import java.util.List;

import ru.nekit.android.model.ClientProxy;
import android.app.Application;

public class JAIRApplication extends Application
{

	public List<ClientProxy> clientList;

	@Override
	public void onCreate() 
	{
		super.onCreate();
		clientList = new ArrayList<ClientProxy>();
	}
}