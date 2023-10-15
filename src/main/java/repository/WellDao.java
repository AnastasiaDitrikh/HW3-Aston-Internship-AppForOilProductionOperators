package repository;

import model.Well;

public interface WellDao {

    boolean delete(Long id);
    Well save(Well well);
}