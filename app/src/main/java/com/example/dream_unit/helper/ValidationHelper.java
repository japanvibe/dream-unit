package com.example.dream_unit.helper;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationHelper {
    public static boolean validateEmail(CharSequence email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
