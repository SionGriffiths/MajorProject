package com.siongriffiths.nppcdatavisualiser.plants.daos;

import com.siongriffiths.nppcdatavisualiser.plants.PlantDay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Repository("plantDayDao")
@Transactional
public class PlantDayDaoImpl implements PlantDayDao {


    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void savePlantDay(PlantDay plantDay) {
        getSession().saveOrUpdate(plantDay);
    }

    @Override
    public PlantDay getPlantDayById(long Id) {
        return (PlantDay) getSession().get(PlantDay.class, Id);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
