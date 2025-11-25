package com.example.JobTask.exception;

public class NoEnoughStockQuantity extends RuntimeException {

    public NoEnoughStockQuantity(String message) {
        super(message);
    }
}
