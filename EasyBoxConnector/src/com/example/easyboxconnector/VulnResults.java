package com.example.easyboxconnector;

//import java.util.Comparator;

import java.io.Serializable;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.SparseArray;

public class VulnResults implements Comparable<VulnResults>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4491525094105670148L;
	//private ScanResult res;
	private String key;	
	private String mac;
	private String ssid;
	private int level;
	private int rssi;
	private int frequency;
	

	public VulnResults(ScanResult result) throws Exception {
		this.mac = result.BSSID;
		this.ssid = result.SSID;
		this.rssi = result.level;
		this.level = WifiManager.calculateSignalLevel(this.rssi, 5);
		this.frequency = result.frequency;
		this.key = EasyBoxWPAGen.GENERATE_WPA_KEY(result.BSSID);	
		
	}
	public String getMac(){
		return this.mac;
	}
	public String getSSID(){
		return this.ssid;
	}
	public String getKey(){
		return this.key;		
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
		return hm.get(this.frequency,"Unknown Channel");		
	}


	public int compareTo(VulnResults another) {
		return WifiManager.compareSignalLevel(this.getRSSI(),another.getRSSI());
	}
	public String toString(){
		return this.getSSID() + " Ch: "+this.getChannel()+" Key:"+this.getKey();
	}
	public int getfrequency() {
		return this.frequency;
	}
	public int getRSSI(){
		return this.rssi;
		
	}
	public int getLevel() {
		return this.level;
		
	}
	
	
}
