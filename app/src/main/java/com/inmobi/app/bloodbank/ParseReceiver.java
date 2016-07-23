package com.inmobi.app.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseAnalytics;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by deepak.jha on 22/08/15.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {
    @Override
    public void onPushOpen(Context context, Intent intent) {

        //To track "App Opens"
        ParseAnalytics.trackAppOpenedInBackground(intent);

        //Here is data you sent
        String tag="";
        Log.i(tag, intent.getExtras().getString("com.parse.Data"));
        System.out.println("Tag ios " + intent.getExtras().getString("com.parse.Data"));
        System.out.println("Tag is "+tag);
        Intent i = new Intent(context, Notification.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
