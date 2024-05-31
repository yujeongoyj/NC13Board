package connector;

import com.mysql.cj.xdevapi.AddResult;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionMaker implements ConnectionMaker {
    private final String URL = "jdbc:mysql://localhost:3306/board";
    private final String USERNAME = "root";
    private final String PASSWORD = "ss4161813!";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @Override
    public Connection makeConnection() {
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            return connection;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}












