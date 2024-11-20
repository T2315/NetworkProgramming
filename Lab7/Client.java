import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
	public static void main(String[] args) throws IOException {
		int port = 2000;
		String host = "127.0.0.1";
		DataInputStream dis;
		DataOutputStream dos;

		Socket socket = new Socket(host, port);
		
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());

		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String line = input.readLine();
			line.trim();

			if (line.equalsIgnoreCase("EXIT")) {
				dos.writeUTF("EXIT");
				dos.flush();
				break;
			} else {
				StringTokenizer stk = new StringTokenizer(line);
				String command, source, des;
				command = stk.nextToken();
				source = stk.nextToken();
				des = stk.nextToken();

				dos.writeUTF(command + " " + des);
				dos.flush();
				
				File file = new File(source);
				try (FileInputStream fis = new FileInputStream(file)) {
					dos.writeLong(file.length());
					int count;
					byte buff[] = new byte[102400];
					while ((count = fis.read(buff)) != -1) {
						dos.write(buff, 0, count);
					}
					dos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(dis.readUTF());
			}
		}
		socket.close();
	}
}
