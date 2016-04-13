package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantDayDao is a spring data repository class providing persistence layer access to the system for
 * PlantDay objects.
 *
 * Some queries were initially adapted from the following Stackoverflow answer:
 * http://stackoverflow.com/a/15155131/6044187
 */
@Repository("plantDayDao")
public interface PlantDayDao extends JpaRepository<PlantDay, Long> {


    /**
     * Finds  plantDay associated with a particular TagData, using the ID of a TagData instance
     * @param id the id of the TagData
     * @return a List of plantsDay associated with the TagData
     */
    @Query("select pd from PlantDay pd join pd.tags tag where tag.id = :tagId ")
    List<PlantDay> findByTagData(@Param("tagId") long id);

    /**
     * Finds PlantDays associated with a particular TagData and a particular experiment, using the ID of a TagData instance
     * @param id the id of the TagData
     * @param experiment the experiment
     * @return a List of PlantDays associated with the TagData and the Experiement
     */
    @Query("select pd from PlantDay pd join pd.tags tag where tag.id = :tagId and pd.plant.experiment =:experiment")
    List<PlantDay> findByTagDataForExperiment(@Param("tagId") long id, @Param("experiment") Experiment experiment);

    /**
     * Finds PlantDays associated with an Experiment
     * @param experiment the Experiment
     * @return a list of PlantDays associated with the Experiment
     */
    @Query("select pd from PlantDay pd join pd.plant plant where plant.experiment = :experiment")
    List<PlantDay> findByExperiment(@Param("experiment")Experiment experiment);

    /**
     * Finds PlantDays for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged PlantDays
     */
    @Query("select pd from PlantDay pd join pd.plant plant where plant.experiment = :experiment and size(pd.tags) > 0")
    List<PlantDay> findPlantDaysWithTagsByExperiment(@Param("experiment")Experiment experiment);

    /**
     * Find a PlantDay associated with a particular Metadata instance
     * @param Metadata the Metadata instance
     * @return the PlantDay associated with the Metadata
     */
    PlantDay findByMetadata(Metadata Metadata);

    /**
     * Find a PlantDay associated with a particular plant and date
     * @param plant the plant
     * @param date the date
     * @return the PlantDay associated with the plant and the date
     */
    PlantDay findByPlantAndDate(Plant plant, Date date);

    /**
     * Returns a pagination page of plants for a given plant and page settings
     * @param plant the plant
     * @param pageable pagination page settings
     * @return a pagination page of PlantDays using the current plant and page settings
     */
    Page<PlantDay> findByPlant(Plant plant, Pageable pageable);
}
