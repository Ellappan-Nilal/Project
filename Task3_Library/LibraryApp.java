<<<<<<< HEAD
package Task3_Library;

public class LibraryApp {
    public static void main(String[] args) {
      
        Library myLibrary = new Library();

        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565");
        Book book2 = new Book("1984", "George Orwell", "978-0451524935");
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084");
        Book book4 = new Book("Pride and Prejudice", "Jane Austen", "978-0141439518");
  
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addBook(book3);
        myLibrary.addBook(book4);
 
        User user1 = new User("U001", "Alice Smith");
        User user2 = new User("U002", "Bob Johnson");

        myLibrary.addUser(user1);
        myLibrary.addUser(user2);
        
        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();

        System.out.println("\n--- Simulating Library Operations ---");
     
        System.out.println("\n--- Attempting to Issue Books ---");
        myLibrary.issueBook("978-0743273565", "U001"); 
        myLibrary.issueBook("978-0451524935", "U002"); 
        myLibrary.issueBook("978-0743273565", "U001"); 
        myLibrary.issueBook("999-0000000000", "U001"); 
        myLibrary.issueBook("978-0061120084", "U999"); 
       
        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();
    
        System.out.println("\n--- Attempting to Return Books ---");
        myLibrary.returnBook("978-0743273565", "U001"); 
        myLibrary.returnBook("978-0451524935", "U001"); 
        myLibrary.returnBook("978-0061120084", "U002"); 
        myLibrary.returnBook("978-0451524935", "U002"); 

        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();
    }
}
=======
package Task3_Library;

public class LibraryApp {
    public static void main(String[] args) {
      
        Library myLibrary = new Library();

        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565");
        Book book2 = new Book("1984", "George Orwell", "978-0451524935");
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084");
        Book book4 = new Book("Pride and Prejudice", "Jane Austen", "978-0141439518");
  
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addBook(book3);
        myLibrary.addBook(book4);
 
        User user1 = new User("U001", "Alice Smith");
        User user2 = new User("U002", "Bob Johnson");

        myLibrary.addUser(user1);
        myLibrary.addUser(user2);
        
        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();

        System.out.println("\n--- Simulating Library Operations ---");
     
        System.out.println("\n--- Attempting to Issue Books ---");
        myLibrary.issueBook("978-0743273565", "U001"); 
        myLibrary.issueBook("978-0451524935", "U002"); 
        myLibrary.issueBook("978-0743273565", "U001"); 
        myLibrary.issueBook("999-0000000000", "U001"); 
        myLibrary.issueBook("978-0061120084", "U999"); 
       
        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();
    
        System.out.println("\n--- Attempting to Return Books ---");
        myLibrary.returnBook("978-0743273565", "U001"); 
        myLibrary.returnBook("978-0451524935", "U001"); 
        myLibrary.returnBook("978-0061120084", "U002"); 
        myLibrary.returnBook("978-0451524935", "U002"); 

        myLibrary.displayAllBooks();
        myLibrary.displayAllUsers();
    }
}
>>>>>>> e4d0fcb (Task 4 file is added)
