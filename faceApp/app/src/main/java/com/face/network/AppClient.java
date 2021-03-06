package com.face.network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.face.faceapp.MainActivity;

import org.appcommon.AppNetwork;
import org.appcommon.AppNetwork.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppClient extends Thread {

    private Client client;

    private static String IP =  "192.168.10.55";  //"192.168.1.5";

    //处理从服务器端回收来的消息
    private AppReceiver receiver;

    //通过线程发送req 请求到服务度
    private ExecutorService reqPool;

    public AppClient()
    {
        reqPool = Executors.newSingleThreadExecutor();
    }

    //call to stop AppReceiver
    public void Stop()
    {
        reqPool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!reqPool.awaitTermination(60, TimeUnit.MICROSECONDS)) {
                reqPool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!reqPool.awaitTermination(60, TimeUnit.MICROSECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            reqPool.shutdownNow();
        }
    }

    @Override
    public void run()
    {
        try{

            Log.d("AppClient","running");

            client = new Client();
            //client.start();
            AppNetwork.register(client);
            addListener(client);


            new Thread("Connect") {
                public void run () {
                    try {
                        client.start();
                        client.connect(5000, AppClient.IP, AppNetwork.port);
                        // Server communication after connection can go here, or in Listener#connected().
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();

        }catch (Exception ex) {
            Log.d("AppClient:",ex.getMessage());
        }
    }

    private void addListener(final Client client) {
        receiver = new AppReceiver(client);

        client.addListener(new Listener() {
            public void connected (Connection connection) {

                Log.d("AppClient","Connected");

                AppNetwork.RegisterName registerName = new AppNetwork.RegisterName();
                registerName.name = "android client";
                client.sendTCP(registerName);
            }

            public void received (Connection connection, Object object) {

                Log.d("AppClient","Received message");

                if(object instanceof AppNetwork.FaceObjBase)
                {
                    AppNetwork.FaceObjBase faceObj = (AppNetwork.FaceObjBase)object;
                    receiver.onHandleReceived(connection,faceObj);
                    return;
                }

                if (object instanceof UpdateNames) {
                    UpdateNames updateNames = (UpdateNames)object;

                    Log.d("AppClient:",updateNames.names.toString());
                    return;
                }

                if (object instanceof ChatMessage) {
                    ChatMessage chatMessage = (ChatMessage)object;
                    Log.d("AppClient:",chatMessage.text);
                    return;
                }
            }

            public void disconnected (Connection connection) {
                Log.d("AppClient:","Disconnected");
            }
        });
    }


    public void ReqLogin(final String id){

        reqPool.execute(new Runnable() {
            @Override
            public void run() {
                AppNetwork.ReqLogin field = new AppNetwork.ReqLogin();
                field.Id = id;
                client.sendTCP(field);
            }
        });
    }
}
