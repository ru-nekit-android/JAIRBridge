package ru.nekit.android.activity.base;

import ru.nekit.android.CONFIG;
import ru.nekit.android.core.JAIRBridgeContext;
import ru.nekit.android.core.StubJAIRBridgeContext;
import ru.nekit.android.core.StubP2PContext;
import ru.nekit.android.core.interfaces.IJAIR;
import ru.nekit.android.core.interfaces.IP2P;

import com.actionbarsherlock.app.SherlockActivity;

public class SherlockJAIRContextActivity extends SherlockActivity {

	protected IJAIR jairContext;
	protected IP2P p2pContext;

	public SherlockJAIRContextActivity()
	{
		super();
		if( CONFIG.USE_JAIR )
		{
			jairContext = JAIRBridgeContext.getInstance();
			p2pContext = JAIRBridgeContext.getInstance();
		}
		else
		{
			jairContext = new StubJAIRBridgeContext(this);
			p2pContext = new StubP2PContext(this);
		}
	}
}