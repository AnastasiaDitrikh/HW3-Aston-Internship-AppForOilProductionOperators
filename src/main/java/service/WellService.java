package service;

import model.Well;
import repository.WellDao;
import repository.impl.WellDaoImpl;

public class WellService {
    private static final WellService wellService = new WellService();

    private WellService() {

    }

    public static WellService getWellService() {
        return wellService;
    }

    WellDao wellDao = WellDaoImpl.getWellDao();

    public Well save(Well well) {
        return wellDao.save(well);
    }

    public boolean delete(Long id) {
        return wellDao.delete(id);
    }
}