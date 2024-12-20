package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author CesarMatias
 */
public class eSolicitud extends cConex {

    private int solNro, cliCodigo, esCodigo, aux;
    private String solDescripcion, solFecha;
    private eCliente cliente;
    private eEstadoSolicitud estado;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eSolicitud() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getSolNro() {
        return solNro;
    }

    public void setSolNro(int solNro) {
        this.solNro = solNro;
    }

    public int getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(int cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public int getEsCodigo() {
        return esCodigo;
    }

    public void setEsCodigo(int esCodigo) {
        this.esCodigo = esCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getSolDescripcion() {
        return solDescripcion;
    }

    public void setSolDescripcion(String solDescripcion) {
        this.solDescripcion = solDescripcion;
    }

    public String getSolFecha() {
        return solFecha;
    }

    public void setSolFecha(String solFecha) {
        this.solFecha = solFecha;
    }

    public eCliente getCliente() {
        return cliente;
    }

    public void setCliente(eCliente cliente) {
        this.cliente = cliente;
    }

    public eEstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(eEstadoSolicitud estado) {
        this.estado = estado;
    }

    public int nuevoNro() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(solnro),0) + 1 FROM solicitud");
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
            ps = con.prepareCall("call pSolicitud(?)");
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
            colum.getColumn(3).setPreferredWidth(100);
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
            colum.getColumn(9).setMinWidth(0);
            colum.getColumn(9).setMaxWidth(0);
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

    public String insertarSolicitud() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO solicitud VALUES (?,?,?,?,?)");
            ps.setInt(1, solNro);
            ps.setString(2, solFecha);
            ps.setString(3, solDescripcion);
            ps.setInt(4, cliCodigo);
            ps.setInt(5, esCodigo);
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

    public String actualizarSolicitud() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE solicitud SET escodigo = ?, soldescripcion = ? WHERE solnro = ?");
            ps.setInt(1, esCodigo);
            ps.setString(2, solDescripcion);
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

    public Collection<?> recuperaDatos(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
