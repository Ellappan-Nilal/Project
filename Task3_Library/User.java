package Task3_Library;

import java.util.ArrayList;
import java.util.List;

class User {
    private String userId;
    private String name;
    private List<Book> borrowedBooks; 

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>(); 
    }

   
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    
    public void borrowBook(Book book) {
        if (book != null) {
            borrowedBooks.add(book);
        }
    }

   
    public void returnBook(Book book) {
        if (book != null) {
            borrowedBooks.remove(book);
        }
    }

   
    public void displayUser() {
        System.out.println("User ID: " + userId + ", Name: " + name);
        if (borrowedBooks.isEmpty()) {
            System.out.println("  No books currently borrowed.");
        } else {
            System.out.println("  Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println("    - " + book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getIsbn() + ")");
            }
        }
    }
}