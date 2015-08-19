package com.jiangnan.unloc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;

import android.widget.Toast;

import com.google.gson.Gson;
import com.jiangnan.Util.WiFiUtil;



import java.util.HashMap;
import java.util.List;

/**
 * Created by nezha on 2015/8/13.
 */
public class BroadcastWiFi extends BroadcastReceiver {

    // 扫描结果列表
    private List<ScanResult> list;
    private ScanResult mScanResult;
    private WiFiUtil mWifiAdmin;
    private HashMap<String,Integer> wifiMap = new HashMap<>();

    private WiFiInterface wifiInterface;


    public BroadcastWiFi(WiFiUtil mWifiAdmin){
        this.mWifiAdmin = mWifiAdmin;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //get wifi info,and send it to server to get returns to update location
        Toast.makeText(context,"Receiver start get wifi information",Toast.LENGTH_LONG).show();
        String wifiLog = getAllNetWorkList();
        wifiInterface.setWiFiInfo(wifiLog);
    }

    public String getAllNetWorkList() {
        // 每次点击扫描之前清空上一次的扫描结果
        if (wifiMap != null) {
            wifiMap = new HashMap<>();
        }
        //开始扫描网络
        mWifiAdmin.startScan();
        list = mWifiAdmin.getWifiList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                //得到扫描结果
                mScanResult = list.get(i);
                wifiMap.put(mScanResult.BSSID,mScanResult.level);
            }
        }
        Gson gson = new Gson();
        return gson.toJson(wifiMap);
    }


    public interface WiFiInterface{
        void setWiFiInfo(String content);
    }

    public void setWiFiInterfaceListener(WiFiInterface wifiInterface){
        this.wifiInterface = wifiInterface;
    }
}
