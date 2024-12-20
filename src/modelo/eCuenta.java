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
public class eCuenta extends cConex {

    private int cueNro, lmCodigo, serCodigo, cuedetCantidad;
    private double cuedetPrecio;
    private String cueFecha;
    private boolean cueAnulado;
    private eMedidor medidor;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eCuenta() {
        this.ps = null;
        this.con = cone();
        this.rs = null;
    }

    public int getCueNro() {
        return cueNro;
    }

    public void setCueNro(int cueNro) {
        this.cueNro = cueNro;
    }

    public int getLmCodigo() {
        return lmCodigo;
    }

    public void setLmCodigo(int lmCodigo) {
        this.lmCodigo = lmCodigo;
    }

    public int getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(int serCodigo) {
        this.serCodigo = serCodigo;
    }

    public int getCuedetCantidad() {
        return cuedetCantidad;
    }

    public void setCuedetCantidad(int cuedetCantidad) {
        this.cuedetCantidad = cuedetCantidad;
    }

    public double getCuedetPrecio() {
        return cuedetPrecio;
    }

    public void setCuedetPrecio(double cuedetPrecio) {
        this.cuedetPrecio = cuedetPrecio;
    }

    public String getCueFecha() {
        return cueFecha;
    }

    public void setCueFecha(String cueFecha) {
        this.cueFecha = cueFecha;
    }

    public eMedidor getMedidor() {
        return medidor;
    }

    public void setMedidor(eMedidor medidor) {
        this.medidor = medidor;
    }

    public boolean isCueAnulado() {
        return cueAnulado;
    }

    public void setCueAnulado(boolean cueAnulado) {
        this.cueAnulado = cueAnulado;
    }

    public int nuevoNro() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(cueNro),0) + 1 FROM cuenta");
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
            ps = con.prepareCall("call pCuenta(?)");
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
            colum.getColumn(4).setMinWidth(0);
            colum.getColumn(4).setMaxWidth(0);
            colum.getColumn(5).setMinWidth(0);
            colum.getColumn(5).setMaxWidth(0);
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
            ps = con.prepareCall("SELECT serdescripcion, cuedetcantidad, FORMAT(cuedetprecio, 2, 'de_DE') precio, "
                    + " FORMAT(cuedetcantidad * cuedetprecio, 2, 'de_DE') subtotal, d.sercodigo FROM cuentadet d "
                    + " INNER JOIN servicio s ON d.sercodigo = s.sercodigo WHERE d.cuenro = ?");
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

    public String insertarCuenta() {
        this.con = cone();
        String mensaje = null;
        try {
            ps = con.prepareStatement("INSERT INTO cuenta VALUES (?,?,?,0)");
            ps.setInt(1, cueNro);
            ps.setString(2, cueFecha);
            ps.setInt(3, lmCodigo);
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

    public String anularCuenta() {
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE cuenta SET cueanulado = 1 WHERE cueNro = ?");
            ps.setInt(1, cueNro);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public String insertarCuentaDet() {
        String mensaje = null;

        try {
            ps = con.prepareStatement("INSERT INTO cuentadet VALUES (?,?,?,?)");
            ps.setInt(1, cueNro);
            ps.setInt(2, serCodigo);
            ps.setInt(3, cuedetCantidad);
            ps.setDouble(4, cuedetPrecio);
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
