package com.example.xinliu.forhelp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class HelpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.i("Receive", "收到求救广播");
        Log.i("Receive", "广播类型为" + intent.getAction());
        Toast.makeText(context, "有人求救", Toast.LENGTH_SHORT).show();
    }
}
