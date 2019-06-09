package face.appserver;

import java.io.IOException;
import java.util.ArrayList;

import org.appcommon.AppNetwork;
import org.appcommon.AppNetwork.ChatMessage;
import org.appcommon.AppNetwork.RegisterName;
import org.appcommon.AppNetwork.UpdateNames;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import face.command.CommandFactory;


public class AppServer {
	Server server;
	
	public AppServer() throws IOException{
		server = new Server() {
			protected Connection newConnection() {
				// By providing our own connection implementation, we can store per
				// connection state without a connection ID to state look up.
				return new AppConnection();
			}
		};
		System.out.println("Hello World!");

		AppNetwork.register(server);

		addListener(server);

		try {

			server.bind(AppNetwork.port);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.start();
	}
	
    private void addListener(final Server server)
    {
        server.addListener(new Listener() {
        	
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually ChatConnections.
				AppConnection connection = (AppConnection)c;
				
				System.out.println("Received Message:" + c.getID());
				
				if(object instanceof AppNetwork.FaceObjBase)
				{
					AppNetwork.FaceObjBase faceObj = ((AppNetwork.FaceObjBase) object);
					
					face.command.ICommand cmd = CommandFactory.getInstance().GetExecutor(faceObj.Command);
					face.command.Context ct = new face.command.Context();
					ct.connect = c;
					ct.field = faceObj;
					ct.server = server;
					cmd.OnExecute(ct);
					return;
				}

				if (object instanceof RegisterName) {
					// Ignore the object if a client has already registered a name. This is
					// impossible with our client, but a hacker could send messages at any time.
					if (connection.name != null) return;
					// Ignore the object if the name is invalid.
					String name = ((RegisterName)object).name;
					if (name == null) return;
					name = name.trim();
					if (name.length() == 0) return;
					// Store the name on the connection.
					connection.name = name;
					// Send a "connected" message to everyone except the new client.
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.text = name + " connected.";
					server.sendToAllExceptTCP(connection.getID(), chatMessage);
					// Send everyone a new list of connection names.
					updateNames();
					return;
				}

				if (object instanceof ChatMessage) {
					// Ignore the object if a client tries to chat before registering a name.
					if (connection.name == null) return;
					ChatMessage chatMessage = (ChatMessage)object;
					// Ignore the object if the chat message is invalid.
					String message = chatMessage.text;
					if (message == null) return;
					message = message.trim();
					if (message.length() == 0) return;
					// Prepend the connection's name and send to everyone.
					chatMessage.text = connection.name + ": " + message;
					server.sendToAllTCP(chatMessage);
					return;
				}
			}

			public void disconnected (Connection c) {
				AppConnection connection = (AppConnection)c;
				if (connection.name != null) {
					// Announce to everyone that someone (with a registered name) has left.
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.text = connection.name + " disconnected.";
					server.sendToAllTCP(chatMessage);
					updateNames();
				}
			}
        	
        });
    }
    
	void updateNames () {
		// Collect the names for each connection.
		Connection[] connections = server.getConnections();
		ArrayList<String> names = new ArrayList<String>(connections.length);
		for (int i = connections.length - 1; i >= 0; i--) {
			AppConnection connection = (AppConnection)connections[i];
			names.add(connection.name);
		}
		// Send the names to everyone.
		UpdateNames updateNames = new UpdateNames();
		updateNames.names = (String[])names.toArray(new String[names.size()]);
		server.sendToAllTCP(updateNames);
	}
}
