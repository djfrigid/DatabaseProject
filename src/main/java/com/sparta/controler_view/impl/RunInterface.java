package com.sparta.controler_view.impl;


import com.sparta.controler_view.io.bretty.console.view.ActionView;
import com.sparta.controler_view.io.bretty.console.view.MenuView;

public class RunInterface {

    public static void runUserInterfaceCRUD(){
        ActionView selectAll = new SelectAll();
        ActionView selectOne = new SelectOne();
        ActionView insertEmployee = new InsertEmployee();
        ActionView updateEmployee = new UpdateEmployee();
        ActionView deleteEmployee = new DeleteEmployee();

        MenuView mainMenu = new MenuView("Database Operations:", "");

        mainMenu.addMenuItem(selectAll);
        mainMenu.addMenuItem(selectOne);
        mainMenu.addMenuItem(insertEmployee);
        mainMenu.addMenuItem(updateEmployee);
        mainMenu.addMenuItem(deleteEmployee);

        mainMenu.display();
    }
}
