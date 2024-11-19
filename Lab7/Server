import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String args[]) throws IOException {
		int port = 2000;
		
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		
		while(true) {
			Socket socket = server.accept();
			new Connection(socket).start();
		}
	}
}
