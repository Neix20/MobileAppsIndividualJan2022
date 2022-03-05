package my.edu.utar.neixpasswordmanager.Utils;

public class util {
    public static boolean checkIfAnyEmpty(String[] arr){
        for(String s : arr){
            if(s.isEmpty()){
                return true;
            }
        }
        return false;
    }
}
