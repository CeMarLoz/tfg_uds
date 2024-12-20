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
public class eImpuesto extends cConex {

    private int impCodigo, impValor, aux;
    private String impNombre;
    ArrayList<eImpuesto> lImp = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eImpuesto(int impCodigo, String impNombre) {
        this.impCodigo = impCodigo;
        this.impNombre = impNombre;
    }

    public eImpuesto() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getImpCodigo() {
        return impCodigo;
    }

    public void setImpCodigo(int impCodigo) {
        this.impCodigo = impCodigo;
    }

    public int getImpValor() {
        return impValor;
    }

    public void setImpValor(int impValor) {
        this.impValor = impValor;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getImpNombre() {
        return impNombre;
    }

    public void setImpNombre(String impNombre) {
        this.impNombre = impNombre;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(impcodigo),0) + 1 FROM impuesto");
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

    public ArrayList<eImpuesto> recuperaDatos() {
        this.con = cone();
        lImp.clear();
        try {
            ps = con.prepareStatement("SELECT impcodigo, impnombre, impvalor FROM impuesto WHERE impcodigo = ?");
            ps.setInt(1, impCodigo);

            rs = ps.executeQuery();
            while (rs.next()) {
                eImpuesto impuesto = new eImpuesto();
                impuesto.setImpCodigo(rs.getInt(1));
                impuesto.setImpNombre(rs.getString(2));
                impuesto.setImpValor(rs.getInt(3));
                lImp.add(impuesto);
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
        return lImp;
    }

    public String insertarImpuesto() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO impuesto VALUES (?,?,?)");
            ps.setInt(1, impCodigo);
            ps.setString(2, impNombre);
            ps.setInt(3, impValor);
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

    public String actualizarImpuesto() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE impuesto SET impcodigo = ?, impnombre = ?, impvalor = ? WHERE impcodigo = ?");
            ps.setInt(1, impCodigo);
            ps.setString(2, impNombre);
            ps.setInt(3, impValor);
            ps.setInt(4, aux);
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

    public String eliminarImpuesto() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM impuesto WHERE impcodigo = ?");
            ps.setInt(1, impCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public void carCombo(JComboBox<eImpuesto> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT impcodigo, impnombre FROM impuesto ORDER BY impcodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eImpuesto(rs.getInt(1), rs.getString(2)));
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
        return this.impNombre;
    }

    @Override
    public boolean equals(Object obj) {
        return this.impCodigo == ((eImpuesto) obj).impCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.impCodigo;
        return hash;
    }
}
