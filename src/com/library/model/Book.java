package com.library.model;

import com.library.enums.Category;
import com.library.enums.Status;
import com.library.interfaces.Borrowable;
import com.library.interfaces.Purchasable;

import java.util.Date;
import java.util.Objects;

public class Book implements Borrowable, Purchasable{
    private int bookID;
    private Author author;
    private String title;
    private double price;
    private Status status;
    private Date dateBorrowed;
    private Person owner;
    private Category category;

    public Book(int bookID, Author author, String title, double price, Category category) {
        this.bookID = bookID;
        this.author = author;
        this.title = title;
        this.price = price;
        this.status = Status.AVAILABLE;
        this.category = category;
        this.owner = null;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBookID() {
        return bookID;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public Person getOwner() {
        return owner;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookID == book.bookID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookID);
    }

    @Override
    public boolean borrowBook(Reader reader) {
        if (status == Status.AVAILABLE && reader.canBorrowMoreBooks()) {
            status = Status.BORROWED;
            owner = reader;
            reader.addBook(this);
            System.out.println(reader.getName() + " borrowed the book: " + title);
            return true;
        } else {
            System.out.println("Book is not available or reader has reached the book limit.");
            return false;
        }
    }

    @Override
    public boolean returnBook(Reader reader) {
        if (status == Status.BORROWED && owner.equals(reader)) {
            status = Status.AVAILABLE;
            owner = null;
            reader.removeBook(this);
            System.out.println(reader.getName() + " returned the book: " + title);
            return true;
        } else {
            System.out.println("This book was not borrowed by " + reader.getName());
            return false;
        }
    }

    @Override
    public void purchaseBook(Reader reader) {
        if (reader.getBalance() >= price) {
            owner = reader;
            reader.addPurchasedBook(this);
            reader.deductBalance(price);
            System.out.println(reader.getName() + " purchased the book: " + title);
        } else {
            System.out.println("Insufficient balance to purchase the book.");
        }
    }

    @Override
    public String toString() {
        return  "------------------------" + "\nBook ID: " + this.bookID + "\n" + this.author + "\nTitle: " + this.title + "\nPrice: " + this.price + "\nStatus: " + this.status + "\nCategory: " + this.category + "\n" + (owner != null ? "Owner: " + owner.getName() : "Owner: none" ) + "\n------------------------";
    }
}
