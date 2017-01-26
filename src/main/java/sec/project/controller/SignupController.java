package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin() {
        return "login";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String submitLogin(Model model, @RequestParam String email, @RequestParam String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
        String query = "SELECT email FROM person WHERE email='" + email + "' AND password='" + password + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        model.addAttribute("email", email);
        if (resultSet.next()) {
            return "welcome";
        } else {
            return "login";
        }
    }}
