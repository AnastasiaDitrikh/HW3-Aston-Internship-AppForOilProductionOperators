package repository;

import model.Operator;

import java.util.List;
import java.util.Optional;

public interface OperatorDao {

    boolean delete(Long id);

    Operator save(Operator operator);

    void update(Operator operator);

    Optional<Operator> getById(Long id);

    List<Operator> findAll();
}