package com.library.model;

import java.util.ArrayList;

public class Author extends Person{
    private ArrayList<Book> books;

    public Author(String name) {
        super(name);
        this.books = new ArrayList<Book>();
    }

    @Override
    public String whoYouAre() {
        return "Author: " + this.getName();
    }

    public void newBook(Book book) {
        this.books.add(book);
    }

    public void showBooks() {
        for (Book book : this.books) {
            System.out.println("-> " + book.getTitle());
        }
    }

    @Override
    public String toString() {
        return "Author: " + this.getName();
    }
}
