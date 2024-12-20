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
public class eItemCategoria extends cConex {

    private int icCodigo, impCodigo, aux;
    private String icDescripcion;
    ArrayList<eItemCategoria> lItemCategoria = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eItemCategoria() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public eItemCategoria(int icCodigo, String icDescripcion) {
        this.icCodigo = icCodigo;
        this.icDescripcion = icDescripcion;
    }

    public int getIcCodigo() {
        return icCodigo;
    }

    public void setIcCodigo(int icCodigo) {
        this.icCodigo = icCodigo;
    }

    public int getImpCodigo() {
        return impCodigo;
    }

    public void setImpCodigo(int impCodigo) {
        this.impCodigo = impCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getIcDescripcion() {
        return icDescripcion;
    }

    public void setIcDescripcion(String icDescripcion) {
        this.icDescripcion = icDescripcion;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(iccodigo),0) + 1 FROM itemcategoria");
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

    public int recuperaImpuestoValor() {
        this.con = cone();
        int impuesto = 0;
        try {
            ps = con.prepareStatement("SELECT impvalor FROM itemcategoria c "
                    + " INNER JOIN impuesto i ON c.impcodigo = i.impcodigo WHERE c.iccodigo = ?");
            ps.setInt(1, icCodigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                impuesto = rs.getInt(1);
                return impuesto;
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

        return impuesto;
    }

    public ArrayList<eItemCategoria> recuperaDatos() {
        this.con = cone();
        lItemCategoria.clear();
        try {

            ps = con.prepareStatement("SELECT iccodigo, itemcategoriadescripcion FROM itemcategoria WHERE iccodigo = ?");
            ps.setInt(1, icCodigo);
            rs = ps.executeQuery();
            while (rs.next()) {
                eItemCategoria itemcategoria = new eItemCategoria();
                itemcategoria.setIcCodigo(rs.getInt(1));
                itemcategoria.setIcDescripcion(rs.getString(2));
                lItemCategoria.add(itemcategoria);
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
        return lItemCategoria;
    }

    public String insertarItemCategoria() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO itemcategoria VALUES (?,?,?)");
            ps.setInt(1, icCodigo);
            ps.setString(2, icDescripcion);
            ps.setInt(3, impCodigo);
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

    public String actualizarItemCategoria() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE itemcategoria SET iccodigo = ?, itemcategoriadescripcion = ? WHERE iccodigo = ?");
            ps.setInt(1, icCodigo);
            ps.setString(2, icDescripcion);
            ps.setInt(3, impCodigo);
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

    public String eliminarItemCategoria() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM itemcategoria WHERE iccodigo = ?");
            ps.setInt(1, icCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public void carCombo(JComboBox<eItemCategoria> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT iccodigo, icdescripcion FROM itemcategoria ORDER BY iccodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eItemCategoria(rs.getInt(1), rs.getString(2)));
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
        return this.icDescripcion;
    }

    public boolean equals(Object obj) {
        return this.icCodigo == ((eItemCategoria) obj).icCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.icCodigo;
        return hash;
    }
}
