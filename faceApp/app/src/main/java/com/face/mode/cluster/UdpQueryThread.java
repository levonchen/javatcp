package com.face.mode.cluster;

/**
 * 2 functions support:
 * 1: send query broadcast to all the other machine in the same domain
 * 2: receive query check from the other
 */
public class UdpQueryThread extends Thread {

    private String dstAddress;
    private int dstPort;
    private UdpReturnHandler Handler = null;
    private boolean running = false;

    public UdpQueryThread(String addr, int port, UdpReturnHandler handler)
    {
        super();

        dstAddress = addr;
        dstPort = port;
        this.Handler = handler;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }


}
