package com.example.dodobal_1.recoderapp;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Dodobal-2 on 12/23/2015.
 */
public class DeviceAdminDemo extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        super.onReceive(context, intent);
       // Toast.makeText(context, "onReceive",Toast.LENGTH_LONG).show();
    }

    public void onEnabled(Context context, Intent intent){

    };

    public void onDisabled(Context context, Intent intent){

    };
}
