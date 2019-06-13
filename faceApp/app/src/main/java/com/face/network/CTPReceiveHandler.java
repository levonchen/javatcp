package com.face.network;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.appcommon.AppNetwork;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CTPReceiveHandler extends Handler {


    public CTPReceiveHandler()
    {
        super();
    }

    private static CTPReceiveHandler s=null;

    public static synchronized CTPReceiveHandler getInstance()
    {
        if(s==null)
            s= new CTPReceiveHandler();
        return s;
    }

    //假设每一个 activity，同一个事件只能监听一次
    private HashMap<Integer,HashMap<Activity,ITCPReceiveHandler>> listeners = new HashMap<Integer,HashMap<Activity,ITCPReceiveHandler>>();

    public void AddListener(Activity act,int command,ITCPReceiveHandler listener)
    {
        HashMap<Activity,ITCPReceiveHandler> value = listeners.get(command);
        if(value == null)
        {
            value = new HashMap<Activity, ITCPReceiveHandler>();
            value.put(act,listener);

            listeners.put(command,value);
        }else {
            value.put(act,listener);
        }
    }

    public void RemoveListener(Activity act,int command)
    {
        HashMap<Activity,ITCPReceiveHandler> value = listeners.get(command);
        if(value == null)
        {
            return;
        }

        value.remove(act);
    }

    @Override
    public void handleMessage(Message msg)
    {
        HashMap<Activity,ITCPReceiveHandler> value = listeners.get(msg.what);
        if(value == null)
        {
            Log.d("CTPReceiveHandler",msg.what + " Not Supported");
            return;
        }

        //便利通知监听者
        Iterator<Map.Entry<Activity,ITCPReceiveHandler>> iterator = value.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity,ITCPReceiveHandler> entry = iterator.next();
            ITCPReceiveHandler listener= entry.getValue();
            listener.OnReceived((AppNetwork.FaceObjBase)msg.obj);
        }
    }
}
