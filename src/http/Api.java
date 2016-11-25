package http;

import fromFilmTitle.FilmInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 21.11.2016.
 */
public interface Api {
    String BASE_URL = "http://www.omdbapi.com/";

    @GET("?plot=full&r=json")
    Call<FilmInfo> getFilmByTitle(@Query("t") String filmTitle);

    @GET("?plot=full&r=json")
    Call<FilmInfo> getFilmById(@Query("i") String id);

}
