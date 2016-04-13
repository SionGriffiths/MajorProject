package com.siongriffiths.nppcdatavisualiser.plants.service;

import com.siongriffiths.nppcdatavisualiser.plants.PlantImage;
import com.siongriffiths.nppcdatavisualiser.plants.daos.PlantImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 07/03/2016.
 *
 * @author Siôn Griffiths / sig2@aber.ac.uk
 * PlantImageManagerImpl implements all business logic and behaviour as defined in the PlantImageManager interface.
 */

@Service("plantImageManager")
public class PlantImageManagerImpl implements PlantImageManager {

    /**
     * The PlantImageDao repository used to provide persistence layer interactions for PlantImage objects
     */
    @Autowired
    private PlantImageDao plantImageDao;

    /**
     * Finds a PlantImage associated with a particular file path or creates one if it doesn't exist
     * @param path the file path
     * @return the retrieved or created PlantImage
     */
    @Override
    public PlantImage getOrCreatePlantImageByPath(String path) {
        PlantImage image = plantImageDao.findByFilePath(path);

         if (image == null){
             return new PlantImage(path);
         } else {
             return image;
         }
    }


}
