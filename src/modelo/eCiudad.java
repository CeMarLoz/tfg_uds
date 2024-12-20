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
public class eCiudad extends cConex {

    private int ciuCodigo, aux;
    private String ciuDescripcion;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    /**
     *
     * @param ciuCodigo
     * @param ciuDescripcion
     */
    public eCiudad(int ciuCodigo, String ciuDescripcion) {
        this.ciuCodigo = ciuCodigo;
        this.ciuDescripcion = ciuDescripcion;
    }

    /**
     * @autor CesarMatias
     */
    public eCiudad() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(int ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getCiuDescripcion() {
        return ciuDescripcion;
    }

    public void setCiuDescripcion(String ciuDescripcion) {
        this.ciuDescripcion = ciuDescripcion;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(ciucodigo),0) + 1 FROM ciudad");
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

    public void recuperaCiudad(JTable tabla, String criterio) {
        this.con = cone();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        try {
            ps = con.prepareCall("call pCiudad(?)");
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

    public String insertarCiudad() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO ciudad VALUES (?,?)");
            ps.setInt(1, ciuCodigo);
            ps.setString(2, ciuDescripcion);
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

    public String actualizarCiudad() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE ciudad SET ciucodigo = ?, ciudescripcion = ? WHERE ciucodigo = ?");
            ps.setInt(1, ciuCodigo);
            ps.setString(2, ciuDescripcion);
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

    public String eliminarCiudad() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM ciudad WHERE ciucodigo = ?");
            ps.setInt(1, ciuCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
