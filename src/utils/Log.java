package utils;

import java.io.*;
import java.util.Date;

/**
 * Created by User on 22.11.2016.
 */
public final class Log {

    public synchronized static void writeLog(String filmId, String log) {
        try (FileWriter fw = new FileWriter("system log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String outString =  new Date().toString() + " | " + filmId + ": " + log + '\n';
            out.println(outString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void error(String filmId) {
        try (FileWriter fw = new FileWriter("error film.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String outString = filmId + '\n';
            out.println(outString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void writeGoodId(String id){
        try (FileWriter fw = new FileWriter("good_imdb_ids.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String outString = id + '\n';
            out.println(outString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void writeLiveId(String id){
        try (FileWriter fw = new FileWriter("live_imdb_ids.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String outString = id + '\n';
            out.println(outString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
