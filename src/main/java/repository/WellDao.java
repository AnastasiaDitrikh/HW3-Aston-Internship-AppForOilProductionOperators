package repository;

import model.Well;

import java.util.List;
import java.util.Optional;

public interface WellDao {

    boolean delete(Long id);

    Well save(Well well);


    void update(Well well);

    Optional<Well> getById(Long id);

    List<Well> findAll();
}