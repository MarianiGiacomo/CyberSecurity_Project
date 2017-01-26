/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Giacomo Mariani
 */
@Controller
public class DatabaseController {

    private List<String> datalist;
    private Connection connection;
    private ResultSet resultSet;

    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public String DatabaseLogin() {
        return "database";
    }

    @RequestMapping(value = "/database/person", method = RequestMethod.POST)
    public String DatabaseLogin(Model model) throws SQLException {
        this.datalist = new ArrayList<>();
        connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
        resultSet = connection.createStatement().executeQuery("SELECT * FROM person;");
        while (resultSet.next()) {
            String email = " Email: " + resultSet.getString(1) + " / ";
            datalist.add(email);
            String password = " Password: " + resultSet.getString(2);
            datalist.add(password);
            datalist.add("</br>");
        }
        System.out.println(datalist.get(0) + " " + datalist.get(1));
        model.addAttribute("datalist", datalist);
        return "database";
    }

}
