import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Pack_Unpack {

	public static void pack(String folder, String packedFile) throws IOException {
		byte[] buff = new byte[102400];
		
		List<File> files = getFiles(folder);
		
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		
		raf.writeInt(files.size());
		
		long pos = 0;
		long[] headPos = new long[files.size()];
		int index = 0;
		
		for (File file : files) { // header
			headPos[index++] = raf.getFilePointer();
			raf.writeLong(pos);
			raf.writeLong(file.length());
			raf.writeUTF(file.getName());
		}
		index = 0;
		int count = 0;
		for (File file : files) { // data
			pos = raf.getFilePointer();
			raf.seek(headPos[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			FileInputStream fis = new FileInputStream(file);
			while ((count = fis.read(buff)) != -1) {
				raf.write(buff, 0, count);
			}
			fis.close();
		}
		raf.close();

	}

	public static void unpack(String packedFile, String extractFile, String destFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		int fileName = raf.readInt();

		long pos, size;
		String name = "";
		for (int i = 0; i < fileName; i++) {
			pos = raf.readLong();
			size = raf.readLong();
			name = raf.readUTF();
			if (extractFile.equalsIgnoreCase(name)) {
				raf.seek(pos);
				FileOutputStream fos = new FileOutputStream(destFile);
				dataCopy(raf, fos, size);
				fos.close();
				break;
			}
		}
		raf.close();
	}

	private static void dataCopy(RandomAccessFile raf, FileOutputStream fos, long size) throws IOException {
		byte[] buff = new byte[102400];
		long rem = size;
		int byteReader;
		
		while(rem > 0) {
			int len = (int) (rem < buff.length ? rem : buff.length);
			byteReader = raf.read(buff, 0, len);
			if (byteReader == -1) return;
			fos.write(buff, 0, byteReader);
			rem -= byteReader;
		}
	}

	private static List<File> getFiles(String folder) {
		List<File> res = new ArrayList<File>();
		File[] files = new File(folder).listFiles();
		for (File f : files) {
			if (f.isFile())
				res.add(f);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		String path = "path\\Test1";
		String packedFile = "path\\abc.pack";
		pack(path, packedFile);
		
		String extractFile = "ABC.pdf";
		String fileDest = "path\\ABC_copy.pdf";
		unpack(packedFile,extractFile , fileDest);
	}
}
