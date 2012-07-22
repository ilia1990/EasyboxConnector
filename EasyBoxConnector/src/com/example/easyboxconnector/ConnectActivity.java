package com.example.easyboxconnector;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ConnectActivity extends Activity implements OnClickListener {
	public VulnResults result;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connect);
		// Den Listener von den Buttons setzen:
		
		Button button;
		button = (Button) findViewById(R.id.ConnectButton);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.BackButton);
		button.setOnClickListener(this);
		// Wert holen:

		Bundle bundle = getIntent().getExtras();
		this.result = (VulnResults) bundle.getSerializable("result");
		// Die Listview füllen:

		String[] arr = new String[]{
				"SSID:"+result.getSSID(),
				"MAC:"+result.getMac(),
				"Channel:"+result.getChannel()+"("+String.valueOf(result.getfrequency())+" Mhz)",
				"Signal:"+String.format("   RSSI : %d / Level : %d/4", result.getRSSI(), result.getLevel()),
				"Key:"+result.getKey(),
				
				
				
				
				};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				arr);
		ListView listView = (ListView) findViewById(R.id.PropertyList);		
		listView.setAdapter(adapter);

		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_connect, menu);
		return true;
	}

	// Handler für Buttons:
	public void onClick(View view) {
		if (view.getId() == R.id.ConnectButton) {
			this.connect();
		}
		if (view.getId() == R.id.BackButton) {
			Intent intent = new Intent(ConnectActivity.this,MainActivity.class);
			this.startActivity(intent);
		}
	}

	public void connect() {
		Log.d("Connect", "Trying to Connect");
		boolean connection = WifiConnector.connectToNetwork((WifiManager) getSystemService(Context.WIFI_SERVICE),
				result.getMac(), result.getKey(), result.getSSID());
		if (connection) {
			Toast.makeText(this, "Connection sucessful!", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(this, "Connection failed!", Toast.LENGTH_LONG)
					.show();
		}
	}
}
