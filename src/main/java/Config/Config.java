/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
//import java.util.ResourceBundle;

/**
 *
 * @author Julio
 */
public class Config {
    public static final ResourceBundle i18n = 
            ResourceBundle.getBundle(String.format("i18n.Bundle_%s", "pt_BR"));
    
    public static final DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    public static final NumberFormat nfc = NumberFormat.getCurrencyInstance();
    public static final NumberFormat nf = NumberFormat.getNumberInstance();
    
    public static final char INCLUIR = 'I';
    public static final char ALTERAR = 'A';
    public static final char EXCLUIR = 'E';
    
}
