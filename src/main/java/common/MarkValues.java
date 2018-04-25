package common;

import java.util.ArrayList;
import java.util.List;

public class MarkValues {

    public static String getMarkValue(String mark){
        switch (mark){
            case "2":
                return "2";
            case "2.5":
                return "2.5";
            case "3":
                return "3";
            case "3.5":
                return "3.5";
            case "4":
                return "4";
            case "4.5":
                return "4.5";
            case "5":
                return "5";
        }
        return "-1";
    }
}
