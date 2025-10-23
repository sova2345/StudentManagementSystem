import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        // Simple admin login
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (!username.equals("admin") || !password.equals("1234")) {
            System.out.println("❌ Invalid credentials. Exiting...");
            return;
        }
        System.out.println("✅ Login successful!\n");

        int choice;
        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search by Course");
            System.out.println("5. Search by Age Range");
            System.out.println("6. Delete Student");
            System.out.println("7. Show Statistics");
            System.out.println("8. Export to CSV");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: "); int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: "); String name = sc.nextLine();
                    System.out.print("Enter Age: "); int age = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Course: "); String course = sc.nextLine();
                    manager.addStudent(new Student(id, name, age, course));
                    break;
                case 2: manager.viewAll(); break;
                case 3: System.out.print("Enter Student ID: "); manager.searchStudentById(sc.nextInt()); sc.nextLine(); break;
                case 4: System.out.print("Enter Course: "); manager.searchByCourse(sc.nextLine()); break;
                case 5: 
                    System.out.print("Min Age: "); int min = sc.nextInt();
                    System.out.print("Max Age: "); int max = sc.nextInt(); sc.nextLine();
                    manager.searchByAgeRange(min, max); 
                    break;
                case 6: System.out.print("Enter ID to delete: "); manager.deleteStudent(sc.nextInt()); sc.nextLine(); break;
                case 7: manager.showStats(); break;
                case 8: manager.exportCSV(); break;
                case 9: System.out.println("Exiting... 👋"); break;
                default: System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 9);

        sc.close();
    }
}
