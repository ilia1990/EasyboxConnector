package com.example.easyboxconnector;

//import java.util.Comparator;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.SparseArray;

public class VulnResults implements Comparable<VulnResults>{
	private ScanResult res;
	private String key;	

	public VulnResults(ScanResult result) throws Exception {
		this.res = result;
		this.key = EasyBoxWPAGen.GENERATE_WPA_KEY(result.BSSID);		
	}
	public String getMac(){
		return this.res.BSSID;
	}
	public String getSSID(){
		return this.res.SSID;
	}
	public String getKey(){
		return this.key;
	}	
	public int getLevel(){
		return this.res.level;
	}
	public String getChannel(){
		// Frequenz in Channel umrechnen:
		// SparseArray da er bei Hashmap gemeckert hat:
		SparseArray<String> hm = new SparseArray<String>();
		hm.put(2412,"1");
		hm.put(2417,"2");
		hm.put(2422,"3");
		hm.put(2427,"4");
		hm.put(2432,"5");
		hm.put(2437,"6");
		hm.put(2442,"7");
		hm.put(2447,"8");
		hm.put(2452,"9");
		hm.put(2457,"10");
		hm.put(2462,"11");
		hm.put(2467,"12");
		hm.put(2472,"13");
		return hm.get(this.res.frequency,"Unknown Channel");		
	}


	public int compareTo(VulnResults another) {
		return WifiManager.compareSignalLevel(this.getLevel(),another.getLevel());
	}
	public String toString(){
		return this.getSSID() + " Channel: "+this.getChannel();
	}
	
}
