package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository("plantDayDao")
public interface PlantDayDao extends JpaRepository<PlantDay, Long> {

}
