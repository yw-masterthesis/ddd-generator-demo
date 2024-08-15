package com.example.demo.lending.librarybranch.model;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class LibraryBranchId {

    @NonNull
    UUID libraryBranchId;
}
