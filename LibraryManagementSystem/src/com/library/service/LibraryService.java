package com.library.service;

import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Member;

/**
 * LibraryService defines the application's use cases as a contract.
 *
 * Why an interface is used here:
 *   It separates WHAT the application can do (the contract) from HOW
 *   it is done (the implementation). The menu layer (Main) only needs to
 *   know about LibraryService, never the concrete LibraryServiceImpl.
 *
 * How abstraction works:
 *   The caller works against the abstract type. The actual object can be
 *   swapped (e.g. a future in-memory vs. file-backed implementation)
 *   without changing any caller, because they all depend on this contract.
 *
 * How polymorphism is demonstrated:
 *   Main holds a reference of type LibraryService. At runtime that
 *   reference points to a LibraryServiceImpl, and the correct method is
 *   resolved dynamically. Any future class implementing LibraryService
 *   can be plugged in without touching Main.
 */
public interface LibraryService {

    // ---- Book management ----
    void addBook(String bookId, String title, String authorId);
    void removeBook(String bookId);
    BookResult findBookById(String bookId);
    java.util.List<BookResult> searchBookByTitle(String title);
    void displayAllBooks();

    // ---- Author management ----
    void addAuthor(String authorId, String authorName);
    void removeAuthor(String authorId);
    AuthorResult findAuthorById(String authorId);
    void displayAllAuthors();
    void displayBooksByAuthor(String authorId);

    // ---- Member management ----
    void addMember(String memberId, String memberName);
    void removeMember(String memberId);
    MemberResult findMemberById(String memberId);
    void displayAllMembers();

    // ---- Borrowing management ----
    void borrowBook(String memberId, String bookId);
    void returnBook(String memberId, String bookId);
    void displayBorrowedBooks();

    // ---- Library information ----
    void displayLibraryInformation();

    // -------------------------------------------------------------------------
    // Data-transfer objects. They are simple, immutable value holders used
    // to return data from the service to the menu layer without exposing
    // the internal model objects directly.
    // -------------------------------------------------------------------------

    /** Result of looking up a book by ID. */
    class BookResult {
        public final Book book;
        public final boolean found;
        public BookResult(Book book, boolean found) {
            this.book = book;
            this.found = found;
        }
    }

    /** Result of looking up an author by ID. */
    class AuthorResult {
        public final Author author;
        public final boolean found;
        public AuthorResult(Author author, boolean found) {
            this.author = author;
            this.found = found;
        }
    }

    /** Result of looking up a member by ID. */
    class MemberResult {
        public final Member member;
        public final boolean found;
        public MemberResult(Member member, boolean found) {
            this.member = member;
            this.found = found;
        }
    }
}