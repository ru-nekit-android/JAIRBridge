package ru.nekit.android.activity;

import ru.nekit.android.activity.base.SherlockJAIRContextActivity;
import ru.nekit.android.core.interfaces.IP2PEventReceivable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.adobe.fre.FREObject;

public class ActionActivity extends SherlockJAIRContextActivity implements OnClickListener, IP2PEventReceivable {

	private Button send;
	private WebView webView;
	private static int COUNT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(jairContext.getResourceId("layout.activity_action"));
		send = (Button)findViewById(jairContext.getResourceId("id.send"));
		send.setOnClickListener(this);
		webView = (WebView)findViewById(jairContext.getResourceId("id.webView"));
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new mWebViewClient());
		webView.loadUrl("http://www.dirty.ru"); 
		WebSettings ws = webView.getSettings();
		ws.setBuiltInZoomControls(true);
		ws.setDefaultTextEncodingName("utf-8");
		p2pContext.registerP2PStatuEventReceiver(this);
	}

	@Override
	public void onClick(View v)
	{
		COUNT++;
		jairContext.publishValue("image" + COUNT, webView);
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		p2pContext.unregisterP2PStatusEventReceiver(this);
	}

	private class mWebViewClient extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) 
		{
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onP2PStatusEventReceive(String name, FREObject[] args)
	{


	}
}