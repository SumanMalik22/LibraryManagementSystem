package com.library.model;

/**
 * Book represents a single title in the library's catalogue.
 *
 * Responsibilities:
 *   - Hold the book's identity, title, and availability status.
 *   - Reference the Author who wrote it (an Author object, not a String).
 *   - Track whether the book is currently borrowed.
 *   - Expose controlled borrow/return behaviour.
 *
 * OOP concepts used:
 *   - Encapsulation: all fields private, validated in constructor/setters.
 *   - Association: Book holds a reference to an Author object.
 *   - Polymorphism (via interface): LibraryService operations act on Book
 *     objects uniformly regardless of their concrete state.
 */
public class Book {

    public static final int BOOK_ID_LENGTH = 5;

    private String bookId;
    private String title;
    private Author author;
    private boolean available;

    public Book(String bookId, String title, Author author) {
        setBookId(bookId);
        setTitle(title);
        setAuthor(author);
        // A newly added book is always available until borrowed.
        this.available = true;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setBookId(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID must not be empty.");
        }
        if (bookId.trim().length() != BOOK_ID_LENGTH) {
            throw new IllegalArgumentException(
                "Book ID must be exactly " + BOOK_ID_LENGTH + " characters.");
        }
        this.bookId = bookId.trim();
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title must not be empty.");
        }
        this.title = title.trim();
    }

    public void setAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Book must have an author.");
        }
        this.author = author;
    }

    /**
     * Marks this book as borrowed.
     *
     * Execution flow:
     *   1. Throw if the book is already borrowed (prevents double-borrow).
     *   2. Flip the availability flag to false.
     *
     * Why this method exists on the model:
     *   The book "knows" its own state, so the library service only has to
     *   ask the book to borrow itself. This keeps state mutation close to
     *   the object that owns the state.
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book '" + title + "' is already borrowed.");
        }
        available = false;
    }

    /**
     * Marks this book as returned.
     *
     * Execution flow:
     *   1. Throw if the book is already available (prevents double-return).
     *   2. Flip the availability flag back to true.
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book '" + title + "' is already available.");
        }
        available = true;
    }

    public void displayBookDetails() {
        System.out.println("Book ID     : " + bookId);
        System.out.println("Title       : " + title);
        System.out.println("Author      : " + author.getAuthorName() + " [" + author.getAuthorId() + "]");
        System.out.println("Available   : " + (available ? "Yes" : "No (borrowed)"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return bookId != null && bookId.equals(other.bookId);
    }

    @Override
    public int hashCode() {
        return bookId == null ? 0 : bookId.hashCode();
    }
}