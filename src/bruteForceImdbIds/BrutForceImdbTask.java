package bruteForceImdbIds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fromFilmTitle.FilmCallback;
import http.Api;
import org.apache.commons.io.IOUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.CsvFileWriter;
import utils.Log;
import utils.Task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 23.11.2016.
 */
public class BrutForceImdbTask implements Task {

    //public static int TOTAL_IDS_FIND;
    public static int TOTAL_IDS_VALID;
    //public static int TOTAL_IDS_CHECKED;
    public static AtomicInteger countOfRequest = new AtomicInteger(0);

    @Override
    public void execute() {
        System.out.println("start ");
        Log.writeLog(new Date() + " PROGRAM", "start");
        CsvFileWriter.writeHeaders();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        //generate list of possible IMDB IDs
        Integer[] array = new Integer[9999999];
        for (int i = 0; i < array.length; i++) {
            array[i] = i+1;
        }
        List<Integer> list = Arrays.asList(array);
        Collections.shuffle(list);
        System.out.print("Start time - " + new Date());
        int i = 0;
        for (Integer aList : list) {
            i++;
            //System.out.println(list.get(i) + " ");
            int intId = aList;
            int currCount = countOfRequest.incrementAndGet();
            while (currCount > 20) {
                try {
                    Thread.sleep(250);
                    //System.out.println("To many query! sleep 250ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currCount = countOfRequest.get();
            }
            String id = "tt" + String.format("%07d", intId);
            api.getFilmById(id).enqueue(new BrutForceCallback(id));
        }
        System.out.print("Finish time - " + new Date());
    }
}
