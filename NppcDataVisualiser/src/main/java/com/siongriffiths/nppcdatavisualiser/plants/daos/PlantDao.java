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

    @Query("select p from Plant p join p.tags tag where tag.id = :tagId ")
    List<Plant> findByTagData(@Param("tagId") long id);

    Plant findByBarCode(String barCode);

}