package com.library;

import com.library.library.Library;
import com.library.service.LibraryService;
import com.library.service.LibraryServiceImpl;
import com.library.service.LibraryService.BookResult;
import com.library.service.LibraryService.AuthorResult;
import com.library.service.LibraryService.MemberResult;

import java.util.List;
import java.util.Scanner;

/**
 * Main is the application entry point.
 *
 * Why it exists:
 *   It is the only place the JVM starts from. It wires the concrete
 *   objects together (Library -> LibraryServiceImpl) and presents the
 *   console menu. All user interaction lives here so the model and
 *   service classes stay free of UI concerns.
 *
 * Abstraction / Polymorphism in action:
 *   Main holds a reference typed as LibraryService (the interface), not
 *   LibraryServiceImpl. Any future implementation of the interface can
 *   be substituted without touching Main.
 */
public class Main {

    private final LibraryService service;
    private final Scanner scanner;

    public Main(LibraryService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("Library Management System");

        // Initialize the library application objects.
        Library library = new Library();
        LibraryService service = new LibraryServiceImpl(library);

        Main app = new Main(service);
        app.run();
    }

    private void run() {
        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            handleChoice(choice);
            if (choice == 16) {
                break;
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Application exited.");
    }

    private void printMenu() {
        System.out.println("===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("1.  Add Book");
        System.out.println("2.  View All Books");
        System.out.println("3.  Search Book by ID");
        System.out.println("4.  Search Book by Title");
        System.out.println("5.  Remove Book");
        System.out.println("6.  Add Author");
        System.out.println("7.  View All Authors");
        System.out.println("8.  View Books by Author");
        System.out.println("9.  Add Member");
        System.out.println("10. View All Members");
        System.out.println("11. Remove Member");
        System.out.println("12. Borrow Book");
        System.out.println("13. Return Book");
        System.out.println("14. View Borrowed Books");
        System.out.println("15. View Library Information");
        System.out.println("16. Exit");
    }

    private void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1:  addBook(); break;
                case 2:  service.displayAllBooks(); break;
                case 3:  searchBookById(); break;
                case 4:  searchBookByTitle(); break;
                case 5:  removeBook(); break;
                case 6:  addAuthor(); break;
                case 7:  service.displayAllAuthors(); break;
                case 8:  displayBooksByAuthor(); break;
                case 9:  addMember(); break;
                case 10: service.displayAllMembers(); break;
                case 11: removeMember(); break;
                case 12: borrowBook(); break;
                case 13: returnBook(); break;
                case 14: service.displayBorrowedBooks(); break;
                case 15: service.displayLibraryInformation(); break;
                case 16: break; // handled in run()
                default: System.out.println("Invalid choice. Please try again.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Book operations
    // -------------------------------------------------------------------------

    private void addBook() {
        String bookId = readNonEmptyLine("Enter Book ID (5 chars): ");
        String title = readNonEmptyLine("Enter Book Title: ");
        String authorId = readNonEmptyLine("Enter Author ID: ");
        service.addBook(bookId, title, authorId);
        System.out.println("Book added successfully.");
    }

    private void searchBookById() {
        String bookId = readNonEmptyLine("Enter Book ID: ");
        BookResult result = service.findBookById(bookId);
        if (result.found) {
            result.book.displayBookDetails();
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }
    }

    private void searchBookByTitle() {
        String title = readNonEmptyLine("Enter Book Title (or part of it): ");
        List<BookResult> results = service.searchBookByTitle(title);
        if (results.isEmpty()) {
            System.out.println("No books found matching: " + title);
            return;
        }
        System.out.println("----- SEARCH RESULTS (" + results.size() + ") -----");
        for (BookResult result : results) {
            result.book.displayBookDetails();
            System.out.println();
        }
    }

    private void removeBook() {
        String bookId = readNonEmptyLine("Enter Book ID to remove: ");
        service.removeBook(bookId);
        System.out.println("Book removed successfully.");
    }

    // -------------------------------------------------------------------------
    // Author operations
    // -------------------------------------------------------------------------

    private void addAuthor() {
        String authorId = readNonEmptyLine("Enter Author ID: ");
        String authorName = readNonEmptyLine("Enter Author Name: ");
        service.addAuthor(authorId, authorName);
        System.out.println("Author added successfully.");
    }

    private void displayBooksByAuthor() {
        String authorId = readNonEmptyLine("Enter Author ID: ");
        service.displayBooksByAuthor(authorId);
    }

    // -------------------------------------------------------------------------
    // Member operations
    // -------------------------------------------------------------------------

    private void addMember() {
        String memberId = readNonEmptyLine("Enter Member ID (6 chars): ");
        String memberName = readNonEmptyLine("Enter Member Name: ");
        service.addMember(memberId, memberName);
        System.out.println("Member added successfully.");
    }

    private void removeMember() {
        String memberId = readNonEmptyLine("Enter Member ID to remove: ");
        service.removeMember(memberId);
        System.out.println("Member removed successfully.");
    }

    // -------------------------------------------------------------------------
    // Borrowing operations
    // -------------------------------------------------------------------------

    private void borrowBook() {
        String memberId = readNonEmptyLine("Enter Member ID: ");
        String bookId = readNonEmptyLine("Enter Book ID: ");
        service.borrowBook(memberId, bookId);
        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() {
        String memberId = readNonEmptyLine("Enter Member ID: ");
        String bookId = readNonEmptyLine("Enter Book ID: ");
        service.returnBook(memberId, bookId);
        System.out.println("Book returned successfully.");
    }

    // -------------------------------------------------------------------------
    // Input helpers
    // -------------------------------------------------------------------------

    /**
     * Reads a menu choice as an integer.
     *
     * Why this helper exists:
     *   Using scanner.nextInt() leaves a newline in the buffer that the
     *   next scanner.nextLine() would consume, causing silent input
     *   skips. Reading full lines and parsing manually avoids that bug.
     */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}