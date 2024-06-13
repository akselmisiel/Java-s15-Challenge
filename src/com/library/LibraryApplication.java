package com.library;

import com.library.enums.Category;
import com.library.model.*;
import com.library.service.*;
import com.library.utility.ValidationUtil;

import java.util.*;


public class LibraryApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService libraryService = new LibraryService();

        initializeLibrary(libraryService);

        System.out.println("Are you logging in as a reader [1] or librarian [2]?");
        int userType = scanner.nextInt();
        scanner.nextLine();

        Person currentUser = null;

        while (currentUser == null) {
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            if (!ValidationUtil.isValidString(name)) {
                System.out.println("Invalid name. Please try again.");
                continue;
            }
            if (userType == 1) {
                currentUser = libraryService.getReaderByName(name.toLowerCase());
                if (currentUser == null) {
                    System.out.println("No reader found with that name. Do you want to register as a new reader? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        System.out.println("Enter starting balance:");
                        double balance = scanner.nextDouble();
                        scanner.nextLine();
                        currentUser = new Reader(name.toLowerCase(), balance);
                        libraryService.addReader((Reader) currentUser);
                        System.out.println("Reader registered successfully!");
                    } else {
                        System.out.println("Please enter a valid name.");
                    }

                } else {
                    System.out.println("Welcome back, " + currentUser.getName() + "!");
                }
            } else if (userType == 2) {
                currentUser = libraryService.getLibrarianByName(name.toLowerCase());
                if (currentUser == null) {
                    System.out.println("No librarian found with that name. Do you want to register as a new librarian? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        currentUser = new Librarian(name);
                        libraryService.addLibrarian((Librarian) currentUser);
                        System.out.println("Librarian registered successfully!");
                    } else {
                        System.out.println("Please enter a valid name.");
                    }
                } else {
                    System.out.println("Welcome back, " + currentUser.getName() + "!");
                }
            }

        }

        boolean isLoggedIn = true;

        while (isLoggedIn) {
            System.out.println("Library Management System");
            System.out.printf("------------------------------\n");
            if (currentUser instanceof Reader) {
                System.out.println("Balance: " + ((Reader) currentUser).getBalance());
            }
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. List Books by Category");
            System.out.println("6. List Books by Author");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. Purchase Book");
            System.out.println("10. Show Borrowed Books");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Adding a book
                    if (currentUser instanceof Librarian) {
                        System.out.println("Enter book ID: ");
                        int bookID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(bookID) || libraryService.getBookById(bookID) != null) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        System.out.println("Enter author name: ");
                        String authorName = scanner.nextLine();
                        if (!ValidationUtil.isValidString(authorName)) {
                            System.out.println("Invalid author name. Please try again.");
                            break;
                        }
                        Author author = new Author(authorName);
                        System.out.println("Enter book title: ");
                        String title = scanner.nextLine();
                        if (!ValidationUtil.isValidString(title)) {
                            System.out.println("Invalid title. Please try again.");
                            break;
                        }
                        System.out.println("Enter book price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidPrice(price)) {
                            System.out.println("Invalid price. Please try again.");
                            break;
                        }
                        System.out.println("Enter book category (FICTION/NON_FICTION/HISTORY/FANTASY): ");
                        String category = scanner.nextLine();
                        if (!ValidationUtil.isValidCategory(category)) {
                            System.out.println("Invalid category. Please try again.");
                            break;
                        }
                        Category bookCategory = Category.valueOf(category.toUpperCase());
                        Book book = new Book(bookID, author, title, price, bookCategory);
                        libraryService.addBook(book);
                        System.out.println("Book added successfully!");
                    } else {
                        System.out.println("Only librarians can add books.");
                    }
                    break;
                case 2:
                    // Searching a book
                    System.out.println("Search by: 1. ID 2. Title 3. Author");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();
                    Book bookFound = null;
                    switch (searchChoice) {
                        case 1:
                            System.out.println("Enter book ID: ");
                            int searchID = scanner.nextInt();
                            scanner.nextLine();
                            if (!ValidationUtil.isValidId(searchID)) {
                                System.out.println("Invalid book ID. Please try again.");
                                break;
                            }
                            bookFound = libraryService.getBookById(searchID);
                            break;
                        case 2:
                            System.out.println("Enter book title: ");
                            String searchTitle = scanner.nextLine();
                            if (!ValidationUtil.isValidString(searchTitle)) {
                                System.out.println("Invalid title. Please try again.");
                                break;
                            }
                            bookFound = libraryService.getBookByTitle(searchTitle);
                            break;
                        case 3:
                            System.out.println("Enter author name: ");
                            String searchAuthor = scanner.nextLine();
                            if (!ValidationUtil.isValidString(searchAuthor)) {
                                System.out.println("Invalid author name. Please try again.");
                                break;
                            }
                            List<Book> booksByAuthor = libraryService.getBooksByAuthor(searchAuthor);
                            if (booksByAuthor.isEmpty())
                                System.out.println("No books found by " + searchAuthor);
                            else {
                                System.out.println("Books by " + searchAuthor + ":");
                                for (Book book : booksByAuthor) {
                                    System.out.println(book);
                                }
                            }
                            continue;
                    }
                    if (bookFound == null) {
                        System.out.println("Book not found.");
                    } else {
                        System.out.println("Book found: " + bookFound);
                    }
                    break;
                case 3:
                    // Updating a book
                    if (currentUser instanceof Librarian) {
                        System.out.println("Enter book ID: ");
                        int updateID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(updateID)) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        Book updatedBook = libraryService.getBookById(updateID);
                        if (updatedBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            System.out.println("Enter new title: ");
                            String newTitle = scanner.nextLine();
                            if (ValidationUtil.isValidString(newTitle)) {
                                updatedBook.setTitle(newTitle);
                            }

                            System.out.println("Enter new price: ");
                            double newPrice = scanner.nextDouble();
                            scanner.nextLine();
                            if (ValidationUtil.isValidPrice(newPrice)) {
                                updatedBook.setPrice(newPrice);
                            }
                            libraryService.updateBook(updatedBook);
                        }

                    } else {
                        System.out.println("Only librarians can update books.");
                    }
                    break;
                case 4:
                    // Deleting a book
                    if (currentUser instanceof Librarian) {
                        System.out.println("Enter book ID: ");
                        int deleteID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(deleteID)) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        libraryService.deleteBook(deleteID);
                        System.out.println("Book deleted successfully!");
                    } else {
                        System.out.println("Only librarians can delete books.");
                    }
                    break;
                case 5:
                    // Listing books by category
                    System.out.println("Enter category (FICTION/NON_FICTION/HISTORY/FANTASY): ");
                    String category = scanner.nextLine();
                    if (!ValidationUtil.isValidCategory(category)) {
                        System.out.println("Invalid category. Please try again.");
                        break;
                    }
                    Category bookCategory = Category.valueOf(category.toUpperCase());
                    List<Book> categoryBooks = libraryService.getBooksByCategory(bookCategory);
                    if (categoryBooks.isEmpty())
                        System.out.println("No books found in " + bookCategory);
                    else {
                        System.out.println("Books in " + bookCategory + ":");
                        for (Book book : categoryBooks) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 6:
                    // Listing books by author
                    System.out.println("Enter author name: ");
                    String authorName = scanner.nextLine();
                    if (!ValidationUtil.isValidString(authorName)) {
                        System.out.println("Invalid author name. Please try again.");
                        break;
                    }
                    List<Book> booksByAuthor = libraryService.getBooksByAuthor(authorName);
                    if (booksByAuthor.isEmpty())
                        System.out.println("No books found by " + authorName);
                    else {
                        System.out.println("Books by " + authorName + ":");
                        for (Book book : booksByAuthor) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 7:
                    // Borrowing a book
                    if (currentUser instanceof Reader) {

                        System.out.println("Enter book ID: ");
                        int borrowID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(borrowID)) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        Book borrowBook = libraryService.getBookById(borrowID);
                        if (borrowBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            libraryService.lendBook(borrowBook, (Reader) currentUser);
                        }

                    } else {
                        System.out.println("Only readers can borrow books.");
                    }
                    break;
                case 8:
                    // Returning a book
                    if (currentUser instanceof Reader) {
                        System.out.println("Enter book ID: ");
                        int returnID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(returnID)) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        Book returnBook = libraryService.getBookById(returnID);
                        if (returnBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            libraryService.takeBackBook(returnBook, (Reader) currentUser);
                        }

                    } else {
                        System.out.println("Only readers can return books.");
                    }
                    break;
                case 9:
                    if (currentUser instanceof Reader) {
                        System.out.println("Enter book ID: ");
                        int purchaseID = scanner.nextInt();
                        scanner.nextLine();
                        if (!ValidationUtil.isValidId(purchaseID)) {
                            System.out.println("Invalid book ID. Please try again.");
                            break;
                        }
                        Book purchaseBook = libraryService.getBookById(purchaseID);
                        if (purchaseBook == null) {
                            System.out.println("Book not found.");
                        } else {
                            libraryService.purchaseBook(purchaseBook, (Reader) currentUser);
                        }
                    } else {
                        System.out.println("Only readers can purchase books.");
                    }
                    break;
                case 10:
                    // Show borrowed books
                    if (currentUser instanceof Reader) {
                        libraryService.showBorrowedBooks((Reader) currentUser);
                    } else {
                        System.out.println("Only readers can view borrowed books.");
                    }
                    break;
                case 11:
                    System.out.println("Exiting...");
                    isLoggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeLibrary(LibraryService libraryService) {
        // Initialize some books (10)
        Book book1 = new Book(1, new Author("George Orwell"), "1984", 15.0 ,Category.FICTION);
        Book book2 = new Book(2, new Author("J.K. Rowling"), "Harry Potter and the Philosopher's Stone", 20.0, Category.FANTASY);
        Book book3 = new Book(3, new Author("J.R.R. Tolkien"), "The Hobbit", 22.0, Category.FANTASY);
        Book book4 = new Book(4, new Author("Anton Çehov"), "Altıncı Koğuş", 30.0, Category.FICTION);
        Book book5 = new Book(5, new Author("Stefan Zweig"), "Chess", 10.0, Category.FICTION);
        Book book6 = new Book(6, new Author("Yuval Noah Harari"), "Sapiens", 30.0, Category.HISTORY);
        Book book7 = new Book(7, new Author("Yuval Noah Harari"), "21 Lessons for the 21st Century", 30.0, Category.NON_FICTION);
        Book book8 = new Book(8, new Author("Stefan Zweig"), "Fear", 10.0, Category.NON_FICTION);
        Book book9 = new Book(9, new Author("Arthur Conan Doyle"), "Sherlock: A Study in Pink", 20.0, Category.FICTION);
        Book book10 = new Book(10, new Author("J.K. Rowling"), "Harry Potter and the Chamber of Secrets", 20.0, Category.FANTASY);

        libraryService.addBook(book1);
        libraryService.addBook(book2);
        libraryService.addBook(book3);
        libraryService.addBook(book4);
        libraryService.addBook(book5);
        libraryService.addBook(book6);
        libraryService.addBook(book7);
        libraryService.addBook(book8);
        libraryService.addBook(book9);
        libraryService.addBook(book10);

        Reader reader1 = new Reader("Aksel", 100);
        Reader reader2 = new Reader("mehmet", 45);
        libraryService.addReader(reader1);
        libraryService.addReader(reader2);

        Librarian librarian1 = new Librarian("Doğancan");
        libraryService.addLibrarian(librarian1);
    }
}
