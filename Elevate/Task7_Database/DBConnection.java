
    import java.sql.*;

public class DBConnection {
    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root";  // your username
    static final String PASS = "password";  // your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

