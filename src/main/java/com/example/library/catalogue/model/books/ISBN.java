package com.example.library.catalogue.model.books;

import lombok.NonNull;
import lombok.Value;

@Value
public class ISBN {

    private static final String VERY_SIMPLE_ISBN_CHECK = "^\\d{9}[\\d|X]$";

    @NonNull
    String isbn;

    ISBN(String isbn) {
        if (!isbn.trim().matches(VERY_SIMPLE_ISBN_CHECK)) {
            throw new IllegalArgumentException("Wrong ISBN!");
        }
        this.isbn = isbn.trim();

    }
}
