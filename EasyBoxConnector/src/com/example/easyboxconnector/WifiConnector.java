package com.example.easyboxconnector;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiConnector {
	public static final int WPA = 1;
	public static final int WEP = 2;

	// Verbindung mit gewählten AP aufbauen
	public static boolean connectToNetwork(WifiManager wifi, String mac,
			int iSecurityType, String key, String ssid) {

		Log.d("Connect", "Trying to Connect");
		Log.d("Connect", ssid);
		Log.d("Connect", mac);
		Log.d("Connect", key);
		WifiConfiguration tmpConfig;
		/*
		 * List <WifiConfiguration> listConfig = wifi.getConfiguredNetworks();
		 * 
		 * for (int i = 0; i<listConfig.size(); i++){ tmpConfig =
		 * listConfig.get(i); if (tmpConfig.BSSID.equalsIgnoreCase(mac)){ return
		 * wifi.enableNetwork(tmpConfig.networkId, true); } }
		 */

		tmpConfig = new WifiConfiguration();
		tmpConfig.BSSID = mac;
		tmpConfig.SSID = ssid;
		tmpConfig.SSID = "\"".concat(ssid).concat("\"");;
		
		tmpConfig.priority = 40;
		tmpConfig.hiddenSSID = false;
		// NUR WPA:
		tmpConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		tmpConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		tmpConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		tmpConfig.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.CCMP);
		tmpConfig.allowedPairwiseCiphers
				.set(WifiConfiguration.PairwiseCipher.TKIP);
		tmpConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		tmpConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		tmpConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		tmpConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

		tmpConfig.preSharedKey = "\"".concat(key).concat("\"");

		tmpConfig.status = WifiConfiguration.Status.ENABLED;

		int netId = wifi.addNetwork(tmpConfig);
		Log.d("Connect", "Netid:" + String.valueOf(netId));
		boolean sucess = wifi.enableNetwork(netId, true);
		Log.d("Connect", "Sucess:" + String.valueOf(sucess));
		return sucess;
		//return wifi.enableNetwork(netId, true);
	}

}
