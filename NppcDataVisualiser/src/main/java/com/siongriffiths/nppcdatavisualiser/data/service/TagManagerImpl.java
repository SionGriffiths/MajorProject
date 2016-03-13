package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.data.doas.TagDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Service("tagManager")
public class TagManagerImpl implements TagManager {

    @Autowired
    private TagDataDao tagDataDao;


    @Override
    public void saveTagData(TagData tagData) {
        tagDataDao.save(tagData);
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
    public TagData createOrGetTag(String content) {

        TagData queryTag = tagDataDao.findByTagContent(content);
        return (queryTag != null) ? queryTag : new TagData(content);
    }


}
