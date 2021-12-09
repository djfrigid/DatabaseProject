package com.sparta.util;

import com.sparta.dbaccess.EmployeeDao;
import com.sparta.dbaccess.EmployeeDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    public static final Logger LOGGER = LogManager.getLogger("Test name");
    public static final EmployeeDao EMPLOYEE_DEO = new EmployeeDaoImpl();
}
