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
public class eRol extends cConex {

    private int codigo, aux;
    private String descripcion;
    ArrayList<eRol> lRol = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eRol() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(rolcodigo),0) + 1 FROM rol");
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

    public ArrayList<eRol> recuperaDatos(boolean rep) {
        this.con = cone();
        lRol.clear();
        try {
            if (rep) {
                ps = con.prepareStatement("SELECT rolcodigo, roldescripcion FROM rol");
            } else {
                ps = con.prepareStatement("SELECT rolcodigo, roldescripcion FROM rol WHERE rolcodigo = ?");
                ps.setInt(1, codigo);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                eRol rol = new eRol();
                rol.setCodigo(rs.getInt(1));
                rol.setDescripcion(rs.getString(2));
                lRol.add(rol);
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
        return lRol;
    }

    public String insertarRol() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO rol VALUES (?,?)");
            ps.setInt(1, codigo);
            ps.setString(2, descripcion);
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

    public String actualizarRol() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE rol SET rolcodigo = ?, roldescripcion = ? WHERE rolcodigo = ?");
            ps.setInt(1, codigo);
            ps.setString(2, descripcion);
            ps.setInt(3, aux);
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

    public String eliminarRol() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM rol WHERE rolcodigo = ?");
            ps.setInt(1, codigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
