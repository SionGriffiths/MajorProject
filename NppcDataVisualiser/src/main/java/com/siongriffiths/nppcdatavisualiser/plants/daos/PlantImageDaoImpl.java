package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Repository("plantImageDao")
@Transactional
public class PlantImageDaoImpl implements PlantImageDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void savePlantImage(PlantImage plantImage){
        getSession().saveOrUpdate(plantImage);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
