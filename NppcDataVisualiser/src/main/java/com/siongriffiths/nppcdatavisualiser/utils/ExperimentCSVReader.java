package com.siongriffiths.nppcdatavisualiser.utils;

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

    private static final String PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{plant-a}}";
    private static final String PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN = "{{plant-t}}";
    private static final String PLANT_DAY_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{day-a}}";
    private static final String PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN = "{{bc}}";
    private static final String IN_HEADER_KEY_VALUE_PAIR_DELIMITER = "~~";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PlantManager plantManager;
    @Autowired
    PlantDayManager plantDayManager;
    @Autowired
    TagManager tagManager;
    private List<Integer> plantATypeColumnIndicies;
    private List<Integer> plantTTypeColumnIndicies;
    private List<Integer> plantDayATypeColumnIndicies;
    private int barcodeColumn;


    public void doParse(){

        try {
            CSVReader  reader = new CSVReader(new FileReader("I:/Diss/MajorProject/Data/O7/annotated.csv"));
            plantTTypeColumnIndicies = new ArrayList<>();
            plantATypeColumnIndicies = new ArrayList<>();
            plantDayATypeColumnIndicies = new ArrayList<>();

            String[] header = reader.readNext();
            processHeaderColumns(header);
            List<String[]> csvLines = reader.readAll();

            for(String[] line : csvLines) {
                enrichPlantRecord(barcodeColumn, plantATypeColumnIndicies,
                        plantTTypeColumnIndicies, plantDayATypeColumnIndicies, header, line);
            }

        } catch (FileNotFoundException e) {
            logger.error("CSV file not found" , e);
        } catch (IOException e) {
            logger.error("Couldn't parse line of file", e);
        }
    }


    private void processHeaderColumns(String[] header){

        //// TODO: 16/03/2016 regex based string clean up in one go would be best eh?

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

    // TODO: 16/03/2016 move these methods to a data service?

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

    private void enrichPlantTags(List<Integer> plantTagIndex, String[] header, String[] line, Plant plant){
        for(Integer i : plantTagIndex){
            if(line[i] != null || !line[i].equals("")){
                TagData tag = tagManager.createOrGetTag(line[i]);
                plantManager.tagPlant(tag,plant);
                tagManager.saveTagData(tag);
                plantManager.savePlant(plant);
            }
        }
    }

    private void enrichPlantAttribs(List<Integer> plantAttribIndex, String[] header, String[] line, Plant plant){
        for(Integer i : plantAttribIndex){
            if(line[i] != null || !line[i].equals("")){
                Metadata data = plant.getPlantMetaData();
                data.addDataAttribute(header[i], line[i]);
                plantManager.savePlant(plant);
            }
        }
    }

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
                    logger.debug("loaded plantDay");
                } catch (ParseException e) {
                    logger.error("Cannot parse column content to date. Content was : " + columnContent
                            + " | Plant barcode in line : " + plant.getBarCode(), e);
                }

                if (day != null) {
                    String[] splitHeader = headerContent.split(IN_HEADER_KEY_VALUE_PAIR_DELIMITER);
                    if (splitHeader.length >= 2) {
                        Metadata data = day.getPlantDayMetaData();
                        data.addDataAttribute(splitHeader[0], splitHeader[1]);
                        plantDayManager.savePlantDay(day);
                    }
                }

            }
        }


    }
    private String cleanAnnotation(String toClean){
//        toClean =  toClean.replace(PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN, "");
//        toClean =  toClean.replace(PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN, "");
//        toClean =  toClean.replace(PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN, "");
//        toClean = toClean.replaceAll("/\\{\\{([^}]+)\\}\\}/", "");
        return toClean;
    }

}
