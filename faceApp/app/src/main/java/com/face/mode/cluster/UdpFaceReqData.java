package com.face.mode.cluster;

public class UdpFaceReqData {
    private byte[] characteristic = new byte[128];

    public static final  int length = 128;

    public void setCharacteristic(byte[] datas)
    {
        System.arraycopy(datas,0,characteristic,0,length);
    }

    public byte[] getCharacteristic()
    {
        return characteristic;
    }
}
