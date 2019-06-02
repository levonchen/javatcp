package com.face.mode.cluster;

import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 2 functions support:
 * 1: send query broadcast to all the other machine in the same domain
 */
public class UdpQueryReqThread extends Thread {

    private String dstAddress;

    private UdpFaceReqData ReqObj;
    private DatagramSocket socket;
    private InetAddress address;

    WifiManager wifiManager;
    private static WifiManager.MulticastLock lock;

    public UdpQueryReqThread(WifiManager wifimanager,byte[] inputCharacteristiDatas)
    {
        super();
        dstAddress = UdpCommonSetting.IP;

        ReqObj = new UdpFaceReqData();
        ReqObj.setCharacteristic(inputCharacteristiDatas);

        wifiManager = wifimanager;
        this.lock= wifimanager.createMulticastLock("UdpQueryReqThread");
    }



    @Override
    public void run()
    {
        try{
            Log.d("UdpQueryReqThread"," sending query:" + dstAddress);
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            address = InetAddress.getByName(dstAddress);

            byte[] buf = ReqObj.getCharacteristic();

            DatagramPacket packet;

            this.lock.acquire();
            packet = new DatagramPacket(buf, buf.length, address, UdpCommonSetting.ReceiveQueryPort);

            socket.send(packet);
            this.lock.release();

            Log.d("UdpQueryReqThread"," send succeed");

        }catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Error QueryReqThread:", e.getMessage());
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }
    }

}
