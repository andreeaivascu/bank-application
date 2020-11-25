package repository.report;

import model.Report;
import model.User;
import repository.EntityNotFoundException;

import java.util.List;

public interface ReportRepository {


    List<Report> findAll(Long idEmployee, Long nrOperations);

    boolean save(Long id, String nameOfOperation);

    Report identify(Long id) throws EntityNotFoundException;
}
