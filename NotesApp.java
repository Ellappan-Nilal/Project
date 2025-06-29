import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NotesApp {

    
    private static final String NOTES_FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        
        do {
            printMenu();
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
                    addNote(scanner);
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    System.out.println("Exiting Notes App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();

        } while (choice != 3);

        scanner.close();
    }

    
    private static void printMenu() {
        System.out.println("--- Notes Manager ---");
        System.out.println("1. Add New Note");
        System.out.println("2. View All Notes");
        System.out.println("3. Exit");
        System.out.println("---------------------");
    }

    private static void addNote(Scanner scanner) {
        System.out.println("\n--- Add New Note ---");
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTES_FILE_NAME, true))) {
            writer.write(note);
            writer.newLine();
            System.out.println("Note added successfully!");
        } catch (IOException e) {
            
            System.err.println("Error writing note to file: " + e.getMessage());
        }
    }

   
    private static void viewNotes() {
        System.out.println("\n--- All Notes ---");
        String line;
        boolean notesFound = false;

       
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE_NAME))) {
         
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                notesFound = true;
            }
            if (!notesFound) {
                System.out.println("No notes found yet. Add some notes!");
            }
        } catch (IOException e) {
           
            System.err.println("Error reading notes from file: " + e.getMessage());
            System.out.println("It seems the notes file ('" + NOTES_FILE_NAME + "') does not exist or is inaccessible.");
            System.out.println("Add a note first to create the file.");
        }
    }
}
