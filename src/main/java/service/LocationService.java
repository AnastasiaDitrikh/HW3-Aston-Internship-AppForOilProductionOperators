package service;

import model.Location;
import repository.LocationDao;
import repository.impl.LocationDaoImpl;

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
}