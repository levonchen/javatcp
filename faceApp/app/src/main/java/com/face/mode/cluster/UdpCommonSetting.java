package com.face.mode.cluster;

public final class UdpCommonSetting {

    //接受查询的端口
    //广播也是通过给该端口发出查询请求
    public static final int ReceiveQueryPort = 6001;

    //接受查询结果的端口
    public static final int ReceiveRtnPort = 6002;

    //从发出请求到收到回复时间的最大延迟时间
    public  static final int WaitingTimout = 500;
}
