package com.face.network;

import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.face.faceapp.MainActivity;

import org.appcommon.AppNetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppReceiver {

    private Client client;
    private CTPReceiveHandler Handler;

    public AppReceiver(Client ct)
    {
        client = ct;
        Handler = CTPReceiveHandler.getInstance();
    }


    //处理face相关的回调
    public void onHandleReceived(Connection connection, AppNetwork.FaceObjBase faceObj){
        int cmd = faceObj.Command;
        sendReceivedObject(faceObj);
    }



    //推送消息到activity
    private void sendReceivedObject(AppNetwork.FaceObjBase obj)
    {
        Log.d("AppReceiver","Handler.. "  + obj.Command);
        Handler.sendMessage(Message.obtain(Handler,obj.Command,obj));
    }


}
