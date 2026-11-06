package com.library.exception;

/**
 * Base exception for all library-related errors.
 *
 * Why it exists:
 *   It gives the application a single, consistent exception type to catch
 *   at the menu layer instead of scattering unrelated RuntimeExceptions
 *   around the code base.
 *
 * It is a RuntimeException (unchecked) so the service interface does not
 * have to declare "throws", which keeps a console app's error handling
 * simple: the menu catches it and prints the message.
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}