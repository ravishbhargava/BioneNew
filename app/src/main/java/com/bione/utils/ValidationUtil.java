package com.bione.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Developer: Bione
 */

public final class ValidationUtil {

    private static final int PASSWORD_LENGTH = 6;
    private static final int PHONE_LENGTH = 10;

    /**
     * Prevent instantiation
     */
    private ValidationUtil() {
    }

    /**
     * Method to validate email_signin id
     *
     * @param email user email_signin
     * @return whether email_signin is valid
     */
    public static boolean checkEmail(final String email) {
        if (TextUtils.isEmpty(email) || (!email.matches(Patterns.EMAIL_ADDRESS.toString()))) {
            return false;
        }
        return true;
    }

    /**
     * Method to validate password
     *
     * @param password user entered password
     * @return whether the password is valid
     */
    public static boolean checkPassword(final String password) {
        if (TextUtils.isEmpty(password) || (password.length() < PASSWORD_LENGTH)) {
            return false;
        }
        return true;
    }

    /**
     * Method to validate Phone Number
     *
     * @param phone user entered password
     * @return whether the password is valid
     */
    public static boolean checkPhone(final String phone) {
        if (TextUtils.isEmpty(phone) || (phone.length() < PHONE_LENGTH)) {
            return false;
        }
        return true;
    }

}
