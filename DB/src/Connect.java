import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Connect {

    private static String host="pg";
    private static int port=9000;
    private static String tabl="studs.public";
    private static String db="studs";
    private static String url="jdbc:postgresql://"+host+":"+port+"/"+db;
    private static String user="postgres";
    private static String pass="lga852";
    static Connection connection;
    public static void connection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            Logger.login(Level.INFO,"Подключение к БД прошло успешно");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
