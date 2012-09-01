package ru.nekit.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartUpActivity extends SherlockFREContextActivity implements OnClickListener {

	public Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(getResourceId("layout.activity_start_up"));
		next = (Button)findViewById(getResourceId("id.button"));
		next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(StartUpActivity.this, NextActivity.class);
		startActivity(intent);
	}
}
