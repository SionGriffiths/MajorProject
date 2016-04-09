package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
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


    Plant findByBarCode(String barCode);

    void deleteByExperiment(Experiment experiment);

}