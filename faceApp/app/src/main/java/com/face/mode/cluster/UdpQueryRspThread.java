package com.face.mode.cluster;

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
    public UdpQueryRspThread( )
    {
        super();

    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        try{
            socket = new DatagramSocket(UdpCommonSetting.ReceiveQueryPort);

            byte[] buf = new byte[UdpFaceReqData.length];
            while (running){

                DatagramPacket msgPackage = new DatagramPacket(buf,buf.length);
                socket.receive(msgPackage);

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
                DatagramSocket sendSocket = new DatagramSocket();

                UdpFaceRtnData rtnData = new UdpFaceRtnData();

                rtnData.setName("测试");
                rtnData.setCardId("01234567899876543210");

                //TODO: add picture

                byte[] buf = rtnData.ToBytes();

                 DatagramPacket sendPacket = new DatagramPacket(buf, UdpFaceRtnData.TotalLength, destAddress ,UdpCommonSetting.ReceiveRtnPort);// 创建发送类型的数据报：  

                sendSocket.send(sendPacket); // 通过套接字发送数据
                sendSocket.close();
             } catch (Exception e) {
                e.printStackTrace();
             }
    }

}
