package com.example.banksysteem.Util;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deze klasse bevat de controles die uitgevoerd worden op de gegevens die de gebruiker bij het registreren ingevuld heeft.
 * @author Inge
 * @version 1
 * @see com.example.banksysteem.Controller.GegevensRegistreerFragment
 */
public class ValidateAanvraagInput {

    /**
     * Deze methode maakt alle tekstvakken die van het type EditText zijn leeg.
     * @param root De parent van de layout waar de tekstvakken in zitten.
     */
    public void clearEdittext(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

    //check of er tekstvelden leeg zijn

    /**
     * Deze methode controleert of alle tekstvakken van het type EditText een waarde bevatten.
     * @param root De parent van de layout waar de tekstvakken in zitten.
     * @return false als er nog tekstvakken leeg zijn, true als alle tekstvakken een waarde hebben.
     */
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

    /**
     * Deze methode controleert of er een geldig email ingevuld is.
     * @param email De email die ingevuld is door de gebruiker
     * @return true als de email goed is, false als dit niet zo is.
     */
    public boolean checkEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //check of bsn voldoet aan 11 proef

    /**
     * Deze methode controleert of de ingevulde bsn voldoet aan de 11 proef en daarmee dus geldig is.
     * @param bsn De bsn die de gebruiker ingevuld heeft.
     * @return true als de bsn goed ingevuld is, false als dit niet zo is.
     */
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

    /**
     * Deze methode controleert of de parameter alleen letters bevat.
     * @param input De waarde van het tekstvak dat gecontroleerd moet worden op alleen letters (bijvoorbeeld voornaam).
     * @return false als de waarde niet voldoet aan de controle, true als dit wel zo is.
     */
    public boolean validateLetters(String input) {

        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();

    }

    /**
     * Deze methode checkt de waarde van bsn of kvk
     * @param kvk_bsn De bsn of kvk die de gebruiker ingevuld heeft.
     * @return false als de waarde niet goed ingevuld is, true als dit wel zo is.
     */
    public boolean checkKvkBsn(String kvk_bsn, RadioButton particulier, RadioButton bedrijf) {

        if (particulier.isChecked()) {
            int bsn = Integer.parseInt(kvk_bsn);
            if (!isValidBSN(bsn)) {
                return false;
            }
        }
        if (bedrijf.isChecked()) {
            if (kvk_bsn.length() != 8) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deze methode controleert het ingevulde telefoonnummer
     * @param telefoon Het telefoonnummer dat de gebruiker heeft ingevuld.
     * @return true als het telefoonnummer goed is, false als dit niet zo is.
     */
    public boolean checkTelefoonnummer(String telefoon){

        String regexMobiel = "^(((\\+31|0|0031)6){1}[1-9]{1}[0-9]{7})$";
        String regexVast = "^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$";
        Pattern pattern = Pattern.compile(regexMobiel);
        Pattern pattern1 = Pattern.compile(regexVast);

        return pattern.matcher(telefoon).matches() || pattern1.matcher(telefoon).matches();

    }

    /**
     * Deze methode checkt of de input alleen letters en cijfers bevat.
     * @param input De waarde die gecontroleerd moet worden.
     * @return true als de waarde goed is, false als dit niet zo is.
     */
    public boolean checkAlphaNumeric(String input){

        String regex = "^(?![0-9]*$)[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input).matches();

    }

    /**
     * Deze methode checkt het ingevoerde adres. Het adres mag alleen letters en cijfers bevatten, niet alleen letters
     * en ook niet alleen cijfers.
     * @param input Het adres dat de gebruiker ingevoerd heeft.
     * @return true als het adres goed ingevuld is, false als dit niet zo is.
     */
    public boolean checkAdres(String input){

        String regex = "^[a-zA-Z](?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9\\s]+$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(input).matches();

    }

}
