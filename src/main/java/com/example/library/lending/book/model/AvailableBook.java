package com.example.library.lending.book.model;

import com.example.library.catalogue.model.BookType;
import com.example.library.catalogue.model.bookinstances.BookId;
import com.example.library.commons.aggregates.Version;
import com.example.library.lending.librarybranch.model.LibraryBranchId;

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
