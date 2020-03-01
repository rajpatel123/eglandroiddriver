package com.eaglecabs.provider.common;

import java.util.regex.Pattern;

/**
 * Created by CSS88 on 09-01-2018.
 */

public class CommonValidation {
    public static Boolean Validation(String Values){
        return Values == null || Values.equalsIgnoreCase("null") || Values.isEmpty();
    }
    public static boolean isValidPhone(String phone) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() < 6 || phone.length() > 13;
        } else {
            check=true;
        }
        return check;
    }
}
