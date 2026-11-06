package com.library.library;

import com.library.exception.BookNotFoundException;
import com.library.exception.MemberNotFoundException;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Library is the domain aggregate that owns all the library's data.
 *
 * Responsibilities:
 *   - Hold the three core collections: books, authors, members.
 *   - Enforce uniqueness of IDs (no duplicate book/author/member IDs).
 *   - Enforce removal rules (cannot remove a borrowed book, an author
 *     who still has books, or a member who still has borrowed books).
 *   - Provide simple lookup helpers used by the service layer.
 *
 * Why it exists as a separate class instead of putting collections in
 * the service implementation:
 *   Library is the single source of truth for the library's state. The
 *   service layer orchestrates use cases (borrow, return, display) and
 *   delegates persistence and consistency to Library. This keeps the
 *   service thin and the data model cohesive.
 *
 * OOP concepts used:
 *   - Composition: Library "has" books, authors, and members. It owns
 *     their lifecycle; when the Library is gone, so are they.
 *   - Encapsulation: collections are private; access is through
 *     controlled methods, never exposed directly.
 */
public class Library {

    // bookId   -> Book
    private final Map<String, Book> books = new HashMap<>();
    // authorId -> Author
    private final Map<String, Author> authors = new HashMap<>();
    // memberId -> Member
    private final Map<String, Member> members = new HashMap<>();

    // -------------------------------------------------------------------------
    // Book management
    // -------------------------------------------------------------------------

    /**
     * Adds a book to the catalogue.
     *
     * Execution flow:
     *   1. Reject duplicate book IDs.
     *   2. Look up the author; throw if the author does not exist.
     *   3. Create the Book and register it in both directions:
     *      - the book is stored in the books map, and
     *      - the book is added to the author's collection (Author.addBook).
     */
    public void addBook(String bookId, String title, String authorId) {
        if (books.containsKey(bookId)) {
            throw new IllegalArgumentException("Book ID '" + bookId + "' already exists.");
        }
        Author author = findAuthorByIdOrThrow(authorId);
        Book book = new Book(bookId, title, author);
        books.put(bookId, book);
        author.addBook(book);
    }

    /**
     * Removes a book from the catalogue.
     *
     * Execution flow:
     *   1. Throw if the book does not exist.
     *   2. Throw if the book is currently borrowed (cannot remove).
     *   3. Remove the book from the author's collection as well, so both
     *      sides of the book <-> author association stay consistent.
     */
    public void removeBook(String bookId) {
        Book book = findBookByIdOrThrow(bookId);
        if (!book.isAvailable()) {
            throw new IllegalStateException(
                "Cannot remove book '" + bookId + "': it is currently borrowed.");
        }
        book.getAuthor().removeBook(book);
        books.remove(bookId);
    }

    public Book findBookById(String bookId) {
        return books.get(bookId);
    }

    public List<Book> searchBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String query = title.trim().toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // -------------------------------------------------------------------------
    // Author management
    // -------------------------------------------------------------------------

    public void addAuthor(String authorId, String authorName) {
        if (authors.containsKey(authorId)) {
            throw new IllegalArgumentException("Author ID '" + authorId + "' already exists.");
        }
        authors.put(authorId, new Author(authorId, authorName));
    }

    /**
     * Removes an author from the catalogue.
     *
     * Execution flow:
     *   1. Throw if the author does not exist.
     *   2. Throw if the author still has registered books (an author with
     *      books cannot be removed; the books must be removed first).
     */
    public void removeAuthor(String authorId) {
        Author author = findAuthorByIdOrThrow(authorId);
        if (!author.getAuthorBooks().isEmpty()) {
            throw new IllegalStateException(
                "Cannot remove author '" + authorId + "': they still have registered books.");
        }
        authors.remove(authorId);
    }

    public Author findAuthorById(String authorId) {
        return authors.get(authorId);
    }

    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    // -------------------------------------------------------------------------
    // Member management
    // -------------------------------------------------------------------------

    public void addMember(String memberId, String memberName) {
        if (members.containsKey(memberId)) {
            throw new IllegalArgumentException("Member ID '" + memberId + "' already exists.");
        }
        members.put(memberId, new Member(memberId, memberName));
    }

    /**
     * Removes a member from the catalogue.
     *
     * Execution flow:
     *   1. Throw if the member does not exist.
     *   2. Throw if the member still has borrowed books.
     */
    public void removeMember(String memberId) {
        Member member = findMemberByIdOrThrow(memberId);
        if (!member.getBorrowedBooks().isEmpty()) {
            throw new IllegalStateException(
                "Cannot remove member '" + memberId + "': they still have borrowed books.");
        }
        members.remove(memberId);
    }

    public Member findMemberById(String memberId) {
        return members.get(memberId);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    public Book findBookByIdOrThrow(String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        }
        return book;
    }

    public Author findAuthorByIdOrThrow(String authorId) {
        Author author = authors.get(authorId);
        if (author == null) {
            throw new IllegalArgumentException("Author not found with ID: " + authorId);
        }
        return author;
    }

    public Member findMemberByIdOrThrow(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        return member;
    }
}