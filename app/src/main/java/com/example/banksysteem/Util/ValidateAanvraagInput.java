package com.example.banksysteem.Util;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateAanvraagInput {

    //methode om alle invoervelden van het type Edittext in dit scherm leeg te maken
    public void clearEdittext(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

    //check of er tekstvelden leeg zijn
    public boolean emptyEdittext(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof EditText) {
                if (TextUtils.isEmpty(((EditText) view).getText())){
                    ((EditText) view).setError("Dit is een verplicht veld");
                    return false;
                }
            }
        } return true;
    }

    //methode die checkt of ingevulde email voldoet aan email format voorwaarden
    public boolean checkEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //check of bsn voldoet aan 11 proef
    public boolean isValidBSN(int bsn) {
        if (bsn <= 9999999 || bsn > 999999999) {
            return false;
        }
        int sum = -1 * bsn % 10;

        for (int multiplier = 2; bsn > 0; multiplier++) {
            int val = (bsn /= 10) % 10;
            sum += multiplier * val;
        }

        return sum != 0 && sum % 11 == 0;
    }

    public boolean validateLetters(String input) {

        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();

    }

}
