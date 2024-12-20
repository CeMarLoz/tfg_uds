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
public class eFacturaVenta extends cConex {

    private int venNro, timCodigo, usucodigo, cliCodigo, serCodigo, vdetCantidad, vdetImpuesto;
    private String venFecha;
    private boolean venContado;
    private double vdetPrecio;
    private int idVenta;
    private eCliente cliente;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eFacturaVenta() {
        this.ps = null;
        this.con = cone();
        this.rs = null;
    }

    public int getVenNro() {
        return venNro;
    }

    public void setVenNro(int venNro) {
        this.venNro = venNro;
    }

    public int getTimCodigo() {
        return timCodigo;
    }

    public void setTimCodigo(int timCodigo) {
        this.timCodigo = timCodigo;
    }

    public int getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(int cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public int getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(int serCodigo) {
        this.serCodigo = serCodigo;
    }

    public int getVdetCantidad() {
        return vdetCantidad;
    }

    public void setVdetCantidad(int vdetCantidad) {
        this.vdetCantidad = vdetCantidad;
    }

    public int getVdetImpuesto() {
        return vdetImpuesto;
    }

    public void setVdetImpuesto(int vdetImpuesto) {
        this.vdetImpuesto = vdetImpuesto;
    }

    public String getVenFecha() {
        return venFecha;
    }

    public void setVenFecha(String venFecha) {
        this.venFecha = venFecha;
    }

    public boolean isVenContado() {
        return venContado;
    }

    public void setVenContado(boolean venContado) {
        this.venContado = venContado;
    }

    public double getVdetPrecio() {
        return vdetPrecio;
    }

    public void setVdetPrecio(double vdetPrecio) {
        this.vdetPrecio = vdetPrecio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public eCliente getCliente() {
        return cliente;
    }

    public void setCliente(eCliente cliente) {
        this.cliente = cliente;
    }

    public int getUsucodigo() {
        return usucodigo;
    }

    public void setUsucodigo(int usucodigo) {
        this.usucodigo = usucodigo;
    }

    public int nuevoNro() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT IFNULL(MAX(vennro), 0) + 1 FROM facturaventa v");
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
            ps = con.prepareCall("call pVenta(?,?,?)");
            ps.setString(1, criterio);
            ps.setInt(2, eLogin.Establecimiento);
            ps.setInt(3, eLogin.PuntoExpedicion);
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
            colum.getColumn(0).setPreferredWidth(180);
            colum.getColumn(1).setPreferredWidth(100);
            colum.getColumn(2).setPreferredWidth(100);
            colum.getColumn(3).setPreferredWidth(200);
            colum.getColumn(4).setMinWidth(0);
            colum.getColumn(4).setMaxWidth(0);
            colum.getColumn(5).setMinWidth(0);
            colum.getColumn(5).setMaxWidth(0);
            colum.getColumn(6).setMinWidth(0);
            colum.getColumn(6).setMaxWidth(0);
            colum.getColumn(7).setMinWidth(0);
            colum.getColumn(7).setMaxWidth(0);
            colum.getColumn(8).setMinWidth(0);
            colum.getColumn(8).setMaxWidth(0);
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

    public String insertarFacturaVenta() {
        this.con = cone();
        String mensaje = null;
        try {
            ps = con.prepareStatement("INSERT INTO facturaventa (vennro, venfecha, vencontado, timcodigo, clicodigo, usucodigo) "
                    + " VALUES(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, venNro);
            ps.setString(2, venFecha);
            ps.setBoolean(3, venContado);
            ps.setInt(4, timCodigo);
            ps.setInt(5, cliCodigo);
            ps.setInt(6, usucodigo);
            int fila = ps.executeUpdate();
            if (fila == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idVenta = rs.getInt(1);
                }
            }
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

    public String insertarFacturaVentaDet() {
        String mensaje = null;

        try {
            ps = con.prepareStatement("INSERT INTO ventadet (vencodigo, sercodigo, vdetcantidad, vdetprecio, vdetimpuesto) "
                    + " VALUES (?,?,?,?,?)");
            ps.setInt(1, idVenta);
            ps.setInt(2, serCodigo);
            ps.setInt(3, vdetCantidad);
            ps.setDouble(4, vdetPrecio);
            ps.setInt(5, vdetImpuesto);
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

    public double montoFactura() {
        this.con = cone();
        double id = 0;
        try {
            ps = con.prepareStatement("SELECT SUM(v.vdetcantidad * v.vdetprecio) FROM ventadet v "
                    + " WHERE v.vencodigo = ?");
            ps.setInt(1, idVenta);
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
}
