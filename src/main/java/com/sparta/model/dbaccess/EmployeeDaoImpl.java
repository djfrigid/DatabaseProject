package com.sparta.model.dbaccess;

import com.sparta.model.employee.Employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.sparta.model.util.Constants.LOGGER;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection connection = ConnectionFactory.getConnectionInstance();

    public  EmployeeDaoImpl(){}

    @Override
    public void dropTable() {
        try(PreparedStatement stmt = StatementFactory.getDropTable()){
            stmt.executeUpdate();
            LOGGER.info("Table dropped");
        }catch(SQLException e){
            e.printStackTrace();
            LOGGER.info("The Table didn't drop");
        }

    }

    @Override
    public void createTable() {
        try(PreparedStatement stmt = StatementFactory.getCreateTable()){
            stmt.executeUpdate();
            LOGGER.info("Table created");
        }catch(SQLException e){
            e.printStackTrace();
            LOGGER.info("The Table didn't create");
        }
    }

    public void truncateTable() {
        try(PreparedStatement stmt = StatementFactory.getTruncateStatement()){
            stmt.executeUpdate();
            LOGGER.info("Table truncated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.warn("Truncating unsuccessful! ");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> listAllEmployees = new ArrayList<>();
        try {
            PreparedStatement stmt = StatementFactory.getAllEmployees();
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                listAllEmployees.add( new Employee(result.getInt("id"),
                        result.getString("namePrefix"),result.getString("firstName"),
                        result.getString("initial").charAt(0), result.getString("lastName"),
                        result.getString("gender").charAt(0), result.getString("email"),
                        result.getDate("dateOfBirth"), result.getDate("dateOfJoining"),
                        result.getInt("salary")));
            }
            LOGGER.info("All employees displayed!");
        } catch (SQLException s) {
            s.printStackTrace();
            LOGGER.error("Error displaying all employees!");
        }
        return listAllEmployees;
    }

    @Override
    public Employee getEmployee(Integer id) {
        Employee employee = new Employee();
        try{
            PreparedStatement stmt = StatementFactory.getOneEmployee();
            stmt.setInt(1,id);
            ResultSet result = stmt.executeQuery();
            if(result.next()){
                employee = new Employee(result.getInt("id"),
                        result.getString("namePrefix"),result.getString("firstName"),
                        result.getString("initial").charAt(0), result.getString("lastName"),
                        result.getString("gender").charAt(0), result.getString("email"),
                        result.getDate("dateOfBirth"), result.getDate("dateOfJoining"),
                        result.getInt("salary"));
            }
            LOGGER.info("Employee found!");
        } catch (SQLException | IOException s) {
            s.printStackTrace();
            LOGGER.error("Employee not found!");
        }
        return employee;
    }

    @Override
    public void insertEmployee(Employee employee) {
        try {
            PreparedStatement stmt = StatementFactory.getInsertEmployee(connection);
            setupEmployeeInsertStatement(employee, stmt);
            int rowAffected = stmt.executeUpdate();
            LOGGER.info("Records inserted: " + rowAffected);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            LOGGER.warn("Insert unsuccessful!");
        }
    }

    private void setupEmployeeInsertStatement(Employee employee, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, employee.getId());
        stmt.setString(2,employee.getNamePrefix());
        stmt.setString(3,employee.getFirstName());
        stmt.setString(4,String.valueOf(employee.getInitial()));
        stmt.setString(5,employee.getLastName());
        stmt.setString(6,String.valueOf(employee.getGender()));
        stmt.setString(7,employee.getEmail());
        stmt.setDate(8,employee.getDateOfBirth());
        stmt.setDate(9,employee.getDateOfJoining());
        stmt.setInt(10,employee.getSalary());
    }

    @Override
    public void insertEmployeeBatch(List<Employee> Employees){
        try{
            PreparedStatement stmt = StatementFactory.getInsertEmployee(connection);
            for (Employee employee: Employees) {
                setupEmployeeInsertStatement(employee, stmt);
                stmt.addBatch();
                stmt.clearParameters();
            }
            stmt.executeBatch();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDownConnection() {
        try {
            connection.close();
        } catch (SQLException e){
            LOGGER.warn("Connection close failed. Connection  will close on JVM termination.");
        }

    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            PreparedStatement stmt = StatementFactory.getUpdateAnEmployee();
            stmt.setString(1, employee.getNamePrefix());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, String.valueOf(employee.getInitial()));
            stmt.setString(4,employee.getLastName());
            stmt.setString(5,String.valueOf(employee.getGender()));
            stmt.setString(6,employee.getEmail());
            stmt.setDate(7,employee.getDateOfBirth());
            stmt.setDate(8,employee.getDateOfJoining());
            stmt.setInt(9,employee.getSalary());
            stmt.setInt(10, employee.getId());
            stmt.executeUpdate();
            LOGGER.info("Record Updated successful!");
        }catch (SQLException | IOException e){
            e.printStackTrace();
            LOGGER.warn("Update unsuccessful!");
        }
    }

    @Override
    public void deleteEmployee(int id) {
        try {
            PreparedStatement stmt = StatementFactory.getDeleteEmployee();
            stmt.setInt(1, id);
            int rowAffected = stmt.executeUpdate();
            LOGGER.info("Records deleted: " + rowAffected);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            LOGGER.error("Error deleting record!");
        }
    }
}
