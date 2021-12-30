package com.picovr.libs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.unity3d.player.UnityPlayer;

/**
 * @author Admin
 */
public class BatteryListener {

    private final BatteryBroadcastReceiver receiver;
    private final Context mContext;
    private String mObjName = "BatteryListener";
    private String mCallBack = "BatteryCallBack";
    private BatteryStateListener mBatteryStateListener;

    public BatteryListener(Context context, String objName, String callBackName) {
        mContext = context;
        mObjName = objName;
        mCallBack = callBackName;
        receiver = new BatteryBroadcastReceiver();
        register();
    }

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.registerReceiver(receiver, filter);
    }

    public void initListener(BatteryStateListener listener) {
        mBatteryStateListener = listener;
    }

    public void unregister() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
    }

    public float getBatteryPercent() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mContext.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return level * 100 / (float) scale;
    }

    public boolean isCharging() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mContext.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }

    public interface BatteryStateListener {
        void onStateChanged(boolean isCharging);

        void onStateLow();

        void onStateOkay();

        void onStatePowerConnected();

        void onStatePowerDisconnected();
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getAction()) {
                    case Intent.ACTION_BATTERY_CHANGED:
                        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
                        UnityPlayer.UnitySendMessage(mObjName, mCallBack, isCharging ? "BATTERY_CHARGING" : "BATTERY_NOT_CHARGING");
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateChanged(isCharging);
                        }
                        break;
                    case Intent.ACTION_BATTERY_LOW:
                        UnityPlayer.UnitySendMessage(mObjName, mCallBack, "BATTERY_LOW");
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateLow();
                        }
                        break;
                    case Intent.ACTION_BATTERY_OKAY:
                        UnityPlayer.UnitySendMessage(mObjName, mCallBack, "BATTERY_OKAY");
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateOkay();
                        }
                        break;
                    case Intent.ACTION_POWER_CONNECTED:
                        UnityPlayer.UnitySendMessage(mObjName, mCallBack, "POWER_CONNECTED");
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStatePowerConnected();
                        }
                        break;
                    case Intent.ACTION_POWER_DISCONNECTED:
                        UnityPlayer.UnitySendMessage(mObjName, mCallBack, "POWER_DISCONNECTED");
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStatePowerDisconnected();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
