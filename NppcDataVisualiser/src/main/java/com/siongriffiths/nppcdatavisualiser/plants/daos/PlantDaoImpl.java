package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.Plant;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 27/02/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository("plantDao")
@Transactional
public class PlantDaoImpl implements PlantDao{

    public static final Logger LOGGER = Logger.getLogger(PlantDaoImpl.class);


    @Autowired
    private SessionFactory sessionFactory;


    public void savePlant(Plant plant){
        getSession().saveOrUpdate(plant);
    }

    // TODO: 28/02/2016 - Deal with the unchecked cast here, probably iterate through enitre returned list and check cast
    @Override
    public List<Plant> getAllPlants() {
        return getSession().createQuery("FROM Plant").list();
    }

    @Override
    public Plant getPlantByBarcode(String barcode) {

        String hibernateQuery = "FROM Plant WHERE bar_code =:bar_code";
        Query query = getSession().createQuery(hibernateQuery);
        query.setParameter("bar_code", barcode);

        return (Plant)query.uniqueResult();
    }


    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
