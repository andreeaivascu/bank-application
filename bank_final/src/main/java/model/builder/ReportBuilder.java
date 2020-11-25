package model.builder;

import model.Account;
import model.Report;

public class ReportBuilder {

    private Report report;

    public ReportBuilder()
    {
        report=new Report();
    }

    public ReportBuilder setId(Long id)
    {
        report.setId(id);
        return this;
    }
    public ReportBuilder setOperation(String operation)
    {
        report.setOperation(operation);
        return this;
    }

    public ReportBuilder setIdEmployee(Long idEmployee)
    {
         report.setIdEmployee(idEmployee);
        return this;
    }
    public Report build()
    {
        return report;
    }
}
