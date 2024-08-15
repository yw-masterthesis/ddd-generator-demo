package com.example.demo.lending.book.model;

import com.example.demo.catalogue.model.BookId;
import com.example.demo.catalogue.model.BookType;
import com.example.demo.commons.aggregates.Version;

public interface Book {

    default BookId bookId() {
        return getBookInformation().getBookId();
    }

    default BookType type() {
        return getBookInformation().getBookType();
    }

    BookInformation getBookInformation();

    Version getVersion();
}
