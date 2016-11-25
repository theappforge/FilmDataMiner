package utils;

import fromFilmTitle.FilmInfo;

/**
 * Created by User on 24.11.2016.
 */
public final class Utils {
    public static boolean isNull(String string){
        return string == null || string.equals("") || string.equals(FilmInfo.NULL);
    }
}
