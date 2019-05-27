package org.appclient;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.appcommon.AppNetwork;
import org.appcommon.AppNetwork.ChatMessage;
import org.appcommon.AppNetwork.RegisterName;
import org.appcommon.AppNetwork.UpdateNames;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;



public class AppClient {
	private Client client;
	private String name;
	private AppFrame chatFrame;
	
	public AppClient() {
		client = new Client();
		client.start();
		
		AppNetwork.register(client);
		

		// Request the host from the user.
		String input = (String)JOptionPane.showInputDialog(null, "Host:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE,
			null, null, "localhost");
		if (input == null || input.trim().length() == 0) System.exit(1);
		final String host = input.trim();

		// Request the user's name.
		input = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE, null,
			null, "Test");
		if (input == null || input.trim().length() == 0) System.exit(1);
		name = input.trim();
		
		// All the ugly Swing stuff is hidden in ChatFrame so it doesn't clutter the KryoNet example code.
				chatFrame = new AppFrame(host);
				// This listener is called when the send button is clicked.
				chatFrame.setSendListener(new Runnable() {
					public void run () {
						ChatMessage chatMessage = new ChatMessage();
						chatMessage.text = chatFrame.getSendText();
						client.sendTCP(chatMessage);
					}
				});
				// This listener is called when the chat window is closed.
				chatFrame.setCloseListener(new Runnable() {
					public void run () {
						client.stop();
					}
				});
				chatFrame.setVisible(true);

				// We'll do the connect on a new thread so the ChatFrame can show a progress bar.
				// Connecting to localhost is usually so fast you won't see the progress bar.
				new Thread("Connect") {
					public void run () {
						try {
							client.connect(5000, host, AppNetwork.port);
							// Server communication after connection can go here, or in Listener#connected().
						} catch (IOException ex) {
							ex.printStackTrace();
							System.exit(1);
						}
					}
				}.start();
	}
	
	private void addListener(final Client client) {
		client.addListener(new Listener() {
			public void connected (Connection connection) {
				RegisterName registerName = new RegisterName();
				registerName.name = name;
				client.sendTCP(registerName);
			}

			public void received (Connection connection, Object object) {
				if (object instanceof UpdateNames) {
					UpdateNames updateNames = (UpdateNames)object;
					chatFrame.setNames(updateNames.names);
					return;
				}

				if (object instanceof ChatMessage) {
					ChatMessage chatMessage = (ChatMessage)object;
					chatFrame.addMessage(chatMessage.text);
					return;
				}
			}

			public void disconnected (Connection connection) {
				EventQueue.invokeLater(new Runnable() {
					public void run () {
						// Closing the frame calls the close listener which will stop the client's update thread.
						chatFrame.dispose();
					}
				});
			}
		});
	}
}
