package com.bankasaurus.app;

public class TransactionStatus {
    public Status Status;
    public String Message;

    public TransactionStatus(Status status, String message) {
        this.Status = status;
        this.Message = message;
    }

    public TransactionStatus() { }
}
