package bruteForceImdbIds;

import fromFilmTitle.FilmInfo;
import fromFilmTitle.FromFileTitleTask;
import jsop.ImdbFilmJsoup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CsvFileWriter;
import utils.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * Created by User on 23.11.2016.
 */
public class BrutForceCallback implements Callback<FilmInfo> {


    private String filmId;

    public BrutForceCallback(String filmId) {
        this.filmId = filmId;
    }

    @Override
    public void onResponse(Call<FilmInfo> call, Response<FilmInfo> response) {
        BrutForceImdbTask.countOfRequest.decrementAndGet();
       // Log.writeLog(filmId, "call success. Response status code:" + response.code());
        // isSuccess is true if response code => 200 and <= 300
        if (response.isSuccessful()) {
            FilmInfo filmInfo = response.body();
            // if parsing the JSON body failed, `response.body()` returns null
            if (filmInfo == null || filmInfo.getTitle() == null) {
                //Log.writeLog(filmId, "onResponse: error while parsing answer");
                //Log.error(filmId);
                //System.out.println("Fail");
            } else {
                Log.writeLiveId(filmId);
                if(filmInfo.isValid()){
                    //TODO if we need check ImdbFilmJsoup data valid we should write isJsoupDataValide() for better performance
                    ImdbFilmJsoup imdbFilmJsoup = new ImdbFilmJsoup(filmId);
                    filmInfo.setJsoupData(imdbFilmJsoup.getData());

                   //Log.writeLog(filmId, "onResponse: Success parsing answer, film is valid");
                    CsvFileWriter.writeCsvFile(filmInfo);
                    Log.writeGoodId(filmId);
                    System.out.println(" ids| valid: " + ++BrutForceImdbTask.TOTAL_IDS_VALID);
                }/*else{
                   // Log.writeLog(filmId, "onResponse: Success parsing answer, but film not valid");
                    //System.out.println("Not valid film with id - " + filmId);
                }*/
            }
        }/* else {
            //Log.writeLog(filmId, "onResponse: response isn't successful ");
        }*/
        //Log.writeLog(filmId, "Checked already: " + BrutForceImdbTask.TOTAL_IDS_CHECKED + " ids | find: " + BrutForceImdbTask.TOTAL_IDS_FIND + " ids| valid: " + ++BrutForceImdbTask.TOTAL_IDS_VALID);
    }

    @Override
    public void onFailure(Call<FilmInfo> call, Throwable throwable) {
        BrutForceImdbTask.countOfRequest.decrementAndGet();
        //Log.writeLog(filmId, "onFailure: something go wrong with request ");
       // System.out.println("onFailure");
        throwable.printStackTrace();
    }
}
