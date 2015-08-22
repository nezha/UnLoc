package com.jiangnan.unloc;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangnan.Util.WiFiUtil;
import com.jiangnan.http.WiFiHttpThread;



public class MainActivity extends Activity implements BroadcastWiFi.WiFiInterface{
    /** Called when the activity is first created. */
    private TextView UserLoc,WiFiInfo;
    private WiFiUtil mWifiAdmin;
    private WiFiHttpThread wifiHttpThread = new WiFiHttpThread(null,null);

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x4000) {
                String location = msg.getData().getString("Location");
                UserLoc.setText(location + System.currentTimeMillis());
            }

        }
    };

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
        //因为要等到wifibroadcast接收到数据才能实例化wifiHttpThread
        //wifiHttpThread.setUpdateLocListener(this);
    }



    public void init(){
        UserLoc = (TextView) findViewById(R.id.UserLoc);
        WiFiInfo = (TextView) findViewById(R.id.WiFiInfo);
    }


    //回调函数，获取到wifi信号数据
    @Override
    public void setWiFiInfo(String content) {
        if (content != null){
            Toast.makeText(this,"the wifi info is:"+content,Toast.LENGTH_LONG).show();
            WiFiInfo.setText(content);
            //handler = new Handler();
            //一旦获得就采取socket通信
            new WiFiHttpThread(content,handler).start();

            Toast.makeText(this, "start socket", Toast.LENGTH_LONG).show();
        }
    }


    /*
    //回调函数，获取到位置信息
    // the string of content is like:{'LocX':123.0,'LocY':123.0,'floor':2}
    @Override
    public void updateLoc(String content) {
        if (content != null){
            UserLoc.setText(content+System.currentTimeMillis());
        }
    }
    */
}
