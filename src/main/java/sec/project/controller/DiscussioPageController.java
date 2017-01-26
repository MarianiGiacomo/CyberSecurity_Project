/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
public class DiscussioPageController {

    private List<String> list;
    private Connection connection;
    private ResultSet resultSet;

    @RequestMapping(value = "/login/forum", method = RequestMethod.GET)
    public String getDiscussions(Model model, @RequestParam(required = false) String content) {
        this.list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
            String query = "SELECT text FROM forum;";
            resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String text = resultSet.getString("text");
                list.add(text);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (content != null && !content.trim().isEmpty()) {
            this.list.add(content.trim());
            String add = "INSERT INTO forum (text) VALUES ('" + content + "');";
            try {
                connection.createStatement().execute(add);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        model.addAttribute("list", list);
        return "forum";
    }
}
