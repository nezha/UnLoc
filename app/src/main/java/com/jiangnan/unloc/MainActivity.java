package com.jiangnan.unloc;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;



public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    private TextView UserLoc,WiFiInfo;
    public Handler handler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        IntentFilter wifiFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        BroadcastWiFi wifiReceive = new BroadcastWiFi();
        registerReceiver(wifiReceive,wifiFilter);
    }
    public void init(){
        UserLoc = (TextView) findViewById(R.id.UserLoc);
        WiFiInfo = (TextView) findViewById(R.id.WiFiInfo);
    }
}
