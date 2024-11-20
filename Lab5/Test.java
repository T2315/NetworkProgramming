import java.io.IOException;

public class Test {
	
	public static void main(String[] args) throws IOException {
		String path = "path\\students.file";
		RAF_Object manageStudent = new RAF_Object(path);
    manageStudent.addStudent(new Student(111, "Nguyễn Văn A", 2004, 3.7));
		manageStudent.addStudent(new Student(222, "Trần văn thị", 2004, 3.5));
		
    manageStudent.updateStudent(1, new Student(123, "Trần văn thị", 2005, 3.5));
		Student st = manageStudent.findById(123);
		System.out.println(st);
	}
}
