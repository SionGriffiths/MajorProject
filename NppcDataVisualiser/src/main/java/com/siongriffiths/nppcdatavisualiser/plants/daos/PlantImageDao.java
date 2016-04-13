package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 *
 * PlantImageDao is a spring data repository class providing persistence layer access to the system for
 * PlantImage objects.
 */
@Repository("plantImageDao")
public interface PlantImageDao extends JpaRepository<PlantImage,Long>{

    /**
     * Finds a PlantImage associated with a particular file path
     * @param filePath the file path
     * @return the PlantImage associated with the file path
     */
    PlantImage findByFilePath(String filePath);

}
