package common;

import javax.ws.rs.core.Response;

public class Utils {
    private static long studentIndex = 0;
    private static long markId = 0;
    private static long subjectId = 0;
    public static synchronized long getStudentIndex(){
        return studentIndex++;
    }
    public static synchronized long getMarkId(){
        return markId++;
    }
    public static synchronized long getSubjectId(){
        return subjectId++;
    }

}
