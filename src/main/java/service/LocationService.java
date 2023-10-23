package service;

import model.Location;
import repository.LocationDao;
import repository.impl.LocationDaoImpl;

import java.util.List;
import java.util.Optional;

public class LocationService {
    private static final LocationService locationService = new LocationService();

    private LocationService() {

    }

    public static LocationService getLocationService() {
        return locationService;
    }

    LocationDao locationDao = LocationDaoImpl.getLocationDao();

    public Location save(Location location) {
        return locationDao.save(location);
    }

    public boolean delete(Long id) {
        return locationDao.delete(id);
    }

    public Optional<Location> getById(Long id) {
        return locationDao.getById(id);
    }

    public void update(Location location) {
        locationDao.update(location);
    }

    public List<Location> findAll() {
        return locationDao.findAll();
    }
}