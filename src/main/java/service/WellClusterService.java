package service;

import model.WellCluster;
import repository.WellClusterDao;
import repository.impl.WellClusterDaoImpl;

public class WellClusterService {
    private static final WellClusterService wellClusterService = new WellClusterService();

    private WellClusterService() {

    }

    public static WellClusterService getWellClusterService() {
        return wellClusterService;
    }

    WellClusterDao wellClusterDao = WellClusterDaoImpl.getWellClusterDao();

    public WellCluster save(WellCluster wellCluster) {
        return wellClusterDao.save(wellCluster);
    }

    public boolean delete(Long id) {
        return wellClusterDao.delete(id);
    }
}