package service;

import model.WellCluster;
import repository.WellClusterDao;
import repository.impl.WellClusterDaoImpl;

import java.util.List;
import java.util.Optional;

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

    public Optional<WellCluster> getById(Long id) {
        return wellClusterDao.getById(id);
    }

    public void update(WellCluster wellCluster) {
        wellClusterDao.update(wellCluster);
    }

    public List<WellCluster> findAll() {
        return wellClusterDao.findAll();
    }
}