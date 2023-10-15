package repository;

import model.Location;

public interface LocationDao {

    boolean delete(Long id);

    Location save(Location location);
}