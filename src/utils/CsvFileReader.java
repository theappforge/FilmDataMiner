package utils;

import fromFilmTitle.FilmInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22.11.2016.
 */
public class CsvFileReader {

    //CSV file header
    private static final String[] FILE_HEADER_MAPPING = {"id", "firstName", "lastName", "gender", "age"};


    public static void readCsvFile() {

        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        //Create the CSVFormat object with the header mapping
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(CsvFileWriter.FILE_HEADER);

        try {
            //Create a new list of student to be filled by CSV file data
            List films = new ArrayList();
            //initialize FileReader object
            fileReader = new FileReader(CsvFileWriter.CSV_NAME);
            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            //Get a list of CSV file records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                //Create a new student object and fill his data
                record.get(CsvFileWriter.FILE_HEADER[0]);
                FilmInfo film = new FilmInfo(record.get(CsvFileWriter.FILE_HEADER[0]), record.get(CsvFileWriter.FILE_HEADER[1]), record.get(CsvFileWriter.FILE_HEADER[2]), record.get(CsvFileWriter.FILE_HEADER[3]),
                        record.get(CsvFileWriter.FILE_HEADER[4]), record.get(CsvFileWriter.FILE_HEADER[5]), record.get(CsvFileWriter.FILE_HEADER[6]), record.get(CsvFileWriter.FILE_HEADER[7]),
                        record.get(CsvFileWriter.FILE_HEADER[8]), record.get(CsvFileWriter.FILE_HEADER[9]), record.get(CsvFileWriter.FILE_HEADER[10]), record.get(CsvFileWriter.FILE_HEADER[11]),
                        record.get(CsvFileWriter.FILE_HEADER[12]), record.get(CsvFileWriter.FILE_HEADER[13]), record.get(CsvFileWriter.FILE_HEADER[14]), record.get(CsvFileWriter.FILE_HEADER[15]));
                System.out.println(film.toString());
            }
        } catch (Exception e) {
            System.out.println("Error in utils.CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }

    }
}
