package com.sparta.model.util;

import com.sparta.model.dbaccess.EmployeeDao;
import com.sparta.model.dbaccess.EmployeeDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    public static final Logger LOGGER = LogManager.getLogger("Data handling app");
    public static final EmployeeDao EMPLOYEE_DAO = new EmployeeDaoImpl();
}
