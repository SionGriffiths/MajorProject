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
 */
@Transactional
@Service("tagManager")
public class TagManagerImpl implements TagManager {

    @Autowired
    private TagDataDao tagDataDao;


    @Override
    public void saveTagData(TagData tagData) {
        tagDataDao.save(tagData);
    }

    @Override
    public void resetAll() {
        tagDataDao.deleteAll();
    }

    @Override
    public void resetForExperiment(Experiment experiment) {

    }

    @Override
    public TagData getTagByContent(String content) {
        return tagDataDao.findByTagContent(content);
    }

    @Override
    public List<TagData> getAllTags() {
        return tagDataDao.findAll();
    }

    @Override
    public Set<TagData> getByExperimentForPlants(Experiment experiment) {
        return tagDataDao.findByExperimentForPlant(experiment);
    }

    @Override
    public Set<TagData> getByExperimentForPlantDays(Experiment experiment) {
        return tagDataDao.findByExperimentForPlantDay(experiment);
    }

    @Override
    public TagData createOrGetTag(String content) {

        TagData queryTag = tagDataDao.findByTagContent(content);
        return (queryTag != null) ? queryTag : new TagData(content);
    }


}
