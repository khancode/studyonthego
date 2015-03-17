package com.studyonthegoapp.activity;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	private EditText usernameET;
	private EditText passwordET;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		usernameET = (EditText) findViewById(id.usernameEditText);
		passwordET = (EditText) findViewById(id.passwordEditText);
		loginButton = (Button) findViewById(id.loginButton);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void verifyLogin(View view)
	{
		String username = usernameET.getText().toString();
		String password = passwordET.getText().toString();
		
		Intent intent = new Intent(this, AppCoreActivity.class);
		intent.putExtra("username", username);
		startActivity(intent);		
	}
}
