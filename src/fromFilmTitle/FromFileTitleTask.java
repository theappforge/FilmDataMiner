package fromFilmTitle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.Api;
import org.apache.commons.io.IOUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Log;
import utils.Task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 23.11.2016.
 */
public class FromFileTitleTask implements Task {

    public static float TOTAL_FILM;
    public static float CURR_PROGRESS;
    public static final String TEST_TITLE = "Doctor Strange";

    private String fileName;

    public FromFileTitleTask(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        System.out.println("start ");
        Log.writeLog("PROGRAM", "start");
        // utils.CsvFileWriter.writeHeaders();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String everything = null;
        try {
            everything = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        List<String> filmsTitles = gson.fromJson(everything, new TypeToken<List<String>>(){}.getType());

        TOTAL_FILM = filmsTitles.size();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        for(String filmTitle : filmsTitles){
            api.getFilmByTitle(filmTitle).enqueue(new FilmCallback(filmTitle));
        }
    }
}
