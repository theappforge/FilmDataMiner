package fromFilmTitle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.*;

import java.io.*;
import java.util.Date;

/**
 * Created by User on 22.11.2016.
 */
public class FilmCallback implements Callback<FilmInfo> {

    private String filmTitle;

    public FilmCallback(String filmTitle){
        this.filmTitle = filmTitle;
    }

    @Override
    public void onResponse(Call<FilmInfo> call, Response<FilmInfo> response) {
        Log.writeLog(filmTitle, "call success. Response status code:" + response.code());
        // isSuccess is true if response code => 200 and <= 300
        if (response.isSuccessful()) {
            FilmInfo filmInfo = response.body();
            // if parsing the JSON body failed, `response.body()` returns null
            if (filmInfo == null || filmInfo.getTitle() == null) {
                Log.writeLog(filmTitle, "onResponse: error while parsing answer");
                Log.error(filmTitle);
            } else {
                Log.writeLog(filmTitle, "onResponse: Success parsing answer");
                CsvFileWriter.writeCsvFile(filmInfo);
            }
        } else {
            Log.error(filmTitle);
        }

        FromFileTitleTask.CURR_PROGRESS++;
        float persent = FromFileTitleTask.CURR_PROGRESS/ FromFileTitleTask.TOTAL_FILM*100;
        System.out.println(new Date() + "| Total: " + FromFileTitleTask.TOTAL_FILM + "; Current: " + FromFileTitleTask.CURR_PROGRESS + "; " + persent + "%");
    }

    @Override
    public void onFailure(Call<FilmInfo> call, Throwable throwable) {
        StringWriter errors = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errors));
        Log.writeLog(filmTitle, "call fail. onFailure: " + errors.toString());
        Log.error(filmTitle);
        throwable.printStackTrace();

        FromFileTitleTask.CURR_PROGRESS++;
        System.out.println(new Date() + "| Total: " + FromFileTitleTask.TOTAL_FILM + "; Current: " + FromFileTitleTask.CURR_PROGRESS);
    }
}
