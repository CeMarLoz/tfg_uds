package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author CesarMatias
 */
public class eEstadoSolicitud extends cConex {

    private int codigo, aux;
    private String descripcion;
    ArrayList<eEstadoSolicitud> lEstSol = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eEstadoSolicitud(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public eEstadoSolicitud() {
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
            ps = con.prepareStatement("SELECT ifnull(max(escodigo),0) + 1 FROM estadosolicitud");
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

    public ArrayList<eEstadoSolicitud> recuperaDatos() {
        this.con = cone();
        lEstSol.clear();
        try {
            ps = con.prepareStatement("SELECT escodigo, esdescripcion FROM estadosolicitud WHERE escodigo = ?");
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                eEstadoSolicitud esta = new eEstadoSolicitud();
                esta.setCodigo(rs.getInt(1));
                esta.setDescripcion(rs.getString(2));
                lEstSol.add(esta);
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
        return lEstSol;
    }

    public String insertarEstadoSolicitud() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO estadosolicitud VALUES (?,?)");
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

    public String actualizarEstadoSolicitud() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE estadosolicitud SET escodigo = ?, esdescripcion = ? WHERE escodigo = ?");
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

    public String eliminarEstadoSolicitud() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM estadosolicitud WHERE escodigo = ?");
            ps.setInt(1, codigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    @Override
    public String toString() {
        return this.descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        return this.codigo == ((eEstadoSolicitud) obj).codigo;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.codigo;
        return hash;
    }

    public void carCombo(JComboBox<eEstadoSolicitud> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT escodigo, esdescripcion FROM estadosolicitud ORDER BY escodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eEstadoSolicitud(rs.getInt(1), rs.getString(2)));
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
    }
}
