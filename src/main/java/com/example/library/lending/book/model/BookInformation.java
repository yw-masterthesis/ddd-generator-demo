package com.example.library.lending.book.model;

import com.example.library.catalogue.model.BookType;
import com.example.library.catalogue.model.bookinstances.BookId;

import lombok.NonNull;
import lombok.Value;

@Value
public class BookInformation {

    @NonNull
    BookId bookId;

    @NonNull
    BookType bookType;
}
