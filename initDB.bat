@echo off
mysql -u %DBUSER% -p%DBPASS% -e "CREATE DATABASE IF NOT EXISTS databaseprojectfromscript;";
mysql -u %DBUSER% -p%DBPASS%  --database databaseprojectfromscript -e "CREATE TABLE IF NOT EXISTS employees (id INT(6) PRIMARY KEY , namePrefix VARCHAR(5), firstName VARCHAR(50),initial char, lastName VARCHAR(50), gender char , email VARCHAR(50), dateOfBirth DATE, dateOfJoining DATE,salary INT);"
exit