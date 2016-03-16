package com.siongriffiths.nppcdatavisualiser.data.utils;

import com.opencsv.CSVReader;
import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 15/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Component
public class ExperimentCSVReader {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


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
