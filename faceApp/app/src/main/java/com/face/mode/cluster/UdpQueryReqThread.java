package com.face.mode.cluster;

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
    public UdpQueryReqThread(String addr,byte[] inputCharacteristiDatas)
    {
        super();
        dstAddress = addr;

        ReqObj = new UdpFaceReqData();
        ReqObj.setCharacteristic(inputCharacteristiDatas);
    }


    @Override
    public void run()
    {
        try{
            socket = new DatagramSocket();
            address = InetAddress.getByName(dstAddress);

            byte[] buf = ReqObj.getCharacteristic();

            DatagramPacket packet;

            packet = new DatagramPacket(buf, buf.length, address, UdpCommonSetting.ReceiveQueryPort);
            socket.send(packet);

            Log.d("QueryReqThread Send","succeed");

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
