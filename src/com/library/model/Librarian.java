package com.library.model;

public class Librarian extends Person{
    public Librarian(String name) {
        super(name);
    }

    @Override
    public String whoYouAre() {
        return "Librarian: " + this.getName();
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return "Librarian: " + this.getName();
    }
}
