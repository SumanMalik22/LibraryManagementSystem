package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author represents a person who writes books.
 *
 * Responsibilities:
 *   - Hold the author's identity (ID and name).
 *   - Keep a collection of books written by this author.
 *   - Provide controlled access to that collection.
 *
 * Why a separate class instead of a String field on Book:
 *   An author is a first-class entity with its own identity and a
 *   collection of books. Storing only a name would lose the reverse
 *   relationship (author -> their books) and would duplicate author
 *   data on every book.
 *
 * OOP concepts used:
 *   - Encapsulation: private fields, getters, validation in setters.
 *   - Association: Author has a collection of Books (a many-to-many
 *     link managed consistently on both sides).
 */
public class Author {

    private String authorId;
    private String authorName;
    private List<Book> books;

    public Author(String authorId, String authorName) {
        setAuthorId(authorId);
        setAuthorName(authorName);
        this.books = new ArrayList<>();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public List<Book> getAuthorBooks() {
        // Return a copy so callers cannot mutate the internal list directly.
        return new ArrayList<>(books);
    }

    public void setAuthorId(String authorId) {
        if (authorId == null || authorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Author ID must not be empty.");
        }
        this.authorId = authorId.trim();
    }

    public void setAuthorName(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name must not be empty.");
        }
        this.authorName = authorName.trim();
    }

    /**
     * Registers a book as written by this author.
     *
     * Execution flow:
     *   1. Guard against null book.
     *   2. Add the book only if it is not already present (avoid duplicates).
     *
     * Why this method exists:
     *   It keeps the author -> book relationship consistent with the
     *   book -> author relationship maintained by Library.addBook().
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    /**
     * Removes a book from this author's collection.
     *
     * Execution flow:
     *   1. Guard against null book.
     *   2. Remove the book if present; silently ignore if absent so the
     *      library can call this safely during cleanup.
     */
    public void removeBook(Book book) {
        if (book == null) {
            return;
        }
        books.remove(book);
    }

    public void displayAuthorDetails() {
        System.out.println("Author ID  : " + authorId);
        System.out.println("Author Name: " + authorName);
        System.out.println("Books written: " + books.size());
        if (books.isEmpty()) {
            System.out.println("  (no books registered)");
        } else {
            for (Book book : books) {
                System.out.println("  - " + book.getTitle() + " [" + book.getBookId() + "]");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author other = (Author) o;
        return authorId != null && authorId.equals(other.authorId);
    }

    @Override
    public int hashCode() {
        return authorId == null ? 0 : authorId.hashCode();
    }
}