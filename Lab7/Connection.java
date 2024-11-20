import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Connection extends Thread {

	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}
	
	public void run() {
		try {
			while(true) {
				String line = dis.readUTF();
				if(line.equalsIgnoreCase("EXIT")) {
					break;
				}
				
				StringTokenizer stk = new StringTokenizer(line);				
				String command = stk.nextToken();
				String dest = stk.nextToken();
						
				Long size = dis.readLong();	
				if(command.equalsIgnoreCase("UPLOAD")) {
					try (FileOutputStream fos = new FileOutputStream(dest)) {		
						dataCopy(dis, fos, size);
						dos.writeUTF("DONE");
						dos.flush();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void dataCopy(InputStream is, FileOutputStream fos, long size) throws IOException {
		byte[] buff = new byte[102400];
		long rem = size;
		int byteReader;
		
		while(rem > 0) {
			int len = (int) (rem < buff.length ? rem : buff.length);
			byteReader = is.read(buff, 0, len);
			if (byteReader == -1) return;
			fos.write(buff, 0, byteReader);
			rem -= byteReader;
		}
	}
}
