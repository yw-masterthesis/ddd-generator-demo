package com.example.demo.lending.book.model;

import com.example.demo.catalogue.model.BookId;
import com.example.demo.catalogue.model.BookType;

import lombok.NonNull;
import lombok.Value;

@Value
public class BookInformation {

    @NonNull
    BookId bookId;

    @NonNull
    BookType bookType;
}
