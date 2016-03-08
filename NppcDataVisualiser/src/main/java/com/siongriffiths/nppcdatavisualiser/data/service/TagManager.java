package com.siongriffiths.nppcdatavisualiser.data.service;

import com.siongriffiths.nppcdatavisualiser.data.TagData;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
public interface TagManager {

    TagData createOrGetTag(String content);
    void saveTagData(TagData tagData);
}
