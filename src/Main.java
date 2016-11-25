
import bruteForceImdbIds.BrutForceImdbTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fromFilmTitle.FilmCallback;
import fromFilmTitle.FromFileTitleTask;
import jsop.ImdbFilmJsoup;
import org.apache.commons.io.IOUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Log;
import utils.Task;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by User on 13.11.2016
 */


public class Main {


    static Task[] tasks = {new FromFileTitleTask("american.txt"), new BrutForceImdbTask()};

    public static void main(String[] args) throws IOException {
        tasks[1].execute();
    }

}
