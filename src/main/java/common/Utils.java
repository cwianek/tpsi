package common;

public class Utils {
    private static int markId = 0;
    public static synchronized int getMarkId(){
        return markId++;
    }
}
