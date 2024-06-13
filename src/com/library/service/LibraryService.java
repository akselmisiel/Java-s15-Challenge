package com.library.service;

import java.util.*;

import com.library.enums.Category;
import com.library.enums.Status;
import com.library.model.Book;
import com.library.model.Reader;
import com.library.model.Librarian;

public class LibraryService {
    private Set<Book> books;
    private Map<String, Reader> readers;
    private Map<String, Librarian> librarians;
    private static final double BORROW_FEE = 10.0;
    private static final double RETURN_REFUND_PERCENTAGE = 0.75;

    public LibraryService() {
        this.books = new HashSet<>();
        this.readers = new HashMap<>();
        this.librarians = new HashMap<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Book getBook(int bookID) {
        for (Book book : this.books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }

    public Book getBookById(int bookID) {
        for (Book book : this.books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }

    public Book getBookByTitle(String title) {
        for (Book book : this.books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getAuthor().getName().equalsIgnoreCase(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    public void updateBook(Book book) {
        if (this.books.contains(book)) {
            this.books.remove(book);
            this.books.add(book);
        }
    }

    public void deleteBook(int bookID) {
        this.books.removeIf(book -> book.getBookID() == bookID);
    }

    public List<Book> getBooksByCategory(Category category) {
        List<Book> categoryBooks = new ArrayList<>();
        for (Book book : this.books) {
            if (book.getCategory() == category) {
                categoryBooks.add(book);
            }
        }
        return categoryBooks;
    }

    public void addReader(Reader reader) {
        this.readers.put(reader.getName().toLowerCase(), reader);
    }

    public Reader getReaderByName(String name) {
        return this.readers.get(name);
    }

    public void addLibrarian(Librarian librarian) {
        this.librarians.put(librarian.getName().toLowerCase(), librarian);
    }

    public Librarian getLibrarianByName(String name) {
        return this.librarians.get(name);
    }

    public void lendBook(Book book, Reader reader) {
        if (reader.getBalance() >= BORROW_FEE) {
            if (book.borrowBook(reader)) {
                reader.deductBalance(BORROW_FEE);
            }
        } else {
            System.out.println("Insufficient balance to borrow the book.");
        }
    }

    public void takeBackBook(Book book, Reader reader) {
        if (book.returnBook(reader)) {
            double refundAmount = BORROW_FEE * RETURN_REFUND_PERCENTAGE;
            reader.addBalance(refundAmount);
            System.out.println(reader.getName() + " received a refund of $" + refundAmount);
        }
    }

    //should be sellBook
    public void purchaseBook(Book book, Reader reader) {
        book.purchaseBook(reader);
    }

    public void showBorrowedBooks(Reader reader) {
        System.out.println(reader.getName() + " has borrowed the following books:");
        reader.showBooks();
    }



}
