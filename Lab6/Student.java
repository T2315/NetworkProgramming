public class Student {
	int id;
	String name;
	int bYear;
	double grade;
	
	public Student(int id, String name, int bYear) {
		this.id = id;
		this.name = name;
		this.bYear = bYear;
	}
	
	public String toString() {
		return id + "\t" + name + "\t" + bYear + "\t" + grade;
	}
}