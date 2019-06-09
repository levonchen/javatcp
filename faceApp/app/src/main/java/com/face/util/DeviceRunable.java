package com.face.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class DeviceRunable  extends Thread{

    private static String SP_DEVICES_ID = "deviceId";
    private Activity parentActivity;

    public DeviceRunable(Activity parent) {
        parentActivity = parent;
    }
    @Override
    public void run()
    {
        try {
            //获取保存在sd中的 设备唯一标识符
            String readDeviceID = DeviceHelper.readDeviceID(parentActivity);
            //获取缓存在  sharepreference 里面的 设备唯一标识
            SharedPreferences devicePreference = parentActivity.getSharedPreferences("deviceing",Context.MODE_PRIVATE);
            String string = devicePreference.getString(SP_DEVICES_ID, readDeviceID);
            //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
            if (string != null) {
                //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
                if (StringUtils.isBlank(readDeviceID) && !string.equals(readDeviceID)) {
                    // 取有效地 app缓存 进行更新操作
                    if (StringUtils.isBlank(readDeviceID) && !StringUtils.isBlank(string)) {
                        readDeviceID = string;
                        DeviceHelper.saveDeviceID(readDeviceID, parentActivity);
                    }
                }
            }
            // app 没有缓存 (这种情况只会发生在第一次启动的时候)
            if (StringUtils.isBlank(readDeviceID)) {
                //保存设备id
                readDeviceID = DeviceHelper.getDeviceId(parentActivity);
            }
            //左后再次更新app 的缓存
            PreferencesUtils.putString(parentActivity,SP_DEVICES_ID,readDeviceID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
