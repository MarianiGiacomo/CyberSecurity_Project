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

    @RequestMapping(value = "/database", method = RequestMethod.POST)
    public String DatabaseLogin(@RequestParam String user, @RequestParam String password) {
        if (user.equals("admin") && password.equals("abc123")) {
            return "database";
        } else {
            return "/forum";
        }
    }

    @RequestMapping(value = "/database/query", method = RequestMethod.POST)
    public String DatabaseLogin(Model model, @RequestParam String query) throws SQLException {
        this.datalist = new ArrayList<>();
        connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
        resultSet = connection.createStatement().executeQuery(query);
        int column = 1;
        while (resultSet.next()) {
            String text = resultSet.getString(column);
            datalist.add(text);
            column ++;
        }
        model.addAttribute("datalist", datalist);
        return "database";
    }

}
