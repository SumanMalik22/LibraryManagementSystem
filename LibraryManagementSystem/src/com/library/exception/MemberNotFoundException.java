package com.library.exception;

/**
 * Thrown when a member operation references a member ID that does not exist.
 *
 * Why a separate subclass exists:
 *   The menu layer can catch this specific type and print a friendly
 *   "member not found" message instead of a generic error.
 */
public class MemberNotFoundException extends LibraryException {

    public MemberNotFoundException(String memberId) {
        super("Member not found with ID: " + memberId);
    }
}