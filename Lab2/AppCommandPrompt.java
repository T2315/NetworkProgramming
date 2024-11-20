import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class AppCMD {
	
	private File pathDefault;
	private boolean exited = false;
	private BufferedReader inputUser;
	
	public AppCMD(String path) {
		pathDefault = new File(path);
	}
	
	private void showPath() {
		System.out.print("\n"+ pathDefault + ">");
	}
	
	private void enterCommand() {
		inputUser = new BufferedReader(new InputStreamReader(System.in));	
	}
	
	private String execute(String command) {
		String result = "";
		String current = "";
		String para = "";
		
		command = command.trim();
		if (command.isEmpty()) return "";
		
		StringTokenizer cmd = new StringTokenizer(command);
		current = cmd.nextToken().toUpperCase();
		if (cmd.hasMoreTokens()) para = cmd.nextToken();
		
		switch (current) {
		case "EXIT":
			exited = true;
			result = "";
			break;
		case "DIR":
			result = showDirectory();
			break;
		case "CD":
			result = changeDirectory(para);
			break;
		case "DELETE":
			result = deleteOperation(para);
		}
		
		return result;
	}
	
	private String changeDirectory(String para) {
		String result = "";
		
		if (para.isEmpty()) return "";
		else if (para.equals("..")) {
			if (this.pathDefault.getParentFile() != null) { 
				pathDefault = pathDefault.getParentFile();
			}
		} else {
			String newPath = pathDefault.getAbsolutePath() + "\\" + para;
			File tmp = new File(newPath);
			if(!tmp.exists()) result = "The system cannot find the path specified.";
			if(tmp.isDirectory()) {
				this.pathDefault = tmp;
			} else 
				result = "The directory name is invalid.";
		}
			
		return result;
	}
	
	private String showDirectory() {
		Set<String> listNameFile = new TreeSet<>();	
		Set<String> listNameDir = new TreeSet<>();	
		File[] listFile = pathDefault.listFiles();
		for (File element : listFile) {
			if(element.isDirectory())
				listNameDir.add(element.getName().toUpperCase());
			else if(element.isFile())
				listNameFile.add(element.getName().toLowerCase());
		}
		
		List<String> dirAndFile = new ArrayList<>();
		for (String dir : listNameDir) dirAndFile.add(dir);
		for (String file : listNameFile) dirAndFile.add(file);
		
		String res = dirAndFile.toString();
		return res.substring(1, res.length()-1).replace(", ", "\n");
	}
	
	private String deleteOperation(String para) {
		String result = "";
		String newPath = pathDefault.getAbsolutePath() + "\\" + para;
		delete(newPath);
		return result;
	}
	
	private boolean delete(String path) {
		File dir = new File(path);
		if (!dir.exists()) return true;
		
		File[] contents = dir.listFiles();
		if (contents != null) 
			for (File item : contents) {
				delete(item.getAbsolutePath());
			}
		
		return dir.delete();
	}
	
	
	public void run() throws IOException {
		String result = "";
		showPath();
		while(!exited) {
			enterCommand();
			result = execute(inputUser.readLine());
			System.out.println(result);
			showPath();
		}	
	}
	
	public static void main(String args[]) {
		String dir = "path\\Test";
		try {
			new AppCMD(dir).run();
		} catch (IOException e) {
			System.out.println("Error");
		}		
	}
	
}
