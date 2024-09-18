package com.example.library.catalogue.model.bookinstances;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class BookId {

    @NonNull
    UUID bookId;
}
