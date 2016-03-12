package plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.service.PlantManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created on 11/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 */

@Transactional
public class PlantManagerTest {


    @Autowired
    private PlantManager plantManager;



}
