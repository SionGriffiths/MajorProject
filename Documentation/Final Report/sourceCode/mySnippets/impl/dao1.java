public Plant getPlantByBarcode(String barcode) {

    String hibernateQuery = "FROM Plant WHERE bar_code =:bar_code";
    Query query = getSession().createQuery(hibernateQuery);
    query.setParameter("bar_code", barcode);

    return (Plant)query.uniqueResult();
}