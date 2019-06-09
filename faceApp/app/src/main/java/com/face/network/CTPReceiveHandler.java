package com.face.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.appcommon.AppNetwork;

public class CTPReceiveHandler extends Handler {

    //准备接受回调的Activity，比如MainActivity，需要继承自接口IQueryReturnHandler
    private ITCPReceiveHandler parent = null;

    public CTPReceiveHandler(ITCPReceiveHandler parent)
    {
        super();
        this.parent = parent;
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what)
        {
            case AppNetwork.C_Rsp_Login:
                parent.OnResLogin((AppNetwork.RspLogin)msg.obj);
                break;
            case AppNetwork.C_Rtn_Login:
                parent.OnRtnLogin((AppNetwork.RtnLogin)msg.obj);
                break;

                default:
                    Log.d("CTPReceiveHandler",msg.what + " Not Supported");

        }
    }
}
