package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.TagData;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 08/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Repository("tagDataDao")
@Transactional
public class TagDataDaoImpl implements TagDataDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TagData getTagByContent(String content) {
        Criteria criteria = getSession().createCriteria(TagData.class);
        TagData tagdata = (TagData)criteria.add(Restrictions.eq("tagContent", content)).uniqueResult();

        return tagdata;
    }

    @Override
    public void saveTagData(TagData tagData){
        getSession().saveOrUpdate(tagData);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
