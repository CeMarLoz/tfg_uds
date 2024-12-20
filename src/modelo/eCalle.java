package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author CesarMatias
 */
public class eCalle extends cConex {

    private int calCodigo, aux;
    private String calNombre;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    /**
     * @autor CesarMatias
     */
    public eCalle() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getCalCodigo() {
        return calCodigo;
    }

    public void setCalCodigo(int calCodigo) {
        this.calCodigo = calCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getCalNombre() {
        return calNombre;
    }

    public void setCalNombre(String calNombre) {
        this.calNombre = calNombre;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(calcodigo),0) + 1 FROM calle");
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

    public void recuperaCalle(JTable tabla, String criterio) {
        this.con = cone();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        try {
            ps = con.prepareCall("call pCalle(?)");
            ps.setString(1, criterio);
            rs = ps.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            for (int j = 1; j <= col; j++) {
                modelo.addColumn(rs.getMetaData().getColumnLabel(j));//agregar los datos en la tabla 
            }
            while (rs.next()) { //mientras haya siguiete va a hacer
                Object[] dato = new Object[col];
                for (int i = 1; i <= col; i++) {
                    dato[i - 1] = rs.getObject(i);
                }
                modelo.addRow(dato);
            }
            tabla.setModel(modelo);
            TableColumnModel colum = tabla.getColumnModel();
            colum.getColumn(0).setPreferredWidth(100);
            colum.getColumn(1).setPreferredWidth(500);
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

    public String insertarCalle() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO calle VALUES (?,?)");
            ps.setInt(1, calCodigo);
            ps.setString(2, calNombre);
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

    public String actualizarCalle() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE calle SET calcodigo = ?, calnombre = ? WHERE calcodigo = ?");
            ps.setInt(1, calCodigo);
            ps.setString(2, calNombre);
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

    public String eliminarCalle() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM calle WHERE calcodigo = ?");
            ps.setInt(1, calCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
