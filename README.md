# 📚 Library Management System

A console-based Library Management System developed using **Core Java and Object-Oriented Programming (OOP)**. This project provides functionality to manage books, authors, and library members, along with book borrowing and returning operations.

The project focuses on applying real-world OOP concepts, writing clean and maintainable Java code, and implementing practical library management operations.

## 🚀 Features

### 📖 Book Management
- Add new books to the library.
- View all available books.
- Search books by Book ID.
- Search books by title.
- Remove books from the catalogue.
- Track book availability status.

### ✍️ Author Management
- Register new authors.
- View all registered authors.
- Search authors by Author ID.
- Display books written by a particular author.
- Associate multiple books with a single author.

### 👥 Member Management
- Register new library members.
- View all registered members.
- Search members by Member ID.
- Remove members who have no borrowed books.
- Track books borrowed by each member.

### 🔄 Borrowing & Returning
- Borrow available books.
- Return previously borrowed books.
- Allow a maximum of 3 books per member.
- Prevent borrowing unavailable books.
- Validate book and member records.
- Maintain book availability status.

### 🛡️ Validation & Error Handling
- Prevent duplicate Book IDs, Author IDs, and Member IDs.
- Validate Book ID length (5 characters).
- Validate Member ID length (6 characters).
- Handle invalid user input.
- Prevent removal of borrowed books.
- Prevent removal of members with outstanding borrowed books.
- Provide meaningful error messages.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| OOP | Application architecture |
| ArrayList | Managing collections of objects |
| Collections Framework | Data management |
| Scanner | Console input |
| Exception Handling | Error management |
| IntelliJ IDEA / VS Code | Development environment |

## 🏗️ Project Structure

```text
LibraryManagementSystem/
│
└── src/
    └── com/
        └── library/
            │
            ├── model/
            │   ├── Book.java
            │   ├── Author.java
            │   └── Member.java
            │
            ├── service/
            │   ├── LibraryService.java
            │   └── LibraryServiceImpl.java
            │
            ├── library/
            │   └── Library.java
            │
            ├── exception/
            │   ├── BookNotFoundException.java
            │   ├── MemberNotFoundException.java
            │   └── LibraryException.java
            │
            └── Main.java
```

*Note: The structure above represents the intended application architecture. Adjust filenames to match the final implementation.*

## 🧠 Object-Oriented Programming Concepts

This project demonstrates practical applications of Java OOP concepts.

### 1. Encapsulation
- Uses private fields to protect object data.
- Provides controlled access through getters and methods.
- Validates data before modifying object state.

### 2. Abstraction
- Uses service interfaces to define application operations.
- Hides implementation details from the application entry point.

### 3. Polymorphism
- Uses interface-based programming and method implementations.
- Allows application logic to work with service abstractions.

### 4. Inheritance
- Uses inheritance where there is a meaningful relationship, such as custom exceptions extending Java exception classes.

### 5. Composition & Association
- A Library manages books, authors, and members.
- A Book is associated with an Author.
- A Member maintains a collection of borrowed Books.

## ⚙️ Getting Started

### Prerequisites

- Java Development Kit (JDK).
- IntelliJ IDEA or Visual Studio Code.
- Basic understanding of Java.

### Installation

**1. Clone the repository**

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

**2. Navigate to the project directory**

```bash
cd LibraryManagementSystem
```

**3. Open the project**

Open the project in IntelliJ IDEA or VS Code and ensure the `src` directory is configured as a Java source folder.

**4. Run the application**

Run `Main.java` from your IDE.

Alternatively, compile and execute from the appropriate source directory using your configured Java package structure.

## 🖥️ Application Menu

The application provides an interactive console menu:

```text
===== LIBRARY MANAGEMENT SYSTEM =====

1.  Add Book
2.  View All Books
3.  Search Book by ID
4.  Search Book by Title
5.  Remove Book
6.  Add Author
7.  View All Authors
8.  View Books by Author
9.  Add Member
10. View All Members
11. Remove Member
12. Borrow Book
13. Return Book
14. View Borrowed Books
15. View Library Information
16. Exit
```

Users can select an operation by entering the corresponding menu number.

## 📋 Business Rules

| Rule | Description |
|---|---|
| Book ID | Must contain exactly 5 characters |
| Member ID | Must contain exactly 6 characters |
| Borrowing Limit | Maximum 3 books per member |
| Book Availability | A borrowed book cannot be borrowed again |
| Book Removal | Borrowed books cannot be removed |
| Member Removal | Members with borrowed books cannot be removed |
| Unique Records | Duplicate IDs are not permitted |

## 💾 Data Storage

The current version uses Java collections to manage application data in memory.

No external database is required. Data stored in memory is not permanently saved and will be reset when the application restarts.

## 🔮 Future Improvements

Potential enhancements for future versions:

- Integrate MySQL using JDBC for permanent data storage.
- Implement member login and authentication.
- Add book issue dates and return deadlines.
- Implement overdue book tracking and fine calculation.
- Add a graphical user interface.
- Develop a REST API using Spring Boot.
- Add automated unit testing using JUnit.

## 🎯 Project Objective

This project was developed as a practical learning exercise to strengthen my understanding of Core Java, Object-Oriented Programming, Collections Framework, exception handling, and application architecture.

It demonstrates how Java concepts can be combined to develop a structured, functional, and maintainable application.

## 👨‍💻 Author

**Suman Malik**

B.Sc. Information Technology Graduate | Aspiring Java Developer

- LinkedIn: [Connect with me](https://www.linkedin.com/in/sumanmalik221)
- GitHub: [Explore my repositories](https://github.com/SumanMalik22)

---

⭐ If you find this project useful, consider giving it a star!
