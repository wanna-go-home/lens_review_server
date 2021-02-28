package com.springboot.intelllij.utils;

public class StringValidationUtils {

    private static final String emailExpression =  "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    private static final String phoneNumberExpression = "^[0-9]*$";

    public static final boolean isValidEmail(String email) {
        return email.matches(emailExpression);
    }

    public static final boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(phoneNumberExpression);
    }
}
