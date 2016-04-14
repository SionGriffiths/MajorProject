package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.doas.TagDataDao;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * TagManagerImpl implements all publicly defined business processes defined in TagManager
 */
@Transactional
@Service("tagManager")
public class TagManagerImpl implements TagManager {


    /**
     * The TagDataDao repository used to provide persistence layer interactions for TagData objects
     */
    @Autowired
    private TagDataDao tagDataDao;


    /**
     * Persists a TagData instance
     * @param tagData the TagData to be persisted
     */
    @Override
    public void saveTagData(TagData tagData) {
        tagDataDao.save(tagData);
    }

    /**
     * Finds a TagData instance corresponding to an ID
     * @param id the ID
     * @return the TagData instance corresponding to the ID
     */
    @Override
    public TagData findByID(long id) {
        return tagDataDao.findOne(id);
    }


    /**
     * Finds a specific TagData instance corresponding to a particular tag content
     * @param content the tag content
     * @return a TagData instance corresponding to the specified content
     */
    @Override
    public TagData getTagByContent(String content) {
        return tagDataDao.findByTagContent(content);
    }

    /**
     * Gets all TagData instances in the system
     * @return a list of all TagData instances
     */
    @Override
    public List<TagData> getAllTags() {
        return tagDataDao.findAll();
    }

    /**
     * Gets all TagData instances associated with plants in a given experiment
     * @param experiment the Experiment instance
     * @return a collection of TagData instances associated with plants for a given experiment
     */
    @Override
    public Set<TagData> getByExperimentForPlants(Experiment experiment) {
        return tagDataDao.findByExperimentForPlant(experiment);
    }

    /**
     * Gets all TagData instances associated with plantDays in a given experiment
     * @param experiment the Experiment instance
     * @return a collection of TagData instances associated with plantDays for a given experiment
     */
    @Override
    public Set<TagData> getByExperimentForPlantDays(Experiment experiment) {
        return tagDataDao.findByExperimentForPlantDay(experiment);
    }

    /**
     * Find a TagData associated with particular content or create it if it doesn't exist
     * @param content the tag content
     * @return the TagData retrieved or created which corresponds to the parameter content
     */
    @Override
    public TagData createOrGetTag(String content) {
        TagData queryTag = tagDataDao.findByTagContent(content);
        return (queryTag != null) ? queryTag : new TagData(content);
    }


}
