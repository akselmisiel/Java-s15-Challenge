package com.library.interfaces;

import com.library.model.Book;
import com.library.model.Reader;

public interface Borrowable {
    boolean borrowBook(Reader reader);
    boolean returnBook(Reader reader);
}
