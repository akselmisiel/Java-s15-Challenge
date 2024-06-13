package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person{
    private double balance;
    private List<Book> books;
    private List<Book> purhasedBooks = new ArrayList<>();
    private static final int BOOK_LIMIT = 5;

    public Reader(String name, double balance) {
        super(name);
        this.balance = balance;
        this.books = new ArrayList<Book>();
    }

    public int getBookLimit() {
        return BOOK_LIMIT;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void showBooks() {
        for (Book book : this.books) {
            System.out.println("-> " + book.getTitle());
        }
    }

    public String getName() {
        return super.getName();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public boolean canBorrowMoreBooks() {
        return books.size() < BOOK_LIMIT;
    }

    @Override
    public String whoYouAre() {
        return "Reader: " + this.getName();
    }

    @Override
    public String toString() {
        return "Reader: " + this.getName();
    }

    public void addPurchasedBook(Book book) {
        this.purhasedBooks.add(book);
    }
}
