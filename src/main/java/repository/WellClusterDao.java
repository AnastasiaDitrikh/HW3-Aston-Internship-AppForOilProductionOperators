package repository;

import model.WellCluster;

public interface WellClusterDao {

    boolean delete(Long id);

    WellCluster save(WellCluster wellCluster);
}