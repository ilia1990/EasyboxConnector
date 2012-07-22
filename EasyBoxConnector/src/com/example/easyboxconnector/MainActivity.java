package com.example.easyboxconnector;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private WifiScanner receiver;
	private WifiManager wifi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button buttonScan = (Button) findViewById(R.id.ScanButton);
		buttonScan.setOnClickListener(this);
		// Wifi = Wifimanager:
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		// Start Wifi:
		if (!wifi.isWifiEnabled()) {
			wifi.setWifiEnabled(true);
		}

		// Receiver setzen::
		receiver = new WifiScanner(wifi);
		ArrayAdapter<VulnResults> adapter = new ArrayAdapter<VulnResults>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				receiver.results);
		receiver.setAdapter(adapter);
		registerReceiver(receiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

		// Assign adapter to ListView
		ListView listView = (ListView) findViewById(R.id.ConnectionList);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		//

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Handler für Buttons:
	public void onClick(View view) {
		if (view.getId() == R.id.ScanButton) {
			this.startscan();
		}
	}
	public void startscan(){
		Log.d("Wifi", "Scan started");
		Toast.makeText(this, "Started Scanning", Toast.LENGTH_LONG).show();
		wifi.startScan();
	}
	// Handler für die Listview
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Object Getten:		
		ListView lv = (ListView) findViewById(R.id.ConnectionList);
		VulnResults result = (VulnResults) lv.getAdapter().getItem(position);
		// Intent erstellen,mit Object füllen:
		Intent intent = new Intent(MainActivity.this,ConnectActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("result", result);
		intent.putExtras(bundle);
		// Activity starten:
		this.startActivity(intent);

		

				
				
		
	

	}
}
