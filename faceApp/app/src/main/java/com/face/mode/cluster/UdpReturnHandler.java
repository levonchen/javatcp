package com.face.mode.cluster;

import android.os.Handler;
import android.os.Message;

import com.face.faceobject.FaceInfo;

public class UdpReturnHandler extends Handler {

    public static final int Rtn_Query_Result = 1;

    //准备接受回调的Activity，比如MainActivity，需要继承自接口IQueryReturnHandler
    private IQueryReturnHandler parent = null;

    public UdpReturnHandler(IQueryReturnHandler parent)
    {
        super();
        this.parent = parent;
    }

    @Override
    public void handleMessage(Message msg)
    {
        if(msg.what == Rtn_Query_Result){
            parent.OnRtnQueryResult((FaceInfo)msg.obj);
        }else {
            super.handleMessage(msg);
        }
    }
}
