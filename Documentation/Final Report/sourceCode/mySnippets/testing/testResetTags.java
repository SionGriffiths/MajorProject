    $$@Test
    public void resetTagsForExperiment()  {
        Long id = 10L;
        Plant plant = plantManager.getPlantByID(id);
        Experiment experiment = plant.getExperiment();

        assertEquals("Expected number of tags to be 2", 2 , plant.getTags().size());

        plantManager.resetTagsForExperiment(experiment);
        assertEquals("Expected number of tags to be 0", 0 , plant.getTags().size());
    }