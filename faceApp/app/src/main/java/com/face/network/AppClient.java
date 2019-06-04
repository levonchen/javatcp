package com.face.network;

import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.face.network.AppNetwork.*;

import java.io.IOException;

public class AppClient extends Thread {

    private Client client;
    @Override
    public void run()
    {
        try{

            Log.d("AppClient","running");

            client = new Client();
            //client.start();

            AppNetwork.register(client);

            addListener(client);

            //client.connect(5000, "192.168.10.55", AppNetwork.port);


            new Thread("Connect") {
                public void run () {
                    try {
                        client.start();
                        client.connect(5000, "192.168.10.55", AppNetwork.port);
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
        client.addListener(new Listener() {
            public void connected (Connection connection) {

                Log.d("AppClient","Connected");

                AppNetwork.RegisterName registerName = new AppNetwork.RegisterName();
                registerName.name = "android client";
                client.sendTCP(registerName);
            }

            public void received (Connection connection, Object object) {

                Log.d("AppClient","Received message");

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

}
