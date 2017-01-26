package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("logged")
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
    public String submitLogin(@RequestParam String email, @RequestParam String password) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
        String query = "SELECT * FROM person WHERE email='" + email + "' AND password='" + password + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet.next()) {    
            return "welcome";
        } else {
            return "redirect:/login";
        }
    }
}
