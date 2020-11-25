package repository.report;

import model.Account;
import model.Client;
import model.Report;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ReportBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryMySQL implements ReportRepository{

    private final Connection connection;

    public ReportRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Report> findAll(Long idEmployee, Long nrOperations) {
        List<Report> reports = new ArrayList<>();

        try{  PreparedStatement statement=connection
                .prepareStatement( "Select * from report_employee WHERE id_employee="+idEmployee+" and id<="+nrOperations);
            ResultSet rs=statement.executeQuery();
            while (rs.next()) {
                reports.add(getReportFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return reports;
    }

    @Override
    public boolean save(Long idEmployee, String nameOfOperation) {

            try {
                PreparedStatement insertStatement = connection
                        .prepareStatement("INSERT INTO report_employee values (null, ?, ?)");
                insertStatement.setLong(1, idEmployee);
                insertStatement.setString(2, nameOfOperation);
                insertStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    @Override
    public Report identify(Long id) throws EntityNotFoundException {


        try{  PreparedStatement statement=connection
                .prepareStatement( "Select * from report_employee where id_employee=" + id);
            ResultSet rs=statement.executeQuery();
            if (rs.next()) {
                return getReportFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Report.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Report.class.getSimpleName());

        }
    }

    private Report getReportFromResultSet(ResultSet rs) throws SQLException {
        return new ReportBuilder()
                .setId(rs.getLong("id"))
                .setIdEmployee(rs.getLong("id_employee"))
                .setOperation(rs.getString("operation"))
                .build();
    }

}
