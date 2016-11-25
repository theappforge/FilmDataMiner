package fromFilmTitle;

import jsop.ImdbFilmJsoup;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 13.11.2016.
 */
public class FilmInfo {

    private static final int MIN_YEAR = 1970;
    private static final int MIN_ACTORS = 3;
    private static final String ONLY_THIS_TYPE = "movie";
    public static final String NULL = "N/A";

    private String Title;
    private String Year;
    private String Rated;
    private String Poster;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Metascore;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;

    private HashMap<String, String> dataFromJsoup;

    public String getTitle() {
        return Title;
    }

    public FilmInfo() {
    }

    public FilmInfo(String title, String year, String rated, String released, String runtime, String genre, String director, String writer,
                    String actors, String plot, String language, String country, String imdbRating, String imdbVotes, String imdbID, String type) {
        Title = title;
        Year = year;
        Rated = rated;
        Released = released;
        Runtime = runtime;
        Genre = genre;
        Director = director;
        Writer = writer;
        Actors = actors;
        Plot = plot;
        Language = language;
        Country = country;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        Type = type;
    }

    //TODO: when upgrade this method dont forgot to upgrate FILE_HEADER in CsvFileWriter.class
    public List getDataList() {
        List<String> dataList = new ArrayList();
        dataList.add(Title);
        dataList.add(Poster);
        dataList.add(Year.replaceAll("[^\\d.]", ""));
        dataList.add(Rated);
        dataList.add(Released);

        dataList.add(Runtime.replaceAll("[^\\d.]", ""));
        dataList.add(Genre);

        dataList.add(Director);
        /*//get only 1 first without descriptions
        dataList.add(Director.replaceAll("\\(.*?\\)", "").split(",")[0]);*/

        dataList.add(Writer);
        /*//get only 1 first without descriptions
        dataList.add(Writer.replaceAll("\\(.*?\\)", "").split(",")[0]);*/

        dataList.add(Actors);
      /*  //get 3 first actors
        String[] actors = Actors.split(",");
        dataList.add(actors[0]);
        dataList.add(actors[1]);
        dataList.add(actors[2]);*/
        dataList.add(Plot);

        //set data from parsing
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.KEY_WORDS));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.BUDGET));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.OPENING_WEEKEND));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.GROSS));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.USER_REVIEW));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.CRITIC_REVIEW));
        dataList.add(dataFromJsoup.get(ImdbFilmJsoup.PRODUCTION_CO));

        dataList.add(Language);
        dataList.add(Country);
        dataList.add(String.valueOf(imdbRating));
        dataList.add(imdbVotes);
        dataList.add(Metascore);
        dataList.add(imdbID);
        dataList.add(Type);
        return dataList;
    }

    @Override
    public String toString() {
        return "Title: " + Title + ",  Director: " + Director + ", imdbRating: " + imdbRating;
    }

    public boolean isValid() {
        String[] actors = Actors.split(",");
        int numberOfActor = actors.length;
        String clearYear = Year.replaceAll("[^\\d.]", "");
        int year = Integer.parseInt(clearYear);

        if (!Type.equals(ONLY_THIS_TYPE)) return false;
        else if (numberOfActor < MIN_ACTORS) return false;
        else if (year < MIN_YEAR) return false;
        else if (Utils.isNull(Plot)) return false;
        else if (Utils.isNull(Director)) return false;
        //else if (Utils.isNull(Writer)) return false;
        else if (Utils.isNull(Country)) return false;

        return true;
    }

    public void setJsoupData(HashMap<String,String> jsoupData) {
        this.dataFromJsoup = jsoupData;
    }
}
