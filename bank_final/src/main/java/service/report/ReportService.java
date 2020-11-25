package service.report;

import model.Report;
import repository.EntityNotFoundException;
import repository.report.ReportRepository;

import java.util.List;

public interface ReportService {

    List<Report> findAll(Long idEmployee, Long nrOperations);

    boolean save(Long idEmployee, String nameOfOperation);

    Report identify(Long id) throws EntityNotFoundException;

}
