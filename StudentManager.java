import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentManager {
    private static final String FILE_NAME = "students.txt";
    private List<Student> students = new ArrayList<>();

    public StudentManager() {
        loadFromFile();
    }

    public void addStudent(Student s) {
        if (students.stream().anyMatch(st -> st.getId() == s.getId())) {
            System.out.println("❌ Student ID already exists!");
            return;
        }
        students.add(s);
        saveToFile();
        System.out.println("✅ Student added successfully!");
    }

    public void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("ID | Name | Age | Course");
        students.forEach(System.out::println);
    }

    public void searchStudentById(int id) {
        students.stream().filter(s -> s.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Student not found.")
                );
    }

    public void searchByCourse(String course) {
        List<Student> result = students.stream()
                .filter(s -> s.getCourse().equalsIgnoreCase(course))
                .collect(Collectors.toList());
        if (result.isEmpty()) System.out.println("No students in course: " + course);
        else result.forEach(System.out::println);
    }

    public void searchByAgeRange(int min, int max) {
        List<Student> result = students.stream()
                .filter(s -> s.getAge() >= min && s.getAge() <= max)
                .collect(Collectors.toList());
        if (result.isEmpty()) System.out.println("No students in age range " + min + "-" + max);
        else result.forEach(System.out::println);
    }

    public void deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        if (removed) System.out.println("✅ Student removed.");
        else System.out.println("❌ Student not found.");
        saveToFile();
    }

    public void showStats() {
        System.out.println("Total Students: " + students.size());
        double avgAge = students.stream().mapToInt(Student::getAge).average().orElse(0);
        System.out.println("Average Age: " + avgAge);

        Map<String, Long> courseCount = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse, Collectors.counting()));
        System.out.println("Students per Course: " + courseCount);
    }

    public void exportCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("students_export.csv"))) {
            writer.println("ID,Name,Age,Course");
            for (Student s : students) writer.println(s.toCSV());
            System.out.println("✅ Exported to students_export.csv");
        } catch (IOException e) {
            System.out.println("❌ Error exporting CSV");
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) writer.println(s.toCSV());
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                if (data.length == 4) {
                    students.add(new Student(
                            Integer.parseInt(data[0]),
                            data[1],
                            Integer.parseInt(data[2]),
                            data[3]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
