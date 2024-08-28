package com.example.library.catalogue.model;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class BookId {

    @NonNull
    UUID bookId;
}
