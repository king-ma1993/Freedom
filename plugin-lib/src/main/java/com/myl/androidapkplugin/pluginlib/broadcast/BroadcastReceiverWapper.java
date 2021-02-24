package com.myl.androidapkplugin.pluginlib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.myl.androidapkplugin.pluginlib.plugin.PluginContext;

public class BroadcastReceiverWapper extends BroadcastReceiver {

    final private BroadcastReceiver mRealBroadcastReceiver;

    final private PluginContext pluginContext;

    public BroadcastReceiverWapper(BroadcastReceiver broadcastReceiver, PluginContext pluginContext) {
        mRealBroadcastReceiver = broadcastReceiver;
        this.pluginContext = pluginContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mRealBroadcastReceiver.onReceive(pluginContext, intent);
    }
}
