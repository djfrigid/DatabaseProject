package com.sparta.model.dbaccess;

import com.sparta.model.employee.Employee;

import java.util.List;

public interface EmployeeDao {
    void dropTable();
    void createTable();
    void truncateTable();
    List<Employee> getAllEmployees();
    Employee getEmployee(int id);
    void insertEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);

}
