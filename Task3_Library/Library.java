<<<<<<< HEAD
package Task3_Library;

import java.util.ArrayList;
import java.util.List;


class Library {
    private List<Book> books; 
    private List<User> users; 

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    
    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getName());
    }

   
    public Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null; 
    }

    
    public User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // User not found
    }

    
    public void issueBook(String isbn, String userId) {
        Book book = findBook(isbn);
        User user = findUser(userId);

        if (book == null) {
            System.out.println("Error: Book with ISBN " + isbn + " not found.");
            return;
        }
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is already issued.");
            return;
        }

        book.setIssued(true); 
        user.borrowBook(book); 
        System.out.println("Success: Book '" + book.getTitle() + "' issued to " + user.getName() + ".");
    }

   
    public void returnBook(String isbn, String userId) {
        Book book = findBook(isbn);
        User user = findUser(userId);

        if (book == null) {
            System.out.println("Error: Book with ISBN " + isbn + " not found.");
            return;
        }
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return;
        }
        if (!book.isIssued()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is not currently issued.");
            return;
        }
        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println("Error: User '" + user.getName() + "' did not borrow the book '" + book.getTitle() + "'.");
            return;
        }

        book.setIssued(false); 
        user.returnBook(book); 
        System.out.println("Success: Book '" + book.getTitle() + "' returned by " + user.getName() + ".");
    }

   
    public void displayAllBooks() {
        System.out.println("\n--- All Books in Library ---");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        for (Book book : books) {
            book.displayBook();
        }
    }

    
    public void displayAllUsers() {
        System.out.println("\n--- All Users in Library ---");
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }
        for (User user : users) {
            user.displayUser();
        }
    }
}
=======
package Task3_Library;

import java.util.ArrayList;
import java.util.List;


class Library {
    private List<Book> books; 
    private List<User> users; 

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    
    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getName());
    }

   
    public Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null; 
    }

    
    public User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // User not found
    }

    
    public void issueBook(String isbn, String userId) {
        Book book = findBook(isbn);
        User user = findUser(userId);

        if (book == null) {
            System.out.println("Error: Book with ISBN " + isbn + " not found.");
            return;
        }
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is already issued.");
            return;
        }

        book.setIssued(true); 
        user.borrowBook(book); 
        System.out.println("Success: Book '" + book.getTitle() + "' issued to " + user.getName() + ".");
    }

   
    public void returnBook(String isbn, String userId) {
        Book book = findBook(isbn);
        User user = findUser(userId);

        if (book == null) {
            System.out.println("Error: Book with ISBN " + isbn + " not found.");
            return;
        }
        if (user == null) {
            System.out.println("Error: User with ID " + userId + " not found.");
            return;
        }
        if (!book.isIssued()) {
            System.out.println("Error: Book '" + book.getTitle() + "' is not currently issued.");
            return;
        }
        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println("Error: User '" + user.getName() + "' did not borrow the book '" + book.getTitle() + "'.");
            return;
        }

        book.setIssued(false); 
        user.returnBook(book); 
        System.out.println("Success: Book '" + book.getTitle() + "' returned by " + user.getName() + ".");
    }

   
    public void displayAllBooks() {
        System.out.println("\n--- All Books in Library ---");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        for (Book book : books) {
            book.displayBook();
        }
    }

    
    public void displayAllUsers() {
        System.out.println("\n--- All Users in Library ---");
        if (users.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }
        for (User user : users) {
            user.displayUser();
        }
    }
}
>>>>>>> e4d0fcb (Task 4 file is added)
