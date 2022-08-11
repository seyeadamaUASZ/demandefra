/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author indiaye
 */
public class PasswordValidator {

    private Pattern pattern;
    private Matcher matcher;
    private String min_size;
    private String max_size;
    private String digit;
    private String uppercase;
    private String lowercase;
    private String specialcase;
    private String patter_pwd;
    /*"Le mot de passe doit comporter entre 8 et 20 caractères dont au moins une minuscule, une majuscule et un chiffre!");*/
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9*$-+?_&=!%@#\\[\\]\\\"{}/])(?=.*?\\W).{8,20})";

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @param nbr_dig
     * @param nbr_lcase
     * @param nbr_ucase
     * @param nbr_scase
     * @return true valid password, false invalid password
     */
    public boolean validate(String password, String min_size, String nbr_dig, String nbr_ucase, String nbr_scase) {        
        this.digit = (nbr_dig!=null)?"(?=(?:[^0-9]*[0-9]){"+nbr_dig+",}[^0-9]*$)":"";
        this.uppercase = (nbr_ucase!=null)?"(?=(?:[^A-Z]*[A-Z]){"+nbr_ucase+",}[^A-Z]*$)":"";
        this.lowercase = "(?=(?:[^a-z]*[a-z]){"+1+",}[^a-z]*$)";
        this.specialcase = (nbr_scase!=null)?"(?=(?:[^*$-+?_&=!%@#\\[\\]\\\"{}/]*[*$-+?_&=!%@#\\[\\]\\\"{}/]){"+nbr_scase+",}[^*$-+?_&=!%@#\\[\\]\\\"{}/]*$)":"";
        this.patter_pwd = "("+this.lowercase+""+this.uppercase+""+this.digit+""+this.specialcase+"(?=.*?\\W).{"+min_size+",20})";
        matcher = Pattern.compile(this.patter_pwd).matcher(password);
        return matcher.matches();
    }

    public String getPatter_pwd() {
        return patter_pwd;
    }

    public void setPatter_pwd(String patter_pwd) {
        this.patter_pwd = patter_pwd;
    }
    
    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }
}
