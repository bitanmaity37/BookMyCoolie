package com.cdac.iaf.bookmycoolie.utils;

public class RegEx {
    public final String mobilePattern = "^[6-9][0-9]{9}$"; // mobile number pattern, mumber starts between 6-9
    public final String emailPattern = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    public final String namePattern = "^[a-zA-Z ]*$"; ///allows spaces

    public final String billaPattern = "^[\\.a-zA-Z0-9:/ -]*$";  ///allows spaces

    public final String platformPattern = "^[\\.a-zA-Z0-9 ]*$";
}
