package com.sparta.dbaccess;

import com.sparta.employee.Employee;

import java.util.List;

public interface EmployeeDao {
    void dropTable();
    void createTable();
    List<Employee> getAllEmployees();
    Employee getEmployee(int id);
    void insertEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);

}
