package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
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

    Plant findByBarCode(String barCode);

}