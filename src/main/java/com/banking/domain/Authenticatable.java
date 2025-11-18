package com.banking.domain;

import com.banking.exception.InvalidPasswordException;

public interface Authenticatable {
    boolean authenticate(String password) throws InvalidPasswordException;

    void changePassword(String oldPassword, String newPassword) throws InvalidPasswordException;

    void resetPassword(String newPassword);

    boolean isLocked();
}
