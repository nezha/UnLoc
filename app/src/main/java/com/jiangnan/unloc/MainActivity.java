package com.jiangnan.unloc;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.jiangnan.LocInstance.Position;
import com.jiangnan.Util.WiFiUtil;
import com.jiangnan.http.WiFiHttpThread;


public class MainActivity extends Activity implements BroadcastWiFi.WiFiInterface,WiFiHttpThread.UpdateLoc{
    /** Called when the activity is first created. */
    private TextView UserLoc,WiFiInfo;
    private WiFiUtil mWifiAdmin;
    private WiFiHttpThread wifiHttpThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mWifiAdmin = new WiFiUtil(this);
        mWifiAdmin.openWifi();
        IntentFilter wifiFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        BroadcastWiFi wifiReceive = new BroadcastWiFi(mWifiAdmin);
        registerReceiver(wifiReceive, wifiFilter);
        wifiReceive.setWiFiInterfaceListener(this);
        wifiHttpThread.setUpdateLocListener(this);
    }



    public void init(){
        UserLoc = (TextView) findViewById(R.id.UserLoc);
        WiFiInfo = (TextView) findViewById(R.id.WiFiInfo);
    }


    //回调函数，获取到wifi信号数据
    @Override
    public void setWiFiInfo(String content) {
        if (content != null){
            WiFiInfo.setText(content);
            //一旦获得就采取socket通信
            wifiHttpThread = new WiFiHttpThread(content);
            wifiHttpThread.start();
        }
    }

    //回调函数，获取到位置信息
    @Override
    public void updateLoc(String content) {
        if (content != null){
            UserLoc.setText(content);
        }
    }
}
