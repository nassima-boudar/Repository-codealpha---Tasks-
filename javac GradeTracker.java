import java.util.ArrayList;
import java.util.Scanner;

// ==================== CLASS: Student ====================
class Student {
    private String name;
    private ArrayList<Double> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            grades.add(grade);
            System.out.println("✅ Grade added: " + grade);
        } else {
            System.out.println("❌ Invalid grade! Must be between 0 and 100.");
        }
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0;
        for (double g : grades) {
            sum += g;
        }
        return sum / grades.size();
    }

    public double getHighestGrade() {
        if (grades.isEmpty()) return 0.0;
        double max = grades.get(0);
        for (double g : grades) {
            if (g > max) max = g;
        }
        return max;
    }

    public double getLowestGrade() {
        if (grades.isEmpty()) return 0.0;
        double min = grades.get(0);
        for (double g : grades) {
            if (g < min) min = g;
        }
        return min;
    }

    public int getGradeCount() {
        return grades.size();
    }

    public void displayGrades() {
        System.out.print("Grades: ");
        for (double g : grades) {
            System.out.print(g + "  ");
        }
        System.out.println();
    }
}

// ==================== CLASS: GradeTracker ====================
public class GradeTracker {
    private ArrayList<Student> students;
    private Scanner scanner;

    public GradeTracker() {
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("========================================");
        System.out.println("   📚 STUDENT GRADE TRACKER SYSTEM");
        System.out.println("========================================\n");

        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1: addStudent(); break;
                case 2: addGradeToStudent(); break;
                case 3: viewStudentDetails(); break;
                case 4: viewAllStudentsSummary(); break;
                case 5: viewClassStatistics(); break;
                case 6: deleteStudent(); break;
                case 0: 
                    System.out.println("\n👋 Thank you for using Grade Tracker! Goodbye.");
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please try again.\n");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n---------- MAIN MENU ----------");
        System.out.println("1. ➕ Add New Student");
        System.out.println("2. 📝 Add Grade to Student");
        System.out.println("3. 👤 View Student Details");
        System.out.println("4. 📊 View All Students Summary");
        System.out.println("5. 📈 View Class Statistics");
        System.out.println("6. 🗑️  Delete Student");
        System.out.println("0. 🚪 Exit");
        System.out.println("-------------------------------");
    }

    private void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Name cannot be empty!");
            return;
        }
        students.add(new Student(name));
        System.out.println("✅ Student '" + name + "' added successfully!\n");
    }

    private void addGradeToStudent() {
        if (students.isEmpty()) {
            System.out.println("❌ No students found! Add a student first.\n");
            return;
        }
        listAllStudents();
        int index = getIntInput("Enter student number: ") - 1;
        if (index < 0 || index >= students.size()) {
            System.out.println("❌ Invalid student number!\n");
            return;
        }
        double grade = getDoubleInput("Enter grade (0-100): ");
        students.get(index).addGrade(grade);
        System.out.println();
    }

    private void viewStudentDetails() {
        if (students.isEmpty()) {
            System.out.println("❌ No students found!\n");
            return;
        }
        listAllStudents();
        int index = getIntInput("Enter student number: ") - 1;
        if (index < 0 || index >= students.size()) {
            System.out.println("❌ Invalid student number!\n");
            return;
        }
        Student s = students.get(index);
        System.out.println("\n========== STUDENT DETAILS ==========");
        System.out.println("Name: " + s.getName());
        s.displayGrades();
        System.out.println("Total Grades: " + s.getGradeCount());
        System.out.println("Average: " + String.format("%.2f", s.getAverage()));
        System.out.println("Highest: " + s.getHighestGrade());
        System.out.println("Lowest: " + s.getLowestGrade());
        System.out.println("=====================================\n");
    }

    private void viewAllStudentsSummary() {
        if (students.isEmpty()) {
            System.out.println("❌ No students to display!\n");
            return;
        }
        System.out.println("\n========== ALL STUDENTS SUMMARY ==========");
        System.out.printf("%-20s %-10s %-10s %-10s %-10s%n", 
            "Name", "Grades", "Average", "Highest", "Lowest");
        System.out.println("--------------------------------------------------------------");
        for (Student s : students) {
            System.out.printf("%-20s %-10d %-10.2f %-10.2f %-10.2f%n",
                s.getName(), s.getGradeCount(), s.getAverage(), 
                s.getHighestGrade(), s.getLowestGrade());
        }
        System.out.println("==========================================\n");
    }

    private void viewClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("❌ No data available!\n");
            return;
        }
        double totalAverage = 0;
        double highestInClass = 0;
        double lowestInClass = 100;
        int totalGrades = 0;

        for (Student s : students) {
            totalAverage += s.getAverage();
            if (s.getHighestGrade() > highestInClass) highestInClass = s.getHighestGrade();
            if (s.getLowestGrade() < lowestInClass) lowestInClass = s.getLowestGrade();
            totalGrades += s.getGradeCount();
        }

        System.out.println("\n========== CLASS STATISTICS ==========");
        System.out.println("Total Students: " + students.size());
        System.out.println("Total Grades Entered: " + totalGrades);
        System.out.println("Class Average: " + String.format("%.2f", totalAverage / students.size()));
        System.out.println("Highest Grade in Class: " + highestInClass);
        System.out.println("Lowest Grade in Class: " + lowestInClass);
        System.out.println("======================================\n");
    }

    private void deleteStudent() {
        if (students.isEmpty()) {
            System.out.println("❌ No students to delete!\n");
            return;
        }
        listAllStudents();
        int index = getIntInput("Enter student number to delete: ") - 1;
        if (index < 0 || index >= students.size()) {
            System.out.println("❌ Invalid student number!\n");
            return;
        }
        Student removed = students.remove(index);
        System.out.println("✅ Student '" + removed.getName() + "' deleted successfully!\n");
    }

    private void listAllStudents() {
        System.out.println("\n--- Students List ---");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(scanner.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        GradeTracker tracker = new GradeTracker();
        tracker.run();
    }
}