package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author CesarMatias
 */
public class eServicio extends cConex {

    private int serCodigo, iccCodigo, aux;
    private double serPrecio;
    private String serDescripcion;

    ArrayList<eServicio> lServ = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eServicio() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(int serCodigo) {
        this.serCodigo = serCodigo;
    }

    public int getIccCodigo() {
        return iccCodigo;
    }

    public void setIccCodigo(int iccCodigo) {
        this.iccCodigo = iccCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public double getSerPrecio() {
        return serPrecio;
    }

    public void setSerPrecio(double serPrecio) {
        this.serPrecio = serPrecio;
    }

    public String getSerDescripcion() {
        return serDescripcion;
    }

    public void setSerDescripcion(String serDescripcion) {
        this.serDescripcion = serDescripcion;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(sercodigo),0) + 1 FROM servicio");
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

    public void recuperaServicio(JTable tabla, String criterio) {
        this.con = cone();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);
        try {
            ps = con.prepareCall("call pServicio(?)");
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
            colum.getColumn(2).setMinWidth(0);
            colum.getColumn(2).setMaxWidth(0);
            colum.getColumn(3).setMinWidth(0);
            colum.getColumn(3).setMaxWidth(0);
            colum.getColumn(4).setMinWidth(0);
            colum.getColumn(4).setMaxWidth(0);
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

    public ArrayList<eServicio> recuperaDatos(boolean todo) {
        this.con = cone();
        lServ.clear();
        try {
            if (todo) {
                ps = con.prepareStatement("SELECT sercodigo, serdescripcion FROM servicio");
            } else {
                ps = con.prepareStatement("SELECT sercodigo, serdescripcion FROM servicio WHERE sercodigo = ?");
                ps.setInt(1, serCodigo);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                eServicio serv = new eServicio();
                serv.setSerCodigo(rs.getInt(1));
                serv.setSerDescripcion(rs.getString(2));
                lServ.add(serv);
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
        return lServ;
    }

    public String insertarServicio() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO servicio VALUES (?,?,?,?)");
            ps.setInt(1, serCodigo);
            ps.setString(2, serDescripcion);
            ps.setDouble(3, serPrecio);
            ps.setInt(4, iccCodigo);
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

    public String actualizarServicio() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE servicio SET sercodigo = ?, serdescripcion = ? WHERE sercodigo = ?");
            ps.setInt(1, serCodigo);
            ps.setString(2, serDescripcion);
            ps.setDouble(3, serPrecio);
            ps.setInt(4, iccCodigo);
            ps.setInt(5, aux);
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

    public String eliminarServicio() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM servicio WHERE sercodigo = ?");
            ps.setInt(1, serCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
