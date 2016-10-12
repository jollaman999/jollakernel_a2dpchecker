package com.jollaman999.jollakernel.a2dpchecker;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Receiver extends BroadcastReceiver  {

    int PLAYING_CHECK_DELAY = 5000;

    private static boolean is_playing = false;
    private final SOVC_TEMP_Handler mSOVC_TEMP_Handler = new SOVC_TEMP_Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED")) {
            if (intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothA2dp.STATE_PLAYING)
                == BluetoothA2dp.STATE_PLAYING) {
                is_playing = true;
                SOVC_TEMP_Switcher.sovc_temp_switcher(1);
                Log.i("jolla-kernelA2DPChecker", "SOVC Enabled. Reason: STATE_PLAYING");
            } else if (intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothA2dp.STATE_PLAYING)
                    == BluetoothA2dp.STATE_NOT_PLAYING) {
                is_playing = false;
                SOVC_TEMP_Scheduler(PLAYING_CHECK_DELAY);
            }
        } else if (action.equals("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")) {
            if (intent.getIntExtra(BluetoothProfile.EXTRA_STATE, BluetoothA2dp.STATE_CONNECTED)
                    == BluetoothA2dp.STATE_DISCONNECTED) {
                is_playing = false;
                SOVC_TEMP_Switcher.sovc_temp_switcher(0);
                Log.i("jolla-kernelA2DPChecker", "SOVC Temporary Disabled. Reason: STATE_DISCONNECTED");
            }
        }
    }

    public static class SOVC_TEMP_Handler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (is_playing) {
                return;
            }
            SOVC_TEMP_Switcher.sovc_temp_switcher(0);
            Log.i("jolla-kernelA2DPChecker", "SOVC Temporary Disabled. Reason: Timeout after STATE_NOT_PLAYING");
        }
    };

    public void SOVC_TEMP_Scheduler(int ms) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Message msg = mSOVC_TEMP_Handler.obtainMessage();
                mSOVC_TEMP_Handler.sendMessage(msg);
            }
        }, ms);
    }
}
