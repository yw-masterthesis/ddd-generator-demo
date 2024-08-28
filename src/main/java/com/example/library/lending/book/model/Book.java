package com.example.library.lending.book.model;

import com.example.library.catalogue.model.BookId;
import com.example.library.catalogue.model.BookType;
import com.example.library.commons.aggregates.Version;

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
