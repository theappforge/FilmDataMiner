package utils;

import fromFilmTitle.FilmInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import utils.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 22.11.2016.
 */
public class CsvFileWriter {

    //Delimiter used in CSV file
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static final String CSV_NAME = "films.csv";

    //CSV file header
    public static final String[] FILE_HEADER = {"Title", "Poster", "Year", "Rated", "Released", "Runtime", "Genre", "Director", "Writer",
            "Actor", "Plot", "KeyWorld", "Budget", "Opening Weekend", "Gross", "CountUserReviews", "CountCriticalReviews", "ProductionCompany",
            "Language", "Country", "imdbRating", "imdbVotes", "Metascore", "imdbID", "Type"};

    public synchronized static void writeCsvFile(FilmInfo filmInfo) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {
            //initialize FileWriter object
            fileWriter = new FileWriter(CSV_NAME, true);

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            List filmDataRecord = filmInfo.getDataList();
            csvFilePrinter.printRecord(filmDataRecord);
         //   Log.writeLog(filmInfo.getTitle(), "CSV file was created successfully !!!");

        } catch (Exception e) {
            Log.writeLog(filmInfo.getTitle(), "Error in utils.CsvFileWriter !!!");
            Log.error(filmInfo.getTitle());
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                Log.writeLog(filmInfo.getTitle(), "Error while flushing/closing fileWriter/csvPrinter !!!");
                Log.error(filmInfo.getTitle());
                e.printStackTrace();
            }
        }
    }

    public synchronized static void writeHeaders() {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {
            //initialize FileWriter object
            fileWriter = new FileWriter(CSV_NAME, true);

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


}
