package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.utils.ExperimentCSVReader;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantDayManager;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 16/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * ExperimentDataImportServiceImpl implements methods defined in ExperimentDataImportService
 */
@Service("experimentDataImportService")
public class ExperimentDataImportServiceImpl implements ExperimentDataImportService {

    /**
     * Logger instance for this class
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * String constants for csv column annotations
     */
    private static final String PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{plant-a}}";
    private static final String PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN = "{{plant-t}}";
    private static final String PLANT_DAY_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{day-a}}";
    private static final String PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN = "{{bc}}";
    private static final String IN_HEADER_KEY_VALUE_PAIR_DELIMITER = "~~";

    /**
     * PlantManager, a service class providing access to business logic and persistence for Plant objects
     */
    @Autowired
    private PlantManager plantManager;

    /**
     * PlantDayManager, a service class providing access to business logic and persistence for PlantDay objects
     */
    @Autowired
    private  PlantDayManager plantDayManager;

    /**
     * TagManager, a service class providing access to business logic and persistence for TagData objects
     */
    @Autowired
    private TagManager tagManager;

    /**
     * ExperimentCSVReader, a simple bean class which parses a csv file into a list of lines split into columns
     */
    @Autowired
    private ExperimentCSVReader experimentCSVReader;

    private List<Integer> plantATypeColumnIndicies;
    private List<Integer> plantTTypeColumnIndicies;
    private List<Integer> plantDayATypeColumnIndicies;
    private int barcodeColumn;


    /**
     * Parses a CSV file annotated with the system specific annotations to enable data routing on a column
     * by column basis
     * @param filePath the path to the annotated file
     */
    public void parseAnnotatedExperimentDataCSVFile(String filePath){
        List<String[]> parsedFile = experimentCSVReader.doParse(filePath);

        if(parsedFile != null) {
            String[] header = parsedFile.get(0);

            plantTTypeColumnIndicies = new ArrayList<>();
            plantATypeColumnIndicies = new ArrayList<>();
            plantDayATypeColumnIndicies = new ArrayList<>();

            processHeaderColumns(header);

            parsedFile.remove(0);
            processCsvFile(parsedFile, header);
        }

    }

    /**
     * Processes the tokenised header columns of a CSV formatted file containing experiment data
     * @param header A String array of the tokenised header columns
     */
    private void processHeaderColumns(String[] header){

        for(int i = 0; i < header.length; i++){
            if (header[i].contains(PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN)) {
                header[i] = header[i].replace(PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN, "");
                plantATypeColumnIndicies.add(i);
            } else if (header[i].contains(PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN)) {
                header[i] = header[i].replace(PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN, "");
                plantTTypeColumnIndicies.add(i);
            } else if (header[i].contains(PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN)) {
                header[i] = header[i].replace(PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN, "");
                barcodeColumn = i;
            } else if (header[i].contains(PLANT_DAY_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN)) {
                header[i] = header[i].replace(PLANT_DAY_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN, "");
                plantDayATypeColumnIndicies.add(i);
            }
        }
    }

    /**
     * Process the content of a CSV data file
     * @param csvFile The tokenised csv columns as a list of string arrays
     * @param header The header columns for the csv file
     */
    private void processCsvFile(List<String[]> csvFile, String[] header){
        for(String[] line : csvFile) {
            enrichPlantRecord(barcodeColumn, plantATypeColumnIndicies,
                    plantTTypeColumnIndicies, plantDayATypeColumnIndicies, header, line);
        }
    }

    /**
     * Route a line from csv column data to the correcponding plant or plantDay attribute
     * @param barCodeIndex index corresponding to plant barcode in the line array
     * @param plantAttribIndex list of indices corresponding to plant attribute columns in the line array
     * @param plantTagIndex list of indices corresponding to plant tags columns in the line array
     * @param dayAttribIndex list of indices corresponding to plantDay attribute columns in the line array
     * @param header csv file header columns
     * @param line a line of the csv file
     */
    private void enrichPlantRecord(int barCodeIndex, List<Integer> plantAttribIndex,
                                   List<Integer> plantTagIndex, List<Integer> dayAttribIndex, String[] header, String[] line) {
        String barCode = line[barCodeIndex];
        Plant plant =  plantManager.getPlantByBarcode(barCode);
        if(plant != null) {
            enrichPlantTags(plantTagIndex, header, line, plant);
            enrichPlantAttribs(plantAttribIndex, header, line, plant);
            enrichPlantDayAttribs(dayAttribIndex, header, line, plant);
            logger.info("Enrich data for plant " + barCode);
        } else {
            logger.info("No plant found for barcode " + barCode );
        }
    }

    /**
     * Route data from csv columns to plant tags
     * @param plantTagIndex list of indices corresponding to plant tags columns in the line array
     * @param header csv file header columns
     * @param line a line of the csv file
     * @param plant the Plant instance
     */
    private void enrichPlantTags(List<Integer> plantTagIndex, String[] header, String[] line, Plant plant){
        for(Integer i : plantTagIndex){
            if(line[i] != null && !line[i].equals("")){
                String escaped = StringUtils.replace(line[i],"."," ");
                TagData tag = tagManager.createOrGetTag(escaped);
                plantManager.tagPlant(tag,plant);
                tagManager.saveTagData(tag);
                plantManager.savePlant(plant);
            }
        }
    }

    /**
     * Route data from csv columns to plant attributes
     * @param plantAttribIndex list of indices corresponding to plant attribute columns in the line array
     * @param header csv file header columns
     * @param line a line of the csv file
     * @param plant the Plant instance
     */
    private void enrichPlantAttribs(List<Integer> plantAttribIndex, String[] header, String[] line, Plant plant){
        for(Integer i : plantAttribIndex){
            if(line[i] != null || !line[i].equals("")){
                Metadata data = plant.getMetadata();
                data.addDataAttribute(header[i], line[i]);
                plantManager.savePlant(plant);
            }
        }
    }

    /**
     * Route data from csv to plantDay attributes
     * @param dayAttribIndex list of indices corresponding to plantDay attribute columns in the line array
     * @param header csv file header columns
     * @param line a line of the csv file
     * @param plant the Plant instance
     */
    private void enrichPlantDayAttribs(List<Integer> dayAttribIndex, String[] header, String[] line, Plant plant) {
        for (Integer i : dayAttribIndex) {
            if (line[i] != null && !line[i].equals("")) {
                String columnContent = line[i];
                String headerContent = header[i];
                PlantDay day = null;
                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = format.parse(columnContent);
                    day = plantDayManager.findByPlantAndDate(plant, date);
                } catch (ParseException e) {
                    logger.error("Cannot parse column content to date. Content was : " + columnContent
                            + " | Plant barcode in line : " + plant.getBarCode(), e);
                }

                if (day != null) {
                    String[] splitHeader = headerContent.split(IN_HEADER_KEY_VALUE_PAIR_DELIMITER);
                    if (splitHeader.length >= 2) {
                        Metadata data = day.getMetadata();
                        data.addDataAttribute(splitHeader[0], splitHeader[1]);
                        plantDayManager.savePlantDay(day);
                    }
                }
            }
        }
    }

}
