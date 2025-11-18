package com.banking.exception;

public class CustomerNotAuthorizedException extends Exception {
    private String customerId;
    private String resource;

    public CustomerNotAuthorizedException(String customerId, String resource) {
        super(String.format("Customer %s not authorized to access %s", customerId, resource));
        this.customerId = customerId;
        this.resource = resource;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getResource() {
        return resource;
    }
}
