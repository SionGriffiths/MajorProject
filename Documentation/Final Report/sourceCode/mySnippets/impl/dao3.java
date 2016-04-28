@Query("select p from Plant p join p.experiment e where e.experimentCode = :experimentCode ")
List<Plant> findByExperimentCode(@Param("experimentCode") String experimentCode);