package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.sun.tools.javac.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository("tagDataDao")
public interface TagDataDao extends JpaRepository<TagData,Long>{

    TagData findByTagContent(String content);




    @Query("select t from Plant p join p.tags t where p.experiment = :experiment ")
    Set<TagData> findByExperimentForPlant(@Param("experiment")Experiment experiment);

}
