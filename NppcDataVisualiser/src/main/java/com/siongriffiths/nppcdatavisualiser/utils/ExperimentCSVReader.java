package com.siongriffiths.nppcdatavisualiser.utils;

import com.opencsv.CSVReader;
import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.service.TagManager;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Component
public class ExperimentCSVReader {

    @Autowired
    PlantManager plantManager;
    @Autowired
    TagManager tagManager;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN = "{{plant-a}}";
    private static final String PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN = "{{plant-t}}";
    private static final String PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN = "{{bc}}";

    private List<Integer> plantATypeColumnIndicies;
    private List<Integer> plantTTypeColumnIndicies;
    private int barcodeColumn;


    public void doParse(){
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("I:/Diss/MajorProject/Data/O7/O7data_15_03_2016-LAYOUT+GS.csv"));
            plantTTypeColumnIndicies = new ArrayList<>();
            plantATypeColumnIndicies = new ArrayList<>();

            //process header into the column mappings?
            //// TODO: 16/03/2016 use json and have the mappings as a 'control object' that can be used for data routing?
            String[] header = reader.readNext();
            processHeaderColumns(header);

            List<String[]> csvLines = reader.readAll();
            for(String[] line : csvLines) {
                enrichPlantRecord(barcodeColumn, plantATypeColumnIndicies, plantTTypeColumnIndicies, header, line);
            }
        } catch (FileNotFoundException e) {
            logger.error("CSV file not found" , e);
        } catch (IOException e) {
            logger.error("Couldn't parse line of file" , e);
        }
    }


    private void processHeaderColumns(String[] header){

        for(int i = 0; i < header.length; i++){
            if(header[i].startsWith(PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN)){
                plantATypeColumnIndicies.add(i);
            } else if (header[i].startsWith(PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN)){
                plantTTypeColumnIndicies.add(i);
            } else if (header[i].startsWith(PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN)) {
                barcodeColumn = i;
            }
        }
    }

    private void enrichPlantRecord(int barCodeIndex, List<Integer> plantAttribIndex,
                                   List<Integer> plantTagIndex, String[] header, String[] line){
        String barCode = line[barCodeIndex];
        Plant plant =  plantManager.getPlantByBarcode(barCode);
        if(plant != null) {
            enrichPlantTags(plantTagIndex, header, line, plant);
            enrichPlantAttribs(plantAttribIndex, header, line, plant);
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
               String attribKey = cleanAnnotation(header[i]);
                Metadata data = plant.getPlantMetaData();
                data.addDataAttribute(attribKey,line[i]);
                plantManager.savePlant(plant);
            }
        }
    }

    private String cleanAnnotation(String toClean){
        toClean =  toClean.replace(PLANT_ATTRIBUTE_COLUMN_MAPPING_STRING_PATTERN, "");
        toClean =  toClean.replace(PLANT_TAG_COLUMN_MAPPING_STRING_PATTERN, "");
        toClean =  toClean.replace(PLANT_BARCODE_COLUMN_MAPPING_STRING_PATTERN, "");
        return toClean;
    }

}
