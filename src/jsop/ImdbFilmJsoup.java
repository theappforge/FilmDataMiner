package jsop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by User on 24.11.2016.
 */
public class ImdbFilmJsoup {

    private static final String BASE_URL = "http://www.imdb.com/title/";
    public static final String BUDGET = "Budget:";
    public static final String OPENING_WEEKEND = "Opening Weekend:";
    public static final String GROSS = "Gross";
    public static final String KEY_WORDS = "Plot Keywords:";
    public static final String PRODUCTION_CO = "Production Co:";
    public static final String USER_REVIEW = "user";
    public static final String CRITIC_REVIEW = "critic";


    private String budget;
    private String openingWeekend;
    private String gross;
    private String keyWords;
    private String productionCo;
    private String userReviews;
    private String criticalReviews;


    public ImdbFilmJsoup(String filmId) {
        String urlToRequest = BASE_URL + filmId;
        try {
            Document doc = Jsoup.connect(urlToRequest).get();
            //get money information || production company
            Elements elements = doc.getElementsByClass("txt-block");
            for (Element element : elements) {

                //get info about many
                if (element.text().contains("$")) {
                    String planeText = element.text();
                    if (planeText.contains(BUDGET)) {
                        budget = getMoneyFromString(planeText);
                    }
                    if (planeText.contains(OPENING_WEEKEND)) {
                        openingWeekend = getMoneyFromString(planeText);
                    }
                    if (planeText.contains(GROSS)) {
                        gross = getMoneyFromString(planeText);
                    }
                }

                //get info about production company
                if (element.text().contains(PRODUCTION_CO)) {
                    String planeText = element.text();
                    productionCo = planeText.replace("Production Co: ", "").replace("See more »",  "");
                }
            }

            //get keyword
            Elements keyWordElements = doc.getElementsByClass("see-more inline canwrap");
            for (Element element : keyWordElements) {
                if (element.text().contains(KEY_WORDS)) {
                    String planeText = element.text();
                    planeText = planeText.replace("Plot Keywords: ", "");
                    keyWords = planeText.substring(0, planeText.lastIndexOf('|'));
                }
            }

            //get reviews
            Elements reviewsElements = doc.getElementsByClass("titleReviewBarItem titleReviewbarItemBorder");
            for (Element element : reviewsElements) {
                Elements links =  element.getElementsByTag("a");
                for (Element link : links) {
                    String linkText = link.text();
                    if(linkText.contains(USER_REVIEW)){
                        userReviews = linkText.replaceAll("[^\\d.]", "");
                    }
                    if(linkText.contains(CRITIC_REVIEW)){
                        criticalReviews = linkText.replaceAll("[^\\d.]", "");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getData(){
        HashMap<String, String> data = new HashMap<>();
        data.put(BUDGET, budget);
        data.put(OPENING_WEEKEND, openingWeekend);
        data.put(GROSS, gross);
        data.put(KEY_WORDS, keyWords);
        data.put(PRODUCTION_CO, productionCo);
        data.put(USER_REVIEW, userReviews);
        data.put(CRITIC_REVIEW, criticalReviews);
        return data;
    }

    private String getMoneyFromString(String string) {
        int indexAfterMoney = string.length();
        for (int i = string.indexOf("$"); i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                indexAfterMoney = i;
                break;
            }
        }
        return string.substring(string.indexOf("$") + 1, indexAfterMoney);
    }
}
