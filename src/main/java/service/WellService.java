package service;

import model.Well;
import repository.WellDao;
import repository.impl.WellDaoImpl;

import java.util.List;
import java.util.Optional;

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

    public Optional<Well> getById(Long id) {
        return wellDao.getById(id);
    }

    public void update(Well well) {
        wellDao.update(well);
    }

    public List<Well> findAll() {
        return wellDao.findAll();
    }
}