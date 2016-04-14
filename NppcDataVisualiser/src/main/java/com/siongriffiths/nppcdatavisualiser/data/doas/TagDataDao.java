package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * TagDataDao is a spring data repository class providing persistence layer access to the system for
 * TagData objects.
 */
@Repository("tagDataDao")
public interface TagDataDao extends JpaRepository<TagData,Long>{

    /**
     * Finds the TagData object associated with specific tag content
     * @param content the content identifying a TagData object
     * @return the TagData object identified by the parameter content
     */
    TagData findByTagContent(String content);

    /**
     * Queries the database using a JPA query to find all tags associated with plants associated with a given experiment
     * @param experiment the currently selected experiment
     * @return a collection of TagData selected by the query
     */
    @Query("select t from Plant p join p.tags t where p.experiment = :experiment ")
    Set<TagData> findByExperimentForPlant(@Param("experiment")Experiment experiment);

    /**
     * Queries the database using a JPA query to find all tags associated with plantDays associated with a given experiment
     * @param experiment the currently selected experiment
     * @return a collection of TagData selected by the query
     */
    @Query("select t from PlantDay pd join pd.tags t where pd.plant.experiment = :experiment ")
    Set<TagData> findByExperimentForPlantDay(@Param("experiment")Experiment experiment);

}
