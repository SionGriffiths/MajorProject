for(Integer i : plantAttribIndex){
    if(line[i] != null || !line[i].equals("")){
        Metadata data = plant.getMetadata();
        data.addDataAttribute(header[i], line[i]);
        plantManager.savePlant(plant);
    }
}