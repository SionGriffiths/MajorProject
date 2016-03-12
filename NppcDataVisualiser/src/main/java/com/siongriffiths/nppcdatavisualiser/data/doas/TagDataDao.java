package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository("tagDataDao")
public interface TagDataDao extends JpaRepository<TagData,Long>{

    TagData findByTagContent(String content);


}
