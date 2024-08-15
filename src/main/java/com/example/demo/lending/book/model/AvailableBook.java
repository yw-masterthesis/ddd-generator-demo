package com.example.demo.lending.book.model;

import com.example.demo.catalogue.model.BookId;
import com.example.demo.catalogue.model.BookType;
import com.example.demo.commons.aggregates.Version;
import com.example.demo.lending.librarybranch.model.LibraryBranchId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(of = "bookInformation")
public class AvailableBook implements Book {

    @NonNull
    BookInformation bookInformation;

    @NonNull
    LibraryBranchId libraryBranch;

    @NonNull
    Version version;

    public AvailableBook(BookId bookId, BookType type, LibraryBranchId libraryBranchId, Version version) {
        this(new BookInformation(bookId, type), libraryBranchId, version);
    }

    public boolean isRestricted() {
        return bookInformation.getBookType().equals(BookType.Restricted);
    }

    public BookId getBookId() {
        return bookInformation.getBookId();
    }

    // public BookOnHold handle(BookPlacedOnHold bookPlacedOnHold) {
    // return new BookOnHold(
    // bookInformation,
    // new LibraryBranchId(bookPlacedOnHold.getLibraryBranchId()),
    // new PatronId(bookPlacedOnHold.getPatronId()),
    // bookPlacedOnHold.getHoldTill(),
    // version);
    // }
}
