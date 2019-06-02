package com.face.mode.cluster;

public class UdpFaceReqData {
    private byte[] characteristic = new byte[128];

    public static final  int length = 128;

    public void setCharacteristic(byte[] datas)
    {
        characteristic = new byte[128];
        for(int i = 0;i < length; i++)
        {
            characteristic[i] = '\0';
        }
        int nLenght = Math.min(datas.length,length);
        System.arraycopy(datas,0,characteristic,0,nLenght);
    }

    public byte[] getCharacteristic()
    {
        return characteristic;
    }
}
