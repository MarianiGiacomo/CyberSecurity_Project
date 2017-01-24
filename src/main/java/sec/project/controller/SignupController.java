package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Person;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private HttpSession session;

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
        String query = "SELECT email FROM person WHERE email='" + email + "' AND password='" + password + "';";
        ResultSet resultSet = connection.createStatement().executeQuery(query);

        if (resultSet.next()) {
            new Person(email, password);
            return "welcome";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/login/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/login/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
}
