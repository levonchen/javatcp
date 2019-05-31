package com.face.mode.cluster;

public class UdpFaceData {
    public byte[] Name = new byte[50];
    public byte[] CardId = new byte[50];

    public  int PicItemLength = 0;
    public byte[] Picture = new byte[3000];

    public final int IntLength = 4;
    public final int NameLength = 50;
    public final int CardIdLength = 50;
    public final int PicLength = 3000;

    public final  int TotalLength = NameLength +CardIdLength +  IntLength + PicLength;


    public void setName(String name)
    {
        byte[] nameBytes = name.getBytes();
        int minLength = Math.min(nameBytes.length,NameLength);
        System.arraycopy(nameBytes,0,Name,0,minLength);
    }

    public String getName()
    {
        return new String(Name);
    }


    public void setCardId(String cardId)
    {
        byte[] nameBytes = cardId.getBytes();
        int minLength = Math.min(nameBytes.length,CardIdLength);
        System.arraycopy(nameBytes,0,CardId,0,minLength);
    }

    public String getCardId()
    {
        return new String(CardId);
    }

    public String InitFromBytes(byte[] inputs)
    {
        if(inputs.length != TotalLength)
        {
            return "Input init bytes is not";
        }

        int xStart = 0;
        System.arraycopy(inputs,xStart,Name,0,NameLength);
        xStart += NameLength;

        System.arraycopy(inputs,xStart,CardId,0,CardIdLength);
        xStart += CardIdLength;

        byte[] picLength = new byte[4];
        System.arraycopy(inputs,xStart,picLength,0,4);
        xStart += 4;

        PicItemLength = bytesToInt(picLength);
        System.arraycopy(inputs,xStart,Picture,0,PicItemLength);
        return "";
    }

    public byte[] ToByte()
    {
        byte[] ret = new byte[TotalLength];

        int xStart = 0;
        System.arraycopy(Name,0,ret,xStart,NameLength);
        xStart += NameLength;

        System.arraycopy(CardId,0,ret,xStart,CardIdLength);
        xStart += CardIdLength;

        byte[] picLenght = intToBytes(PicItemLength);
        System.arraycopy(picLenght,0,ret, xStart,4);
        xStart += 4;

        System.arraycopy(Picture,0,ret,xStart,PicItemLength);

        return ret;
    }


    public static byte[] intToBytes(int value )
    {
        byte[] src = new byte[4];
        src[0] =  (byte) (value & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[3] =  (byte) ((value>>24) & 0xFF);
        return src;
    }

    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24));
        return value;
    }


}
