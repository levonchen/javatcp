package com.face.network;

import org.appcommon.AppNetwork;

public interface ITCPReceiveHandler {

    //public void OnResLogin(AppNetwork.RspLogin field);

    //public  void OnRtnLogin(AppNetwork.RtnLogin field);

    public void OnReceived(AppNetwork.FaceObjBase field);
}
