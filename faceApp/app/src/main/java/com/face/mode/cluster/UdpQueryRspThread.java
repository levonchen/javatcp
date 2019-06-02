package com.face.mode.cluster;

import android.net.wifi.WifiManager;
import android.util.Log;

import com.face.faceobject.FaceInfo;
import com.face.faceobject.FaceList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *   Waiting to receive query from broadcast
 *   If received query, then make some check
 *   If found some result, then send Rsp to specified
 *   query server to the Return Thread
 */
public class UdpQueryRspThread extends Thread {


    private int dstPort;
    private UdpReturnHandler Handler = null;
    private boolean running = true;

    private DatagramSocket socket;
    private InetAddress address;
    WifiManager wifiManager;
    private static WifiManager.MulticastLock lock;
    public UdpQueryRspThread(WifiManager wifimanager )
    {
        super();
        wifiManager = wifimanager;
        this.lock= wifimanager.createMulticastLock("UdpQueryRspThread");
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        try{

            Log.d("UdpQueryRspThread ","start run");
            socket = new DatagramSocket(UdpCommonSetting.ReceiveQueryPort);

            byte[] buf = new byte[UdpFaceReqData.length];
            while (running){

                Log.d("UdpQueryRspThread"," waiting receive ");


                this.lock.acquire();
                DatagramPacket msgPackage = new DatagramPacket(buf,buf.length);
                socket.receive(msgPackage);

                this.lock.release();

                Log.d("UdpQueryRspThread ",new String(buf));

                InetAddress rtnAddress = msgPackage.getAddress();
                UdpFaceReqData dtObj = new UdpFaceReqData();
                dtObj.setCharacteristic(buf);

                sendRtnToQueryTerminal(rtnAddress,dtObj);
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

    private void  sendRtnToQueryTerminal(InetAddress destAddress, UdpFaceReqData qryObj){

        try {

                Log.d("UdpQueryRspThread "," start response:" + destAddress.getHostAddress());
                byte[] datas = qryObj.getCharacteristic();
                String queryName = new String(datas);
                queryName = queryName.trim();
                FaceInfo findObj = FaceList.SearchInfo(queryName);
                if(findObj == null)
                {
                    Log.d("UdpQueryRspThread ","Not found" + queryName);
                    return;
                }

                DatagramSocket sendSocket = new DatagramSocket();

                UdpFaceRtnData rtnData = new UdpFaceRtnData();

                rtnData.setName(findObj.Name);
                rtnData.setCardId(findObj.CardId);

                //TODO: add picture

                byte[] buf = rtnData.ToBytes();

                 DatagramPacket sendPacket = new DatagramPacket(buf, UdpFaceRtnData.TotalLength, destAddress ,UdpCommonSetting.ReceiveRtnPort);// 创建发送类型的数据报：  

                this.lock.acquire();
                sendSocket.send(sendPacket); // 通过套接字发送数据
            this.lock.release();
                sendSocket.close();

                 Log.d("UdpQueryRspThread"," Response succeed");

             } catch (Exception e) {
                e.printStackTrace();
             }
    }

}
