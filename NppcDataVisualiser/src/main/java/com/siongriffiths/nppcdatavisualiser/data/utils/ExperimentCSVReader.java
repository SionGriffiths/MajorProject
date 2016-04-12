package com.siongriffiths.nppcdatavisualiser.data.utils;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created on 15/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ExperimentCSVReader is a simple spring bean class which wraps opencsv CSVReader for convenient use
 * in the system.
 *
 */
@Component
public class ExperimentCSVReader {

    /**
     * The logger for this class
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Parses a CSV file into a list of string arrays corresponding to lines and columns.
     * @param filePath the path to the CSV file
     * @return a list of string arrays corresponding to lines and columns.
     */
    public List<String[]> doParse(String filePath){

        List<String[]> csvLines = null;

        try {
            CSVReader  reader = new CSVReader(new FileReader(filePath));
            csvLines = reader.readAll();
        } catch (FileNotFoundException e) {
            logger.error("CSV file not found" , e);
        } catch (IOException e) {
            logger.error("Couldn't parse line of file", e);
        }

        return csvLines;
    }




}
