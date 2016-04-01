package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;

import java.util.List;
import java.util.Set;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface TagManager {

    TagData getTagByContent(String content);
    List<TagData> getAllTags();
    Set<TagData> getByExperimentForPlants(Experiment experiment);
    TagData createOrGetTag(String content);
    void saveTagData(TagData tagData);
    void resetAll();
}
