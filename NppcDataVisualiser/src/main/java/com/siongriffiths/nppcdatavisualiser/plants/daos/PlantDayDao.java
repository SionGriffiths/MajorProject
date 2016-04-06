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
 */
@Repository("plantDayDao")
public interface PlantDayDao extends JpaRepository<PlantDay, Long> {

//    http://stackoverflow.com/questions/15153877/jpa-select-from-collection-with-many-to-many-association
    @Query("select pd from PlantDay pd join pd.tags tag where tag.id = :tagId ")
    List<PlantDay> findByTagData(@Param("tagId") long id);

    @Query("select pd from PlantDay pd join pd.plant plant where plant.experiment = :experiment")
    List<PlantDay> findByExperiment(@Param("experiment")Experiment experiment);

    @Query("select pd from PlantDay pd join pd.plant plant where plant.experiment = :experiment and size(pd.tags) > 0")
    List<PlantDay> findPlantDaysWithTagsByExperiment(@Param("experiment")Experiment experiment);

    PlantDay findByMetadata(Metadata Metadata);

    PlantDay findByPlantAndDate(Plant plant, Date date);

    Page<PlantDay> findByPlant(Plant plant, Pageable pageable);
}
