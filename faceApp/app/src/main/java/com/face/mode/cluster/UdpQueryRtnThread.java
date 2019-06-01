package com.face.mode.cluster;


import android.os.Message;

import com.face.faceobject.FaceInfo;

public class UdpQueryRtnThread extends Thread {

    private String dstAddress;
    private int dstPort;
    private UdpReturnHandler Handler = null;
    private boolean running = false;

    public UdpQueryRtnThread(String addr, int port, UdpReturnHandler handler)
    {
        super();

        dstAddress = addr;
        dstPort = port;
        this.Handler = handler;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    private void sendQueryReturn(FaceInfo obj)
    {
        Handler.sendMessage(Message.obtain(Handler,UdpReturnHandler.Rtn_Query_Result,obj));
    }
}
