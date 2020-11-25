package service.report;

import model.Report;
import repository.EntityNotFoundException;
import repository.report.ReportRepository;

import java.util.List;

public class ReportServiceImpl implements ReportService{


    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Report> findAll(Long idEmployee, Long nrOperations) {
        return repository.findAll(idEmployee, nrOperations);
    }

    @Override
    public boolean save(Long idEmployee, String nameOfOperation) {
        return repository.save(idEmployee,nameOfOperation);
    }

    @Override
    public Report identify(Long id) throws EntityNotFoundException {
        return  repository.identify(id);
    }
}
