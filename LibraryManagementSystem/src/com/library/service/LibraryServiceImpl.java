package com.library.service;

import com.library.library.Library;
import com.library.model.Book;
import com.library.model.Author;
import com.library.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * LibraryServiceImpl is the concrete implementation of LibraryService.
 *
 * Why a separate implementation class:
 *   The interface declares the contract; this class provides the HOW.
 *   The menu layer depends on the interface (abstraction), and this class
 *   is the concrete object wired in at startup. Swapping it for another
 *   implementation requires no changes to Main.
 *
 * How it works:
 *   It holds a reference to a Library (composition) which owns all the
 *   data. Each use-case method resolves IDs to objects, delegates state
 *   mutation to the model objects, and handles display. It does not own
 *   any data itself.
 */
public class LibraryServiceImpl implements LibraryService {

    private final Library library;

    public LibraryServiceImpl(Library library) {
        this.library = library;
    }

    // -------------------------------------------------------------------------
    // Book management
    // -------------------------------------------------------------------------

    @Override
    public void addBook(String bookId, String title, String authorId) {
        library.addBook(bookId, title, authorId);
    }

    @Override
    public void removeBook(String bookId) {
        library.removeBook(bookId);
    }

    @Override
    public BookResult findBookById(String bookId) {
        Book book = library.findBookById(bookId);
        return new BookResult(book, book != null);
    }

    @Override
    public List<BookResult> searchBookByTitle(String title) {
        List<BookResult> results = new ArrayList<>();
        for (Book book : library.searchBookByTitle(title)) {
            results.add(new BookResult(book, true));
        }
        return results;
    }

    @Override
    public void displayAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the catalogue yet.");
            return;
        }
        System.out.println("----- ALL BOOKS (" + books.size() + ") -----");
        for (Book book : books) {
            book.displayBookDetails();
            System.out.println();
        }
    }

    // -------------------------------------------------------------------------
    // Author management
    // -------------------------------------------------------------------------

    @Override
    public void addAuthor(String authorId, String authorName) {
        library.addAuthor(authorId, authorName);
    }

    @Override
    public void removeAuthor(String authorId) {
        library.removeAuthor(authorId);
    }

    @Override
    public AuthorResult findAuthorById(String authorId) {
        Author author = library.findAuthorById(authorId);
        return new AuthorResult(author, author != null);
    }

    @Override
    public void displayAllAuthors() {
        List<Author> authors = library.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No authors registered yet.");
            return;
        }
        System.out.println("----- ALL AUTHORS (" + authors.size() + ") -----");
        for (Author author : authors) {
            author.displayAuthorDetails();
            System.out.println();
        }
    }

    @Override
    public void displayBooksByAuthor(String authorId) {
        AuthorResult result = findAuthorById(authorId);
        if (!result.found) {
            System.out.println("Author not found with ID: " + authorId);
            return;
        }
        List<Book> books = result.author.getAuthorBooks();
        System.out.println("----- BOOKS BY " + result.author.getAuthorName().toUpperCase() + " -----");
        if (books.isEmpty()) {
            System.out.println("  (no books registered for this author)");
        } else {
            for (Book book : books) {
                System.out.println("  - " + book.getTitle() + " [" + book.getBookId() + "]");
            }
        }
    }

    // -------------------------------------------------------------------------
    // Member management
    // -------------------------------------------------------------------------

    @Override
    public void addMember(String memberId, String memberName) {
        library.addMember(memberId, memberName);
    }

    @Override
    public void removeMember(String memberId) {
        library.removeMember(memberId);
    }

    @Override
    public MemberResult findMemberById(String memberId) {
        Member member = library.findMemberById(memberId);
        return new MemberResult(member, member != null);
    }

    @Override
    public void displayAllMembers() {
        List<Member> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
            return;
        }
        System.out.println("----- ALL MEMBERS (" + members.size() + ") -----");
        for (Member member : members) {
            member.displayMemberDetails();
            System.out.println();
        }
    }

    // -------------------------------------------------------------------------
    // Borrowing management
    // -------------------------------------------------------------------------

    @Override
    public void borrowBook(String memberId, String bookId) {
        Member member = library.findMemberByIdOrThrow(memberId);
        Book book = library.findBookByIdOrThrow(bookId);
        member.borrowBook(book);
    }

    @Override
    public void returnBook(String memberId, String bookId) {
        Member member = library.findMemberByIdOrThrow(memberId);
        Book book = library.findBookByIdOrThrow(bookId);
        member.returnBook(book);
    }

    @Override
    public void displayBorrowedBooks() {
        boolean any = false;
        for (Member member : library.getAllMembers()) {
            if (!member.getBorrowedBooks().isEmpty()) {
                any = true;
                System.out.println("----- " + member.getMemberName() + " [" + member.getMemberId() + "] -----");
                for (Book book : member.getBorrowedBooks()) {
                    System.out.println("  - " + book.getTitle() + " [" + book.getBookId() + "]");
                }
                System.out.println();
            }
        }
        if (!any) {
            System.out.println("No books are currently borrowed.");
        }
    }

    // -------------------------------------------------------------------------
    // Library information
    // -------------------------------------------------------------------------

    @Override
    public void displayLibraryInformation() {
        System.out.println("===== LIBRARY INFORMATION =====");
        System.out.println("Total Books   : " + library.getAllBooks().size());
        System.out.println("Total Authors : " + library.getAllAuthors().size());
        System.out.println("Total Members : " + library.getAllMembers().size());

        int borrowed = 0;
        for (Book book : library.getAllBooks()) {
            if (!book.isAvailable()) {
                borrowed++;
            }
        }
        System.out.println("Borrowed Books: " + borrowed);
        System.out.println("Available Books: " + (library.getAllBooks().size() - borrowed));
    }
}