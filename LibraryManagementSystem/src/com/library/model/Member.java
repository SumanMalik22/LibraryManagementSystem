package com.library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Member represents a library patron who can borrow books.
 *
 * Responsibilities:
 *   - Hold the member's identity (ID and name).
 *   - Keep a collection of currently borrowed books.
 *   - Enforce the borrowing limit and validate borrow/return rules.
 *
 * OOP concepts used:
 *   - Encapsulation: private fields, validated setters, controlled access.
 *   - Association: Member has a collection of Book objects it has borrowed.
 *   - Polymorphism (via interface): LibraryService operations act on
 *     Member objects uniformly.
 */
public class Member {

    public static final int MEMBER_ID_LENGTH = 6;
    public static final int MAX_BORROWED_BOOKS = 3;

    private String memberId;
    private String memberName;
    private List<Book> borrowedBooks;

    public Member(String memberId, String memberName) {
        setMemberId(memberId);
        setMemberName(memberName);
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public List<Book> getBorrowedBooks() {
        // Return a copy so callers cannot mutate the internal list directly.
        return new ArrayList<>(borrowedBooks);
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID must not be empty.");
        }
        if (memberId.trim().length() != MEMBER_ID_LENGTH) {
            throw new IllegalArgumentException(
                "Member ID must be exactly " + MEMBER_ID_LENGTH + " characters.");
        }
        this.memberId = memberId.trim();
    }

    public void setMemberName(String memberName) {
        if (memberName == null || memberName.trim().isEmpty()) {
            throw new IllegalArgumentException("Member name must not be empty.");
        }
        this.memberName = memberName.trim();
    }

    /**
     * Borrows a book on behalf of this member.
     *
     * Execution flow:
     *   1. Guard against null book.
     *   2. Throw if the member already holds the maximum number of books.
     *   3. Throw if the book is not currently available.
     *   4. Add the book to the member's borrowed list.
     *   5. Ask the book to mark itself as borrowed (which flips its state).
     *
     * Why the book's own borrowBook() is called:
     *   The book owns its availability state, so the mutation happens there.
     *   The member owns the "who has it" state, so the book is added there.
     *   Both sides stay consistent because both are updated in one method.
     */
    public void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS) {
            throw new IllegalStateException(
                memberName + " has reached the maximum of " + MAX_BORROWED_BOOKS + " borrowed books.");
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is not available.");
        }
        borrowedBooks.add(book);
        book.borrowBook();
    }

    /**
     * Returns a book previously borrowed by this member.
     *
     * Execution flow:
     *   1. Guard against null book.
     *   2. Throw if the member has not borrowed this book.
     *   3. Remove the book from the borrowed list.
     *   4. Ask the book to mark itself as returned.
     */
    public void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (!borrowedBooks.contains(book)) {
            throw new IllegalStateException(
                memberName + " has not borrowed '" + book.getTitle() + "'.");
        }
        borrowedBooks.remove(book);
        book.returnBook();
    }

    public boolean hasBorrowed(Book book) {
        return book != null && borrowedBooks.contains(book);
    }

    public void displayMemberDetails() {
        System.out.println("Member ID   : " + memberId);
        System.out.println("Member Name : " + memberName);
        System.out.println("Borrowed books (" + borrowedBooks.size() + "/" + MAX_BORROWED_BOOKS + "):");
        if (borrowedBooks.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println("  - " + book.getTitle() + " [" + book.getBookId() + "]");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member other = (Member) o;
        return memberId != null && memberId.equals(other.memberId);
    }

    @Override
    public int hashCode() {
        return memberId == null ? 0 : memberId.hashCode();
    }
}