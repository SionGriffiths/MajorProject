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
 */
@Service("experimentDataImportService")
public class ExperiemntDataImportServiceImpl implements ExperiemntDataImportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{plant-a}}";
    private static final String PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN = "{{plant-t}}";
    private static final String PLANT_DAY_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{day-a}}";
    private static final String PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN = "{{bc}}";
    private static final String IN_HEADER_KEY_VALUE_PAIR_DELIMITER = "~~";

    @Autowired
    private PlantManager plantManager;
    @Autowired
    private  PlantDayManager plantDayManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private ExperimentCSVReader experimentCSVReader;

    private List<Integer> plantATypeColumnIndicies;
    private List<Integer> plantTTypeColumnIndicies;
    private List<Integer> plantDayATypeColumnIndicies;
    private int barcodeColumn;



    public void parseAnnotatedExperiemntDataCSVFile(String filePath){
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


    public void processHeaderColumns(String[] header){

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

    private void processCsvFile(List<String[]> csvFile, String[] header){
        for(String[] line : csvFile) {
            enrichPlantRecord(barcodeColumn, plantATypeColumnIndicies,
                    plantTTypeColumnIndicies, plantDayATypeColumnIndicies, header, line);
        }
    }

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

}
