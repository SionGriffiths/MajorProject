package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 28/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantDao is a spring data repository class providing persistence layer access to the system for
 * Plant objects.
 */
@Repository("plantDao")
public interface PlantDao extends JpaRepository<Plant,Long> {

    /**
     * Finds plants associated with a particular TagData and a particular experiment, using the ID of a TagData instance
     * @param id the id of the TagData
     * @param experiment the experiment
     * @return a List of plants associated with the TagData and the Experiment
     */
    @Query("select p from Plant p join p.tags tag where tag.id = :tagId and p.experiment = :experiment")
    List<Plant> findByTagDataForExperiment(@Param("tagId") long id, @Param("experiment") Experiment experiment);

    /**
     * Finds plants for a given experiment which have been tagged with at least 1 TagData
     * @param experiment the Experiment
     * @return a List of tagged plants
     */
    @Query("select p from Plant p where size(p.tags) > 0 and p.experiment = :experiment")
    List<Plant> findPlantsWithTagsByExperiment(@Param("experiment")Experiment experiment);

    /**
     * Returns a pagination page of plants for a given Experiment and page settings
     * @param experimentCode code of the Experiment
     * @param pageable pagination page settings
     * @return a pagination page of plants using the current experiment and page settings
     */
    @Query("select p from Plant p join p.experiment e where e.experimentCode = :experimentCode ")
    Page<Plant> findByExperimentCode(@Param("experimentCode") String experimentCode, Pageable pageable);

    /**
     * Returns a list of all plants associated with an Experiment
     * @param experimentCode code of the Experiement
     * @return list of all plants associated with the Experiment
     */
    @Query("select p from Plant p join p.experiment e where e.experimentCode = :experimentCode ")
    List<Plant> findByExperimentCode(@Param("experimentCode") String experimentCode);


    /**
     * Finds plants based on two key value pairs within the plants metadata map. This method is used for finding plants
     * corresponding to data points in visualisations in the graph view. Each key/value pair represents an axis label and
     * value for the data point.
     * @param attrKey1 key for the first pair
     * @param attrVal1 value for the first pair
     * @param attrKey2 key for the second pair
     * @param attrVal2 value for the second pair
     * @return a list of all plants matching the given key value pairs
     */
    @Query("select p from Plant p join p.metadata.dataAttributes md join p.metadata.dataAttributes md2 where " +
            "(key(md) =:attrKey1 and :attrVal1 in (value(md)) ) and " +
            "(key(md2) =:attrKey2 and :attrVal2 in (value(md2)) )")
    List<Plant> findByTwoAttrributeValues(@Param("attrKey1") String attrKey1,
                                          @Param("attrVal1") String attrVal1,
                                          @Param("attrKey2") String attrKey2,
                                          @Param("attrVal2") String attrVal2);

    /**
     * Finds the plant corresponding to a particular barcaode
     * @param barCode the barcode
     * @return the plant corresponding to the barcode
     */
    Plant findByBarCode(String barCode);

    /**
     * Deletes all plants associated with a particular Experiment
     * @param experiment the experiment
     */
    void deleteByExperiment(Experiment experiment);

}