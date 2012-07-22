package com.example.easyboxconnector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ArrayAdapter;

public class WifiScanner extends BroadcastReceiver {
	private static final String TAG = "wifiscanner";
	
	private WifiManager wifi;
	public ArrayList<VulnResults> results = new ArrayList<VulnResults>();

	private ArrayAdapter<VulnResults> adapter;


	public WifiScanner(WifiManager systemService) {
		super();
		this.wifi = systemService;
	}

	@Override
	public void onReceive(Context c, Intent intent) {
		results.clear();
		List<ScanResult> scans;
		try {
			// Access to the currently configured networks stored on your
			// device.
			scans = wifi.getScanResults();
			// remembered = wifi.getConfiguredNetworks();

			int idx = 0;

			// Copies the List of SSID names to a new Array called ArrSSID
			while (scans.size() > idx) {
				String ssid = scans.get(idx).SSID;
				if (Pattern.matches("Arcor-.{6}", ssid)
						|| Pattern.matches("Vodafone-.{6}", ssid)
						|| Pattern.matches("EasyBox-.{6}", ssid)) {
					Log.v(TAG, "Network Found:" + ssid);
					//Log.v("WifiScanner",
						//	String.valueOf(scans.get(idx).frequency));
					this.results.add(new VulnResults(scans.get(idx)));
					
				}
				idx++;
			}

		} catch (Exception e) {
			// result =
			Log.v(TAG, e.toString());
		}
		// Ergebnisse sind da,ordnen:
		Collections.sort(this.results);
		// Adapter benachrichtigen:
		adapter.notifyDataSetChanged();
		
		

	}

	public void setAdapter(ArrayAdapter<VulnResults> ada) {
		this.adapter= ada;		
	}



}