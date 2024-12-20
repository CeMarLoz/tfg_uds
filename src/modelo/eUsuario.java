package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CesarMatias
 */
public class eUsuario extends cConex {

    private int usucodigo, rolcodigo, aux;
    private String nombre, apellido, alias, clave;
    ArrayList<eUsuario> lUsu = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    /**
     * @autor CesarMatias
     */
    public eUsuario() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getUsucodigo() {
        return usucodigo;
    }

    public void setUsucodigo(int usucodigo) {
        this.usucodigo = usucodigo;
    }

    public int getRolcodigo() {
        return rolcodigo;
    }

    public void setRolcodigo(int rolcodigo) {
        this.rolcodigo = rolcodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(usucodigo),0) + 1 FROM usuario");
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                return id;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

        return id;
    }

    public ArrayList<eUsuario> recuperaDatos() {
        this.con = cone();
        lUsu.clear();
        try {
            ps = con.prepareStatement("SELECT usucodigo, nombre, apellido, alias, clave, rolcodigo FROM usuario WHERE usucodigo = ?");
            ps.setInt(1, usucodigo);

            rs = ps.executeQuery();
            while (rs.next()) {
                eUsuario usuario = new eUsuario();
                usuario.setUsucodigo(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setAlias(rs.getString(4));
                usuario.setClave(rs.getString(5));
                lUsu.add(usuario);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
        return lUsu;
    }

    public String insertarUsuario() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO usuario VALUES (?,?,?,?,?,?)");
            ps.setInt(1, usucodigo);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, alias);
            ps.setString(5, clave);
            ps.setInt(6, 1);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            mensaje = "No se pudo guardar!";

            if (ex.getErrorCode() == 1062) {
                mensaje = "No se pudo guardar. Registro Duplicado!";
            }
            return mensaje;
        }
    }

    public String actualizarUsuario() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE usuario SET usucodigo = ?, nombre = ?, apellido = ?, alias = ?, clave = ?, rolcodigo = ? WHERE usucodigo = ?");
            ps.setInt(1, usucodigo);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, alias);
            ps.setString(5, clave);
            ps.setInt(6, 1);
            ps.setInt(7, aux);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            mensaje = "No se pudo actualizar!";

            if (ex.getErrorCode() == 1062) {
                mensaje = "No se pudo actualizar. Registro Duplicado!";
            }
            return mensaje;
        }
    }

    public String eliminarUsuario() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM usuario WHERE usucodigo = ?");
            ps.setInt(1, usucodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
