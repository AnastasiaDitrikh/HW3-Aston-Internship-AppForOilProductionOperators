package repository;

import model.WellCluster;

import java.util.List;
import java.util.Optional;

public interface WellClusterDao {

    boolean delete(Long id);

    WellCluster save(WellCluster wellCluster);

    void update(WellCluster wellCluster);

    Optional<WellCluster> getById(Long id);

    List<WellCluster> findAll();
}