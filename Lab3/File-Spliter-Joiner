import java.io.*;

public class FileSpliterJoiner {
	
	public static void split(String source, int pSize) throws IOException{
		FileInputStream fis = new FileInputStream(source);
		
		boolean hasMoreData = true;
		int exNum = 1;
		
		while(hasMoreData) {
			String path = source + createExtension(exNum);
			FileOutputStream fos = new FileOutputStream(path);
			hasMoreData = copyData(fis, fos, pSize);
			fos.close();
			exNum++;
		}
		fis.close();
	}
	
	private static boolean copyData(FileInputStream fis, FileOutputStream fos, int pSize) throws IOException {
		byte[] buff = new byte[102400];
		int rem = pSize;
		int byteReader;
		
		while(rem > 0) {
			int len = rem < buff.length ? rem : buff.length;
			byteReader = fis.read(buff, 0, len);
			if (byteReader == -1) return false;
			fos.write(buff, 0, byteReader);
			rem -= byteReader;
		}
		
		return true;
	}

	private static String createExtension(int exNum) {
		String res = exNum + "";
		while(res.length() < 3) res = "0" + res;
		return "."+res;
	}

	public static void join(String partFileName) throws IOException {
		String source = partFileName.substring(0, partFileName.lastIndexOf("."));
		FileOutputStream fos = new FileOutputStream(source);
		
		boolean hasMoreData = true;
		int exNum = 1;
		
		while(hasMoreData) {
			String path = source + createExtension(exNum);
			File file = new File(path);
			if(!file.exists()) break;
			FileInputStream fis = new FileInputStream(path);
			hasMoreData = copyData(fis, fos, (int)file.length());
			fis.close();
			exNum++;
		}
		fos.close();
	}
	
	
	public static void main(String args[]) throws IOException {
		String source = "path\\Test\\ABC.pdf";
		int pSize = 1000000;
		
		split(source, pSize);
		
		String dest = "path\\Test\\ABC.pdf.002";
		join(dest);

	}
}
