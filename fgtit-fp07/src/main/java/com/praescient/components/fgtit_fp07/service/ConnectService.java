package com.praescient.components.fgtit_fp07.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectService extends Service {

    public static final String TAG = "UsbConnect";
    public static Boolean mainThreadFlag = true;
    public static Boolean ioThreadFlag = true;
    ServerSocket serverSocket = null;
    final int SERVER_PORT = 15101;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "androidService--->onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "androidService--->Start()");
        mainThreadFlag = true;
        new Thread()
        {
            public void run()
            {
                doListen();
            };
        }.start();
        return START_NOT_STICKY;
    }

    private void doListen()
    {
        serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while (mainThreadFlag)
            {
                Socket socket = serverSocket.accept();
                new Thread(new ThreadReadWriterIOSocket(this, socket)).start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainThreadFlag = false;
        ioThreadFlag = false;
        try
        {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "androidService--->onDestroy()");
    }


}
