import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
	
	public static void main(String args[]) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(12345);
		while(true) {
			Socket socket = server.accept();
			new Connection(socket).start();
		}
	}
 
}
