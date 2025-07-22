import java.util.ArrayList;
import java.util.Scanner;


class Student {
    private int id;
    private String name;
    private double marks;

   
    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

   
    
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks;
    }
}


public class Task2_Student_Management {

   
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1; 

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
                System.out.print("Enter your choice: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting Student Record Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println(); 
        } while (choice != 5);

        scanner.close();
    }

    
    private static void displayMenu() {
        System.out.println("--- Student Record Management System ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student Record");
        System.out.println("4. Delete Student Record");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------");
    }

   
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        double marks;
        while (true) {
            System.out.print("Enter student marks: ");
            if (scanner.hasNextDouble()) {
                marks = scanner.nextDouble();
                if (marks >= 0 && marks <= 100) { 
                    break;
                } else {
                    System.out.println("Marks must be between 0 and 100.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for marks.");
                scanner.next(); 
            }
        }
        scanner.nextLine(); 

        Student newStudent = new Student(nextId++, name, marks);
        studentList.add(newStudent);
        System.out.println("Student added successfully! ID: " + newStudent.getId());
    }

    
    private static void viewStudents() {
        System.out.println("\n--- All Students ---");
        if (studentList.isEmpty()) {
            System.out.println("No students found in the record.");
        } else {
            for (Student student : studentList) {
                System.out.println(student);
            }
        }
    }

 
    private static void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No students to update. Please add students first.");
            return;
        }

        System.out.print("Enter the ID of the student to update: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number for ID.");
            scanner.next();
            System.out.print("Enter the ID of the student to update: ");
        }
        int idToUpdate = scanner.nextInt();
        scanner.nextLine(); 
        Student studentToUpdate = null;
        for (Student student : studentList) {
            if (student.getId() == idToUpdate) {
                studentToUpdate = student;
                break;
            }
        }

        if (studentToUpdate != null) {
            System.out.println("Found student: " + studentToUpdate);

            System.out.print("Enter new name (leave blank to keep current: '" + studentToUpdate.getName() + "'): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                studentToUpdate.setName(newName);
            }

            System.out.print("Enter new marks (leave blank to keep current: " + studentToUpdate.getMarks() + "): ");
            String newMarksStr = scanner.nextLine();
            if (!newMarksStr.isEmpty()) {
                try {
                    double newMarks = Double.parseDouble(newMarksStr);
                    if (newMarks >= 0 && newMarks <= 100) {
                        studentToUpdate.setMarks(newMarks);
                    } else {
                        System.out.println("Marks must be between 0 and 100. Marks not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid marks format. Marks not updated.");
                }
            }
            System.out.println("Student record updated successfully!");
        } else {
            System.out.println("Student with ID " + idToUpdate + " not found.");
        }
    }

   
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        if (studentList.isEmpty()) {
            System.out.println("No students to delete. Please add students first.");
            return;
        }

        System.out.print("Enter the ID of the student to delete: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number for ID.");
            scanner.next(); 
            System.out.print("Enter the ID of the student to delete: ");
        }
        int idToDelete = scanner.nextInt();
        scanner.nextLine(); 

        boolean removed = false;
     
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId() == idToDelete) {
                System.out.println("Deleting student: " + studentList.get(i));
                studentList.remove(i);
                removed = true;
                System.out.println("Student record deleted successfully!");
                break;
            }
        }

        if (!removed) {
            System.out.println("Student with ID " + idToDelete + " not found.");
        }
    }
}
