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
 */
@Repository("plantDao")
public interface PlantDao extends JpaRepository<Plant,Long> {

    @Query("select p from Plant p join p.tags tag where tag.id = :tagId ")
    List<Plant> findByTagData(@Param("tagId") long id);

    @Query("select p from Plant p join p.tags tag where tag.id = :tagId and p.experiment = :experiment")
    List<Plant> findByTagDataForExperiment(@Param("tagId") long id, @Param("experiment") Experiment experiment);

    List<Plant> findByExperiment(Experiment experiment);

    @Query("select p from Plant p where size(p.tags) > 0 and p.experiment = :experiment")
    List<Plant> findPlantsWithTagsByExperiment(@Param("experiment")Experiment experiment);

    @Query("select p from Plant p join p.experiment e where e.experimentCode = :experimentCode ")
    Page<Plant> findByExperimentCode(@Param("experimentCode") String experimentCode, Pageable pageable);

    @Query("select p from Plant p join p.experiment e where e.experimentCode = :experimentCode ")
    List<Plant> findByExperimentCode(@Param("experimentCode") String experimentCode);

    @Query("select p from Plant p join p.metadata.dataAttributes md where " +
            "(key(md) =:attrKey1 and :attrVal1 in (value(md)) )")
    List<Plant> findByTwoAttrributeValues(@Param("attrKey1") String attrKey1,
                                          @Param("attrVal1") String attrVal1);

    @Query("select p from Plant p join p.metadata.dataAttributes md join p.metadata.dataAttributes md2 where " +
            "(key(md) =:attrKey1 and :attrVal1 in (value(md)) ) and " +
            "(key(md2) =:attrKey2 and :attrVal2 in (value(md2)) )")
    List<Plant> findByTwoAttrributeValues(@Param("attrKey1") String attrKey1,
                                          @Param("attrVal1") String attrVal1,
                                          @Param("attrKey2") String attrKey2,
                                          @Param("attrVal2") String attrVal2);

    Plant findByBarCode(String barCode);

    void deleteByExperiment(Experiment experiment);



}