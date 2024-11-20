import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Connection extends Thread {
	Socket socket;
	BufferedReader netIn;
	PrintWriter netOut;
	
	double operand1, operand2;
	char operator;

	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		netOut = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void run() {
		netOut.println("Welcome simple calcualtor assitance!!");
		String line;
		
		try {
			while(true) {
				line = netIn.readLine();
				if (line.equalsIgnoreCase("EXIT")) break;
				try {
					analysisInput(line);
					double res = getMathExecute();
					netOut.println(line + " = " + res);	
				} catch (MyException e) {
					netOut.println(e.getMessage());
				}
			}
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private double getMathExecute() throws MyException {
		double res = 0;
		switch(operator) {
		case '+': res = operand1+operand2; break;
		case '-': res = operand1-operand2; break;
		case '*': res = operand1*operand2; break;
		case '/': 
			res = operand1/operand2; 
			if (Double.isInfinite(res)) throw new MyException("Phep chia cho 0.");
			break;
		}
		return res;
	}

	private void analysisInput(String line) throws MyException {
		StringTokenizer stk = new StringTokenizer(line, "+-*/");
		String od1, od2;
		try {
			od1 = stk.nextToken();		
		} catch(NoSuchElementException e) {
			throw new MyException("Thieu toan hang thu 1.");
		}
		
		try {
			od2 = stk.nextToken();
		} catch(NoSuchElementException e) {
			throw new MyException("Thieu toan hang thu 2.");
		}
		
		try {
			this.operand1 = Double.parseDouble(od1.trim());	
		} catch(NumberFormatException e) {
			throw new MyException("Toan hang thu 2 khong phai so.");
		}
		
		try {
			this.operand2 = Double.parseDouble(od2.trim());			
		} catch(NumberFormatException e) {
			throw new MyException("Toan hang thu 2 khong phai so.");
		}
		
		this.operator = line.charAt(od1.length());
	}
	
}
