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
public class eTipoDocumento extends cConex {

    private int codigo, aux;
    private String descripcion;
    ArrayList<eTipoDocumento> lDep = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eTipoDocumento(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public eTipoDocumento() {
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
            ps = con.prepareStatement("SELECT ifnull(max(tdoccodigo),0) + 1 FROM tipodocumento");
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

    public ArrayList<eTipoDocumento> recuperaDatos() {
        this.con = cone();
        lDep.clear();
        try {
            ps = con.prepareStatement("SELECT tdoccodigo, tdocdescripcion FROM tipodocumento WHERE tdoccodigo = ?");
            ps.setInt(1, codigo);

            rs = ps.executeQuery();
            while (rs.next()) {
                eTipoDocumento tipodocumento = new eTipoDocumento();
                tipodocumento.setCodigo(rs.getInt(1));
                tipodocumento.setDescripcion(rs.getString(2));
                lDep.add(tipodocumento);
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
        return lDep;
    }

    public String insertarTipoDocumento() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO tipodocumento VALUES (?,?)");
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

    public String actualizarTipoDocumento() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE tipodocumento SET tdoccodigo = ?, tdocdescripcion = ? WHERE tdoccodigo = ?");
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

    public String eliminarTipoDocumento() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM tipodocumento WHERE tdoccodigo = ?");
            ps.setInt(1, codigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public void carCombo(JComboBox<eTipoDocumento> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT tdoccodigo, tdocdescripcion FROM tipodocumento ORDER BY tdoccodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eTipoDocumento(rs.getInt(1), rs.getString(2)));
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

    @Override
    public String toString() {
        return this.descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        return this.codigo == ((eTipoDocumento) obj).codigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.codigo;
        return hash;
    }
}
