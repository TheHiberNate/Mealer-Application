package com.example.mealer.functions;

import androidx.core.util.PatternsCompat;

public class Validation {
    public static boolean validateEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateAdmin(String email, String password) {
        return email.equals("admin@email.com") && password.equals("mealeradmin09!");
    }
}
