package com.cops.bloodbankclone.utility;

import android.app.Activity;
import android.widget.EditText;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.view.activity.AuthActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.Settings.System.getString;

public class CheckInput {

    private static Pattern p;
    private static Matcher m;


    public static boolean isPasswordMatched(TextInputLayout password,TextInputLayout cPassword){


        if(password.getEditText().getText().toString().equals(cPassword.getEditText().getText().toString())){
            return true;


        } else {
            password.getEditText().setError("Password not matched");
            cPassword.getEditText().setError("Password not matched");
            password.getEditText().requestFocus();

            return false;
        }
    }

    public static boolean isEmailValid(TextInputLayout email){

        p=Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        m=p.matcher(email.getEditText().getText().toString());

        if(m.matches()){
            return true;

        } else {
            email.getEditText().setError("Please enter valid email address");
            email.getEditText().requestFocus();
            return false;
        }
    }
    public static boolean isEditTextSet(TextInputEditText editText){
        if(editText.getText().toString().equals("")){
            editText.setError("this field is required");
            editText.requestFocus();
            return false;

        } else {
            return true;
        }
    }

    public static boolean isEditTextSet(EditText editText,EditText editText2){
        if(editText.getText().toString().equals("")){
            editText.setError("this field is required");
            editText.requestFocus();
            return false;

        } else if(editText2.getText().toString().equals("")){
            editText2.setError("this field is required");
            editText2.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isPhoneSet(TextInputLayout phone){
         p=Pattern.compile("[0-9]{11}");
         m=p.matcher(phone.getEditText().getText().toString());
        if (m.matches()) {
            return true;
        }else {
            phone.getEditText().setError("Please enter correct phone number");
            phone.getEditText().requestFocus();
            return false;
        }
    }

    public static boolean isEditTextSet(TextInputLayout editText){
        if(editText.getEditText().getText().equals("")){
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEditTextSet(TextInputLayout editText, TextInputLayout editText2){
        if(editText.getEditText().getText().toString().equals("")) {
            editText.getEditText().setError("this field is required");
            editText.getEditText().requestFocus();
            return false;
        }else if(editText2.getEditText().getText().toString().equals("")) {
            editText2.getEditText().setError("this field is required");
            editText2.getEditText().requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEditTextSet(TextInputLayout editText, TextInputLayout editText2, TextInputLayout editText3){
        if(editText.getEditText().getText().toString().equals("")) {
            editText.getEditText().setError("this field is required");
            editText.getEditText().requestFocus();
            return false;
        }else if(editText2.getEditText().getText().toString().equals("")) {
            editText2.getEditText().setError("this field is required");
            editText2.getEditText().requestFocus();
            return false;
        }else if(editText3.getEditText().getText().toString().equals("")){
            editText3.getEditText().setError("this field is required");
            editText3.getEditText().requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEditTextSet(TextInputLayout editText, TextInputLayout editText2, TextInputLayout editText3,TextInputLayout editText4){
        if(editText.getEditText().getText().toString().equals("")) {
            editText.getEditText().setError("this field is required");
            editText.getEditText().requestFocus();
            return false;
        }else if(editText2.getEditText().getText().toString().equals("")) {
            editText2.getEditText().setError("this field is required");
            editText2.getEditText().requestFocus();
            return false;
        }else if(editText3.getEditText().getText().toString().equals("")){
            editText3.getEditText().setError("this field is required");
            editText3.getEditText().requestFocus();
            return false;
        }else if(editText4.getEditText().getText().toString().equals("")){
            editText4.getEditText().setError("this field is required");
            editText4.getEditText().requestFocus();
            return false;
        }else {
            return true;
        }
    }
    public static boolean isEditTextSet(TextInputLayout editText, TextInputLayout editText2,
                                        TextInputLayout editText3,TextInputLayout editText4,
                                        TextInputLayout editText5, TextInputLayout editText6){
        if(editText.getEditText().getText().toString().equals("")) {
            editText.getEditText().setError("this field is required");
            editText.getEditText().requestFocus();
            return false;
        }else if(editText2.getEditText().getText().toString().equals("")) {
            editText2.getEditText().setError("this field is required");
            editText2.getEditText().requestFocus();
            return false;
        }else if(editText3.getEditText().getText().toString().equals("")){
            editText3.getEditText().setError("this field is required");
            editText3.getEditText().requestFocus();
            return false;
        }else if(editText4.getEditText().getText().toString().equals("")){
            editText4.getEditText().setError("this field is required");
            editText4.getEditText().requestFocus();
            return false;
        }else if(editText5.getEditText().getText().toString().equals("")) {
            editText5.getEditText().setError("this field is required");
            editText5.getEditText().requestFocus();
            return false;
        }else if(editText6.getEditText().getText().toString().equals("")){
            editText6.getEditText().setError("this field is required");
            editText6.getEditText().requestFocus();
            return false;

        }else {
            return true;
        }
    }
    public static boolean isEditTextSet(TextInputLayout editText, TextInputLayout editText2,
                                        TextInputLayout editText3,TextInputLayout editText4,
                                        TextInputLayout editText5, TextInputLayout editText6,
                                        TextInputLayout editText7){
        if(editText.getEditText().getText().toString().equals("")) {
            editText.getEditText().setError("this field is required");
            editText.getEditText().requestFocus();
            return false;
        }else if(editText2.getEditText().getText().toString().equals("")) {
            editText2.getEditText().setError("this field is required");
            editText2.getEditText().requestFocus();
            return false;
        }else if(editText3.getEditText().getText().toString().equals("")){
            editText3.getEditText().setError("this field is required");
            editText3.getEditText().requestFocus();
            return false;
        }else if(editText4.getEditText().getText().toString().equals("")){
            editText4.getEditText().setError("this field is required");
            editText4.getEditText().requestFocus();
            return false;
        }else if(editText5.getEditText().getText().toString().equals("")) {
            editText5.getEditText().setError("this field is required");
            editText5.getEditText().requestFocus();
            return false;
        }else if(editText6.getEditText().getText().toString().equals("")){
            editText6.getEditText().setError("this field is required");
            editText6.getEditText().requestFocus();
            return false;
        }else if(editText7.getEditText().getText().toString().equals("")){
            editText7.getEditText().setError("this field is required");
            editText7.getEditText().requestFocus();
            return false;


        }else {
            return true;
        }
    }
}
