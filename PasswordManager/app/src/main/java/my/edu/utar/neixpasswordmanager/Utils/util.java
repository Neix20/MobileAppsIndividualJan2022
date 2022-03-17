package my.edu.utar.neixpasswordmanager.Utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import java.security.Key;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class util {
    private static final String ALGORITHM = "AES";

    public static boolean checkIfAnyEmpty(String[] arr){
        for(String s : arr){
            if(s.isEmpty()){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to code text in clip board
     *
     * @param context context
     * @param text    text what wan to copy in clipboard
     * @param label   label what want to copied
     */
    public static void copyCodeInClipBoard(Context context, String label, String text) {
        if (context != null) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(label, text);
            if (clipboard == null || clip == null)
                return;
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Successfully Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }

    public static String genPassword(int pwd_len, boolean needUpperCase, boolean needLowerCase, boolean needDigit, boolean needSymbol){
        String password = genPassword2(pwd_len, needUpperCase, needLowerCase, needDigit, needSymbol);
        while(!check(password, needUpperCase, needLowerCase, needDigit, needSymbol)){
            password = genPassword2(pwd_len, needUpperCase, needLowerCase, needDigit, needSymbol);
        }
        return password;
    }

    public static String genPassword2(int pwd_len, boolean needUpperCase, boolean needLowerCase, boolean needDigit, boolean needSymbol){
        String lowercase_str = "abcdefghijklmnopqrstuvwxyz";
        String uppercase_str = lowercase_str.toUpperCase();
        String digit_str = "012345689";
        String symbol_str = "#?!:;?%*£€$=+@{}[]&()";

        String chars="";
        if(needUpperCase) chars += uppercase_str;
        if(needLowerCase) chars += lowercase_str;
        if(needDigit) chars += digit_str;
        if(needSymbol) chars += symbol_str;
        StringBuilder finalPassword = new StringBuilder();
        for(int i = 0; i < pwd_len; i++)  {
            int index = ThreadLocalRandom.current().nextInt(0,chars.length());
            finalPassword.append(chars.charAt(index));
        }
        return finalPassword.toString();
    }

    public static boolean isAlphaNumeric(char char1) {
        return (char1 >= 'a' && char1 <= 'z') || (char1 >= 'A' && char1 <= 'Z') || (char1 >= '0' && char1 <= '9');
    }

    private static boolean check(String password, boolean needUpperCase, boolean needLowerCase, boolean needDigit, boolean needSymbol) {
        //we declare our booleans
        boolean hasDigit = false;
        boolean hasSymbol = false;
        boolean hasLower = false;
        boolean hasUpper = false;

        for(char c : password.toCharArray()) {
            //we check that the password corresponds to a sufficient level of security according to the selected options
            if (needUpperCase && !hasUpper) hasUpper = Character.isUpperCase(c);
            if (needLowerCase && !hasLower) hasLower = Character.isLowerCase(c);
            if (needDigit && !hasDigit) hasDigit = Character.isDigit(c);
            if (needSymbol && !hasSymbol) hasSymbol = !isAlphaNumeric(c);
        }
        return (!needUpperCase || hasUpper) && (!needLowerCase || hasLower) && (!needDigit || hasDigit) && (!needSymbol || hasSymbol);
    }

    public static String encrypt(String value, String KEY) throws Exception
    {
        Key key = generateKey(KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;
    }

    public static String decrypt(String value, String KEY) throws Exception
    {
        Key key = generateKey(KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
    }

    private static Key generateKey(String KEY) throws Exception
    {
        Key key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        return key;
    }
}
