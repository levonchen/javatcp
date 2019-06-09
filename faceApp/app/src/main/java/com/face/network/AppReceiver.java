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
    private Map<Integer,ReceiveBase> commandToReceive = new HashMap<Integer, ReceiveBase>();
    private CTPReceiveHandler Handler;

    public AppReceiver(Client ct)
    {
        client = ct;
        Handler = CTPReceiveHandler.getInstance();
        registerCallback();
    }


    //处理face相关的回调
    public void onHandleReceived(Connection connection, AppNetwork.FaceObjBase faceObj){
        int cmd = faceObj.Command;
        ReceiveBase receive = commandToReceive.get(cmd);
        if(receive != null)
        {
            receive.onHandleReceived(connection, faceObj);
        }
    }

    private void registerCallback()
    {
        commandToReceive.put(AppNetwork.C_Rsp_Login,new RspLogin());
        commandToReceive.put(AppNetwork.C_Rtn_Login,new RtnLogin());
    }

    //推送消息到activity
    private void sendReceivedObject(AppNetwork.FaceObjBase obj)
    {
        Log.d("AppReceiver","Handler.. "  + obj.Command);
        Handler.sendMessage(Message.obtain(Handler,obj.Command,obj));
    }

    class ReceiveBase
    {
        public void onHandleReceived(Connection connection, AppNetwork.FaceObjBase faceObj){}
    }

    class RspLogin extends ReceiveBase
    {
        @Override
        public void onHandleReceived(Connection connection, AppNetwork.FaceObjBase faceObj)
        {
            if(faceObj instanceof AppNetwork.RspLogin) {
                AppNetwork.RspLogin field = (AppNetwork.RspLogin)faceObj;
                sendReceivedObject(field);
            }
            else
            {
                Log.e("AppReceiver.RspLogin","Not a rsplogin object");
            }
        }
    }

    class RtnLogin extends ReceiveBase
    {
        @Override
        public void onHandleReceived(Connection connection, AppNetwork.FaceObjBase faceObj)
        {
            if(faceObj instanceof AppNetwork.RtnLogin) {
                AppNetwork.RtnLogin field = (AppNetwork.RtnLogin)faceObj;
                sendReceivedObject(field);
            }
            else
            {
                Log.e("AppReceiver.RspLogin","Not a rtnlogin object");
            }
        }
    }
}
