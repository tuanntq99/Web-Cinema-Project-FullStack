package com.example.webcinema.Handle.Email;

import java.util.regex.Pattern;

public class Validate {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String pnRegex = "0[0-9]{9}";
        Pattern pattern = Pattern.compile(pnRegex);
        return pattern.matcher(phoneNumber).matches();
    }
}
