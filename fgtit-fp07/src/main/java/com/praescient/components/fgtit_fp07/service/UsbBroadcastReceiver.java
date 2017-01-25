package com.praescient.components.fgtit_fp07.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class UsbBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "Broadcast";
    private static String START_ACTION = "NotifyUsbStartNet";
    private static String STOP_ACTION = "NotifyUsbStopNet";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "UsbBroadcastReceiver" + "---->"+ "UsbBroadcastReceiver onReceive");

        String action = intent.getAction();
        if (START_ACTION.equalsIgnoreCase(action)) {
            context.startService(new Intent(context, ConnectService.class));
            Log.d(TAG, "UsbBroadcastReceiver" + "---->"+ "UsbBroadcastReceiver onReceive start end");

            Toast.makeText(context, "USB Start", Toast.LENGTH_SHORT).show();

        } else if (STOP_ACTION.equalsIgnoreCase(action)) {
            context.stopService(new Intent(context, ConnectService.class));
            Log.d(TAG, "UsbBroadcastReceiver" + "---->"+ "UsbBroadcastReceiver onReceive stop end");

            Toast.makeText(context, "USB Stop", Toast.LENGTH_SHORT).show();
        }
    }
}
