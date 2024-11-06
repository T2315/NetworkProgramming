
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAF_Object {
	private static final int NAME_LEN = 25;
	private static int count;
	private int recSize;
	private int nlen;
	private RandomAccessFile raf;
	
	public RAF_Object(String pathDest) throws IOException {
		this.raf = new RandomAccessFile(pathDest, "rw");
		if(raf.length() > 0) {
			count = raf.readInt();
			recSize = raf.readInt();
		} else {			
			count = 0;
			recSize = 4 + 4 + 8 + NAME_LEN*2;
			raf.writeInt(count);
			raf.writeInt(recSize);
		}
		nlen = (recSize - 4 - 4 - 8) / 2;
	}
	
	public void addStudent(Student st) throws IOException {
		raf.seek(raf.length());
		writeStudent(st);
		
		count++;
		raf.seek(0);
		raf.writeInt(count);
	}
	
	public Student getStudent(int index) throws IOException {
		if(index < 0 || index >= count) throw new IndexOutOfBoundsException("index not valid");
		int pos = 4 + 4 + recSize*index;
		raf.seek(pos);
		return readStudent();
	}
	
	public void updateStudent(int index, Student newSt) throws IOException {
		int pos = 4 + 4 + recSize*index;
		raf.seek(pos);
		writeStudent(newSt);
	}
	
	public Student findById(int id) throws IOException {
		int pos = 4 + 4;
		int indexRes = -1;
		for(int i = 0; i < count; i++) {
			raf.seek(pos + i*recSize);
			int idStudent = raf.readInt();
			if(idStudent == id) {indexRes = i; break;}
		}
		if(indexRes == -1) throw new IllegalArgumentException("id not exits in file!");
		else {
			raf.seek(pos + indexRes*recSize);
			return readStudent();
		}
	}
	
	public void writeStudent(Student st) throws IOException {
		raf.writeInt(st.getId());
		char c = 0;
		for(int i = 0; i < nlen; i++) {
			if (st.getName().length() > i) c = st.getName().charAt(i);
			else c = 0;
			raf.writeChar(c);
		}
		raf.writeInt(st.getbYear());
		raf.writeDouble(st.getGrade());
	}
	
	public Student readStudent() throws IOException {
		int id = raf.readInt();
		String name = "";
		char c;
		for(int i = 0; i < nlen; i++) {
			c = raf.readChar();
			if (c > 0) name += c;
		}
		int bYear = raf.readInt();
		double grade = raf.readDouble();
		return new Student(id, name, bYear, grade);
	}

}
