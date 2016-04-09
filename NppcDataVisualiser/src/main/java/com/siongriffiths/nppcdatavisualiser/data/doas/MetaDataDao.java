package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import com.siongriffiths.nppcdatavisualiser.experiment.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository
public interface MetaDataDao extends JpaRepository<Metadata, Integer>{

    @Query("SELECT m FROM Metadata m JOIN m.dataAttributes d WHERE KEY(d) = :key")
    List<Metadata> findByDataAttributeKey(@Param("key") String key);

    @Query("select metadata from Plant p where p.experiment = :experiment ")
    List<Metadata> findByExperimentForPlant(@Param("experiment")Experiment experimentCode);

    @Query("select metadata from PlantDay pd where pd.plant.experiment = :experiment ")
    List<Metadata> findByExperimentForPlantDay(@Param("experiment")Experiment experimentCode);
}
