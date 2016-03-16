package com.siongriffiths.nppcdatavisualiser.data.service;

/**
 * Created on 16/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface ExperiemntDataImportService {

    void processHeaderColumns(String[] header);
    void parseAnnotatedExperiemntDataCSVFile(String filePath);

}
