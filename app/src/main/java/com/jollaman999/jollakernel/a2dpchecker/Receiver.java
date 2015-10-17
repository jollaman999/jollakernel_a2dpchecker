package com.jollaman999.jollakernel.a2dpchecker;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver  {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        SOVCSOTCSwitcher.check_permission();

        if (action.equals("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED")) {
            if (intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothA2dp.STATE_PLAYING)
                == BluetoothA2dp.STATE_PLAYING) {
                SOVCSOTCSwitcher.sovc_switcher(1);
                SOVCSOTCSwitcher.sotc_switcher(1);
            } else if (intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothA2dp.STATE_PLAYING)
                    == BluetoothA2dp.STATE_NOT_PLAYING) {
                SOVCSOTCSwitcher.sovc_switcher(0);
                SOVCSOTCSwitcher.sotc_switcher(0);
            }
        }
    }
}
