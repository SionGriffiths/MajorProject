package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;

import java.util.List;
import java.util.Set;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 *
 * The TagManager interface defines the public business processes for managing TagData objects
 * and their interactions within the system.
 */
public interface TagManager {

    /**
     * Finds a specific TagData instance corresponding to a particular tag content
     * @param content the tag content
     * @return a TagData instance corresponding to the specified content
     */
    TagData getTagByContent(String content);

    /**
     * Gets all TagData instances in the system
     * @return a list of all TagData instances
     */
    List<TagData> getAllTags();

    /**
     * Gets all TagData instances associated with plants in a given experiment
     * @param experiment the Experiment instance
     * @return a collection of TagData instances associated with plants for a given experiment
     */
    Set<TagData> getByExperimentForPlants(Experiment experiment);


    /**
     * Gets all TagData instances associated with plantDays in a given experiment
     * @param experiment the Experiment instance
     * @return a collection of TagData instances associated with plantDays for a given experiment
     */
    Set<TagData> getByExperimentForPlantDays(Experiment experiment);

    /**
     * Find a TagData associated with particular content or create it if it doesn't exist
     * @param content the tag content
     * @return the TagData retrieved or created which corresponds to the parameter content
     */
    TagData createOrGetTag(String content);

    /**
     * Persists a TagData instance
     * @param tagData the TagData to be persisted
     */
    void saveTagData(TagData tagData);

    /**
     * Resets all TagData in the system
     */
    void resetAll();

    /**
     * Resets all TagData in the system for a given experiment
     * @param experiment the Experiment instance
     */
    void resetForExperiment(Experiment experiment);
}
