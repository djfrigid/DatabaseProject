package com.sparta.employee;

import com.sparta.jdbc.StatementFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static com.sparta.util.Constants.LOGGER;

public class EmployeeDaoImpl implements EmployeeDao {

    public EmployeeDaoImpl(){

    }


    @Override
    public List<Employee> getAllEmployees() {
        try(PreparedStatement stmt = StatementFactory.getAllEmployees()){
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                System.out.println("Employee{" +
                        "id=" + result.getInt(id) +
                        ", namePrefix='" + result.getString(namePrefix) + '\'' +
                        ", firstName='" + result.getString(firstName) + '\'' +
                        ", initial=" + result.getString(initial) +
                        ", lastName='" + result.getString(lastName) + '\'' +
                        ", gender=" + result.getString(gender) +
                        ", email='" + result.getString(email) + '\'' +
                        ", dateOfBirth=" + result.getDate(dateOfBirth) +
                        ", dateOfJoining=" + result.getString(dateOfJoining) +
                        ", salary=" + result.getString(salary) +
                        '}');
            }
            LOGGER.info("All employees displayed!");
        } catch (SQLException s) {
            s.printStackTrace();
            LOGGER.error("Error displaying all employees!");
        }
        return null;
    }

    @Override
    public Employee getEmployee(int id) {
        try(PreparedStatement stmt = StatementFactory.getEmployee()){
            stmt.setInt(1,id);
            ResultSet result = stmt.executeQuery();
            System.out.println("Employee{" +
                    "id=" + result.getInt(id) +
                    ", namePrefix='" + result.getString(namePrefix) + '\'' +
                    ", firstName='" + result.getString(firstName) + '\'' +
                    ", initial=" + result.getString(initial) +
                    ", lastName='" + result.getString(lastName) + '\'' +
                    ", gender=" + result.getString(gender) +
                    ", email='" + result.getString(email) + '\'' +
                    ", dateOfBirth=" + result.getDate(dateOfBirth) +
                    ", dateOfJoining=" + result.getString(dateOfJoining) +
                    ", salary=" + result.getString(salary) +
                    '}');
            LOGGER.info("Employee found!");
        } catch (SQLException s) {
            s.printStackTrace();
            LOGGER.error("Employee not found!");
        }
        return null;
    }

    @Override
    public void insertEmployee(Employee employee) {
        try(PreparedStatement stmt = StatementFactory.getInsertStatement()) {
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
            int rowAffected = stmt.executeUpdate();
            LOGGER.info("Records inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warn("Insert unsuccessful!");
        }

    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int id) {
        try (PreparedStatement stmt = StatementFactory.getDeleteStatement()){
            stmt.setInt(1, id);
            int rowAffected = stmt.executeUpdate();
            LOGGER.info("Records deleted: " + rowAffected);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("Error deleting record!");
        }

    }
}
