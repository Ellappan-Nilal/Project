<<<<<<< HEAD
package Task3_Library;
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isIssued; 

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isIssued = false; 
    }

    
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return isIssued;
    }


    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public void displayBook() {
        System.out.println("Title: " + title +
                           ", Author: " + author +
                           ", ISBN: " + isbn +
                           ", Status: " + (isIssued ? "Issued" : "Available"));
    }
=======
package Task3_Library;
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isIssued; 

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isIssued = false; 
    }

    
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return isIssued;
    }


    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public void displayBook() {
        System.out.println("Title: " + title +
                           ", Author: " + author +
                           ", ISBN: " + isbn +
                           ", Status: " + (isIssued ? "Issued" : "Available"));
    }
>>>>>>> 7c1143f (files are added)
}