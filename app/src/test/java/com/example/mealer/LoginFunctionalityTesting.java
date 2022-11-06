package com.example.mealer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mealer.functions.Validation;

import org.junit.Test;

public class LoginFunctionalityTesting {

    @Test
    public void checkInvalidEmail() {
        boolean checkValid = Validation.validateEmail("email.com");
        assertFalse("Verify that email is invalid", checkValid);
    }

    @Test
    public void checkValidEmail() {
        boolean checkValid = Validation.validateEmail("nathan@cool.ca");
        assertTrue("Verify that email is valid", checkValid);
    }

    @Test
    public void checkInvalidAdminCredentials() {
        boolean checkValid = Validation.validateAdmin("admin@admin.com", "mealeradmin10!");
        assertFalse("Verify invalid administrator email and password", checkValid);
    }

    @Test
    public void checkValidAdminCredentials() {
        boolean checkValid = Validation.validateAdmin("admin@email.com", "mealeradmin09!");
        assertTrue("Verify valid administrator email and password", checkValid);
    }
}
