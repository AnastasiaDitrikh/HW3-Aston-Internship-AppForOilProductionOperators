package service;

import model.Operator;
import repository.OperatorDao;
import repository.impl.OperatorDaoImpl;

import java.util.List;
import java.util.Optional;

public class OperatorService {
    private static final OperatorService operatorService = new OperatorService();

    private OperatorService() {

    }

    public static OperatorService getOperatorService() {
        return operatorService;
    }

    OperatorDao operatorDao = OperatorDaoImpl.getOperatorDao();

    public Operator save(Operator operator) {
        return operatorDao.save(operator);
    }

    public Optional<Operator> getById(Long id) {
        return operatorDao.getById(id);
    }

    public void update(Operator operator) {
        operatorDao.update(operator);
    }

    public boolean delete(Long id) {
        return operatorDao.delete(id);
    }

    public List<Operator> findAll() {
        return operatorDao.findAll();
    }
}