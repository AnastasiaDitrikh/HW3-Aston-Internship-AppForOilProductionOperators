package repository;

import model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {

    boolean delete(Long id);

    Location save(Location location);

    void update(Location location);

    Optional<Location> getById(Long id);

    List<Location> findAll();
}