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
public class ePresupuesto extends cConex {

    private int preNro, cueNro, serCodigo, predetCantidad;
    private double predetPrecio;
    private String preFecha;
    private eMedidor medidor;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public ePresupuesto() {
        this.ps = null;
        this.con = cone();
        this.rs = null;
    }

    public int getPreNro() {
        return preNro;
    }

    public void setPreNro(int preNro) {
        this.preNro = preNro;
    }

    public int getCueNro() {
        return cueNro;
    }

    public void setCueNro(int cueNro) {
        this.cueNro = cueNro;
    }

    public int getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(int serCodigo) {
        this.serCodigo = serCodigo;
    }

    public int getPredetCantidad() {
        return predetCantidad;
    }

    public void setPredetCantidad(int predetCantidad) {
        this.predetCantidad = predetCantidad;
    }

    public double getPredetPrecio() {
        return predetPrecio;
    }

    public void setPredetPrecio(double predetPrecio) {
        this.predetPrecio = predetPrecio;
    }

    public String getPreFecha() {
        return preFecha;
    }

    public void setPreFecha(String preFecha) {
        this.preFecha = preFecha;
    }

    public eMedidor getMedidor() {
        return medidor;
    }

    public void setMedidor(eMedidor medidor) {
        this.medidor = medidor;
    }

    public int nuevoNro() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(prenro),0) + 1 FROM presupuesto");
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

    public void recuperaDatos(JTable tabla, String criterio) {
        this.con = cone();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        try {
            ps = con.prepareCall("call pPresupuesto(?)");
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
            colum.getColumn(0).setPreferredWidth(80);
            colum.getColumn(1).setPreferredWidth(300);
            colum.getColumn(2).setPreferredWidth(100);
            colum.getColumn(3).setMinWidth(0);
            colum.getColumn(3).setMaxWidth(0);
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

    public void recuperaDatosDet(DefaultTableModel modelo, String criterio) {
        this.con = cone();
        modelo.setRowCount(0);

        try {
            ps = con.prepareCall("SELECT  serdescripcion, predetcantidad, FORMAT(predetprecio, 2, 'de_DE') precio, "
                    + " FORMAT(predetcantidad * predetprecio, 2, 'de_DE') subtotal, d.sercodigo "
                    + " FROM presupuestodet d INNER JOIN servicio s ON d.sercodigo = s.sercodigo "
                    + " WHERE d.prenro = ?");
            ps.setString(1, criterio);
            rs = ps.executeQuery();
            int col = rs.getMetaData().getColumnCount();

            while (rs.next()) { //mientras haya siguiete va a hacer
                Object[] dato = new Object[col];
                for (int i = 1; i <= col; i++) {
                    dato[i - 1] = rs.getObject(i);
                }
                modelo.addRow(dato);
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

    public void presupuestoFactura(DefaultTableModel modelo, String criterio) {
        this.con = cone();
        modelo.setRowCount(0);

        try {
            ps = con.prepareCall("SELECT d.sercodigo, serdescripcion, predetcantidad, FORMAT(predetprecio, 2, 'de_DE') precio, "
                    + " i.impvalor, FORMAT(predetcantidad * predetprecio, 2, 'de_DE') subtotal FROM presupuestodet d "
                    + " INNER JOIN servicio s ON d.sercodigo = s.sercodigo INNER JOIN itemcategoria c ON s.iccodigo = c.iccodigo "
                    + " INNER JOIN impuesto i ON c.impcodigo = i.impcodigo WHERE d.prenro = ?");
            ps.setString(1, criterio);
            rs = ps.executeQuery();
            int col = rs.getMetaData().getColumnCount();

            while (rs.next()) { //mientras haya siguiete va a hacer
                Object[] dato = new Object[col];
                for (int i = 1; i <= col; i++) {
                    dato[i - 1] = rs.getObject(i);
                }
                modelo.addRow(dato);
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

    public String insertarPresupuesto() {
        this.con = cone();
        String mensaje = null;
        try {
            ps = con.prepareStatement("INSERT INTO presupuesto VALUES (?,?,?)");
            ps.setInt(1, preNro);
            ps.setString(2, preFecha);
            ps.setInt(3, cueNro);
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

    public String eliminarPresupuesto() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM presupuesto WHERE prenro = ?");
            ps.setInt(1, preNro);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public String insertarPresupuestoDet() {
        String mensaje = null;

        try {
            ps = con.prepareStatement("INSERT INTO presupuestodet VALUES (?,?,?,?)");
            ps.setInt(1, preNro);
            ps.setInt(2, serCodigo);
            ps.setInt(3, predetCantidad);
            ps.setDouble(4, predetPrecio);
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
}
