package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CesarMatias
 */
public class cConex {

    private final String base = "tfg";
    private final String usuario = "root";
    private final String clave = "12345";
    private final String host = "jdbc:mysql://localhost:3306/" + base + "?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC";
    private Connection cone = null;

    public Connection cone() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cone = DriverManager.getConnection(this.host, this.usuario, this.clave);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error cargando el Driver MySQL JDBC ... FAIL" + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Imposible realizar conexion con la base de datos ... FAIL" + ex.getMessage());
        }
        return cone;
    }
}
