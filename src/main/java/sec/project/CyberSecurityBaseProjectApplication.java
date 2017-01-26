package sec.project;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {
    
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
        
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");

        try {
            RunScript.execute(connection, new FileReader("sql/database_schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database_import.sql"));
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM person;");

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
        }
    }
}
