package com.example.library.catalogue.model.bookinstances;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

import com.example.library.catalogue.model.BookType;
import com.example.library.catalogue.model.books.Book;
import com.example.library.catalogue.model.books.ISBN;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookInstance {

    @NonNull
    ISBN bookIsbn;
    @NonNull
    BookId bookId;
    @NonNull
    BookType bookType;

    static BookInstance instanceOf(Book book, BookType bookType) {
        return new BookInstance(book.getBookIsbn(), new BookId(UUID.randomUUID()), bookType);

    }
}