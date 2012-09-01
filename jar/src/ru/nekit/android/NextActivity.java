package ru.nekit.android;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class NextActivity extends SherlockFREContextActivity implements OnClickListener, IJAIREventReceivable {

	private Button call;
	private EditText editText;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(getResourceId("layout.activity_next"));
		call = (Button)findViewById(getResourceId("id.button"));
		call.setOnClickListener(this);
		editText = (EditText)findViewById(getResourceId("id.editText"));
		webView = (WebView)findViewById(getResourceId("id.webView"));
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new mWebViewClient());
		webView.loadUrl("http://www.dirty.ru"); 
		WebSettings ws = webView.getSettings();
		ws.setBuiltInZoomControls(true);
		ws.setDefaultTextEncodingName("utf-8");
		registerEventReserver(this);
	}

	@Override
	public void onClick(View v)
	{
		publishValue("editTextValue", editText.getText().toString());
		publishValue("webViewImage", webView);
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		unregisterEventReserver(this);
	}

	@Override
	public void onEventReceive(String name, FREObject[] args) 
	{
		try {
			dispatchStatusEvent("++", name + "::" + args[0].getAsString());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
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
}