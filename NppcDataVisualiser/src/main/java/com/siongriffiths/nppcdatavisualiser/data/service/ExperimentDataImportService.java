package com.siongriffiths.nppcdatavisualiser.data.service;

/**
 * Created on 16/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * The ExperimentDataImportService interface defines public services and business logic for
 * handling the importing of experiment data into the system
 */
public interface ExperimentDataImportService {

    /**
     * Parses a CSV file annotated with the system specific annotations to enable data routing on a column
     * by column basis
     * @param filePath the path to the annotated file
     */
    void parseAnnotatedExperimentDataCSVFile(String filePath);

}
