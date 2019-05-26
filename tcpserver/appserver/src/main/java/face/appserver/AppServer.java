package face.appserver;

import java.io.IOException;

import org.appcommon.AppNetwork;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class AppServer {
	private Server server;
	
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
	
    private void addListener(Server server)
    {
        server.addListener(new Listener() {
        	
        });
    }
}
