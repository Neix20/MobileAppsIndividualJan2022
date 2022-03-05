package my.edu.utar.neixpasswordmanager.Utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class util {
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
}
