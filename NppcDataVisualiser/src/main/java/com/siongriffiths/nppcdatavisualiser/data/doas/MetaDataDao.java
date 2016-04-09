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
 *
 * MetaDataDao is a spring data repository class providing persistence layer access to the system for
 * Metadata objects.
 */
@Repository
public interface MetaDataDao extends JpaRepository<Metadata, Integer>{

    /**
     * Queries the database using a JPA query to find all metadata objects with a specific key
     * @param key the key used for the query
     * @return a list of metadata objects with the specified key
     */
    @Query("SELECT m FROM Metadata m JOIN m.dataAttributes d WHERE KEY(d) = :key")
    List<Metadata> findByDataAttributeKey(@Param("key") String key);

    /**
     * Queries the database using a JPA query to find all metadata objects associated with a given plant
     * in the currently selected experiement
     * @param experiment the currently selected experiment
     * @return a list of metadata objects found by the query
     */
    @Query("select metadata from Plant p where p.experiment = :experiment ")
    List<Metadata> findByExperimentForPlant(@Param("experiment")Experiment experiment);

    /**
     * Queries the database using a JPA query to find all metadata objects associated with a given plantday
     * in the currently selected experiement
     * @param experiment the currently selected experiment
     * @return a list of metadata objects found by the query
     */
    @Query("select metadata from PlantDay pd where pd.plant.experiment = :experiment ")
    List<Metadata> findByExperimentForPlantDay(@Param("experiment")Experiment experiment);
}
