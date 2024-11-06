import java.io.File;

public class FileOperation {
	
	public static boolean delete(String path) {
		File dir = new File(path);
		if (!dir.exists()) return true;
		
		File[] contents = dir.listFiles();
		if (contents != null) 
			for (File item : contents) {
				delete(item.getAbsolutePath());
			}
		
		return dir.delete();
	}
	
	public static boolean deleteExpand(String path) {
		File dir = new File(path);
		if (!dir.exists()) return true;
		if (dir.isFile()) dir.delete();
		else if (dir.isDirectory()) {
			File[] contents = dir.listFiles();
			if (contents != null) {
				for (File item : contents) {
					deleteExpand(item.getAbsolutePath());
				}
			} else return false;
		}
		return dir.isDirectory();
	}

	public static void main(String args[]) {
		String path = "path\\Test";
		System.out.println(deleteExpand(path));
	}
}
