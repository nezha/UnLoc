package com.jiangnan.http;


import android.widget.Toast;

import com.jiangnan.LocInstance.Position;
import com.jiangnan.unloc.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nezha on 2015/8/13.
 */
public class WiFiHttpThread extends Thread{

    private String WiFiInfo;
    private static final int port = 4000;
    private static final String IP_ADDR = "127.0.0.1";

    /**
     *
     * @param WiFiInfo the msg which will post to server ,like this : {'74:25:8a:47:36:90': -44.3125, '74:25:8a:47:38:60': -93.5}
     */
    public WiFiHttpThread(String WiFiInfo){
        this.WiFiInfo = WiFiInfo;
    }


    /**
     * @description this this a method to communicate with server,and get the return position msg,and update the instance of Position
     *
     */
    private void PostMsg(){
        Position position = Position.getPosition(); //get instance object
        try {
            Socket socket = new Socket(IP_ADDR,port);
            //get write sream
            OutputStream  os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            //get reag stream
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            pw.write(WiFiInfo);
            pw.flush();
            socket.shutdownOutput();

            //get msg from server
            String RS = null;
            String reply = null;
            while ((reply=br.readLine())!=null){
                RS = RS + reply;
            }
            System.out.print(RS);
            //Toast.makeText(,RS,Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        PostMsg();
    }
}
