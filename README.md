# CyberSecurity_Project
The task was to create a web application that has at least five different flaws from the OWASP top ten list.  Written using Spring framework.

This is a very simple application with a login page and a page to post discussions. Start the app and go to localhost:8080. You will be on the login page. 

## First vulnerability: SQL-injection.
You can use for example Firefox extension SQL Inject Me to find out if the app is vulnerable to SQL injection and you will find several vulnerabilities. Write some random stuff to test if login works and you will be always redirected to login page. Now write both in the email and password fields 1' OR '1'='1 and click submit. You will be redirected to welcome page. 

Fix: the SQL request should be prametrized.

In SignupController modify the method submitLogin() as follows:

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String submitLogin(@RequestParam String email, @RequestParam String password) throws SQLException {
        String e = email;
        String p = password; 
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./database", "user", "");
        String query = "SELECT * FROM person WHERE email= ? AND password= ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString( 1, e);
        pstmt.setString(2, p);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return "welcome";
        } else {
            return "login";
        }
    }

Now the previous SQL-injection won’t work anymore. 

## Second vulnerability: Cross site scripting.
Click on the link in the welcome page. Try adding the following in the discussion page:
Be careful with <script>alert(''Injected!'');</script> injections!
Go to login in page and login again with the SQL injection. Go to the discussion page to test the injection. 

Fix: go to forum.html file and in the line:
<li th:each="item: ${list}" th:utext="${item}">Text here</li>
change th:utext to th:text
Try going again to the page to add the javascript and the injection will not work. 
  
## Third vulnerability: Broken authentication and session management / security misconfiguration
You can not get to the welcome page without login in but you can get to the discussion page /login/forum. That is not suppost to happen. 


Fix: in CyberSecurityBaseProjectApplication.java add ”public static boolean login;” and ”login = false;”

public class CyberSecurityBaseProjectApplication {

    public static boolean login;
…
….
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            login = false;
        }
    }
}

In SignupController.java in submitLogin method add ”login = true;”:

…
        if (resultSet.next()) {    
            login = true;
            return "welcome";
        } else {
…

In DiscussionPageController.java in getDiscussion method add the followinf if loop:

    @RequestMapping(value = "/login/forum", method = RequestMethod.GET)
    public String getDiscussions(Model model, @RequestParam(required = false) String content) {
        if (!login) {
            return "redirect:/login";
        }
...

Now in order to access the discussion page you will have to login first. 

## Fourth vulnerability: Insecure Direct Object References
In the discussion page open Firefox developer tools. With the style inspector open the <div> and you will se an hidden form. Delete the hidden=”hidden” in the style inspector and press enter. A click button will appear, which will give you access to a page called database

Fix: the database page should be protected with login and use of strong password. We could use a login form with thymeleaf and a controller requiring POST.

FORM
<form hidden="hidden" th:action="@{/database}" method="POST">
<p><label for="user">User</label>User<input type="text" name="user"/></p>
<p><label for="password">Password</label>Password<input type="password" name="password"/></p>
<p><input type="submit" value="Click!"/></p>
</form>

CONTROLLER
@RequestMapping(value = "/database", method = RequestMethod.POST)
    public String DatabaseLogin(@RequestParam String user, @RequestParam String password) {
        if (user.equals("admin") && password.equals("aF_4%6lka?a*")) {
            return "database";
        } else {
            return "/forum";
        }
    }

## Fifth vulnerability: Insecure Cryptographic Storage
From the database page click get users and you will see email and password in  plane text of the registered users. 

Fix: instead of adding users and password with a SQL script containing the password in plain text, we could use a java class which encrypts the password before passing it to the SQL script. We could use the same class to decrypt the password when we retrive it from the database to check it against user input. 

package sec.project.domain;

import org.apache.tomcat.util.codec.binary.Base64;

public class MyCrypto {


    public static String encrypt(String text) {

        return new String(Base64.encodeBase64(text.getBytes()));

    }

    public static String decrypt(String text) {

        return new String(Base64.decodeBase64(text.getBytes()));

    }

}
