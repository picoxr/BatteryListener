package com.picovr.batterylistener;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.picovr.libs.BatteryListener;

/**
 * @author Admin
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "====TAG====";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BatteryListener listener = new BatteryListener(this, "", "");
        listener.initListener(new BatteryListener.BatteryStateListener() {
            @Override
            public void onStateChanged(boolean isCharging) {
                Log.e(TAG, "BATTERY_CHARGING:  " + isCharging);
            }

            @Override
            public void onStateLow() {
                Log.e(TAG, "BATTERY_LOW");
            }

            @Override
            public void onStateOkay() {
                Log.e(TAG, "BATTERY_OKAY");
            }

            @Override
            public void onStatePowerConnected() {
                Log.e(TAG, "POWER_CONNECTED");
            }

            @Override
            public void onStatePowerDisconnected() {
                Log.e(TAG, "POWER_DISCONNECTED");
            }
        });

        Log.e(TAG, "BatteryPercent:  " + listener.getBatteryPercent());
    }
}