import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Import_Export_files {
	
	public static List<Student> loadData(String stFile, String gradeFile, String charset) throws IOException {
		List<Student> listSt = new ArrayList<>();
		
		BufferedReader buffStReader = new BufferedReader(new InputStreamReader(new FileInputStream(stFile), charset));
		BufferedReader buffGradeReader = new BufferedReader(new InputStreamReader(new FileInputStream(gradeFile), charset));
		
		List<Integer> listId = new ArrayList<>();
		
		String line = "";
		while((line = buffStReader.readLine()) != null) {
			StringTokenizer token = new StringTokenizer(line, "\t");
			int id = Integer.parseInt(token.nextToken());
			listId.add(id);
			String name = token.nextToken();
			int bYear = Integer.parseInt(token.nextToken());
			listSt.add(new Student(id, name, bYear));
		}
		
		line = "";
		while((line = buffGradeReader.readLine()) != null) {
			StringTokenizer token = new StringTokenizer(line, "\t");
			int id = Integer.parseInt(token.nextToken());
			int pos = listId.indexOf(id);
			int count = 0;
			double sum = 0;
			while(token.hasMoreTokens()) {
				sum += Double.parseDouble(token.nextToken());
				count++;
			}
			double diem = sum / count;
			listSt.get(pos).grade = Math.round(diem * 100.0) / 100.0;
		}
			
		buffStReader.close();
		buffGradeReader.close();
		
		return listSt;
	}
	
	public static void export(List<Student> list, String textFile, String charset) throws IOException {
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(textFile), charset));
		for(Student st : list) {
			String text = st.toString();
			writer.println(text);
		}
		writer.close();
	}
	
	public static void printListStudent(List<Student> list) {
		for(Student st : list) {
			System.out.println(st);
		}
	}
	
	public static void main(String[] args) throws IOException {
		String pathFileSV = "path\\TestLab6\\sv.txt";
		String pathFileDiem = "path\\TestLab6\\diem.txt";
		String pathFileList = "path\\listStudent.txt";
		
		List<Student> res = loadData(pathFileSV, pathFileDiem, "UTF-8");
		
		printListStudent(res);
		export(res, pathFileList, "UTF-8");
	}
	
}
