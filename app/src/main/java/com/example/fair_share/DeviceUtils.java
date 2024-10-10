package com.example.fair_share;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtils {
    public static String getDeviceUUID(Context context) {
        if (context != null) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            throw new IllegalArgumentException("Context cannot be null");
        }
    }
}
