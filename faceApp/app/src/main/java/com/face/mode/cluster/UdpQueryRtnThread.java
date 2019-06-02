package com.face.mode.cluster;


import android.os.Message;
import android.util.Log;

import com.face.faceobject.FaceInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpQueryRtnThread extends Thread {

    private UdpReturnHandler Handler = null;
    private boolean running = true;

    private DatagramSocket socket;

    public UdpQueryRtnThread(UdpReturnHandler handler)
    {
        super();
        Log.d("UdpQueryRtnThread","init");
        this.Handler = handler;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    private void sendQueryReturn(FaceInfo obj)
    {
        Log.d("UdpQueryRtnThread","Rtn broadcast..");
        Handler.sendMessage(Message.obtain(Handler,UdpReturnHandler.Rtn_Query_Result,obj));
    }

    @Override
    public void run()
    {
        try{
            socket = new DatagramSocket(UdpCommonSetting.ReceiveRtnPort);

            byte[] buf = new byte[UdpFaceRtnData.TotalLength];
            while (running){

                Log.d("UdpQueryRtnThread","waiting...");

                DatagramPacket msgPackage = new DatagramPacket(buf,buf.length);
                socket.receive(msgPackage);


                Log.d("UdpQueryRtnThread","gotton");

                UdpFaceRtnData dtObj = new UdpFaceRtnData();
                String errorInfo = dtObj.FromBytes(buf);
                if(errorInfo.length() != 0)
                {
                    Log.d("Parse Rtn Error:",errorInfo);
                }else
                {
                    FaceInfo faceObj = new FaceInfo();
                    faceObj.Name = dtObj.getName();
                    faceObj.CardId = dtObj.getCardId();

                    sendQueryReturn(faceObj);
                }
            }

        }catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }
    }

}
