package com.siongriffiths.nppcdatavisualiser.data.doas;

import com.siongriffiths.nppcdatavisualiser.data.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */
@Repository
public interface MetaDataDao extends JpaRepository<Metadata, Integer>{
}
