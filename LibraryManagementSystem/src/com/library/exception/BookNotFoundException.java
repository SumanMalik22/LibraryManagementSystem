package com.library.exception;

/**
 * Thrown when a book operation references a book ID that does not exist.
 *
 * Why a separate subclass exists:
 *   The menu layer can catch this specific type and print a friendly
 *   "book not found" message instead of a generic error.
 */
public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(String bookId) {
        super("Book not found with ID: " + bookId);
    }
}