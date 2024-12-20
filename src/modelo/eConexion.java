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
public class eConexion extends cConex {

    private int conNro, conLecturaAct, sNro, medCodigo, ccCodigo, calCodigo, aux;
    private String conFecha;
    private eCategoriaConexion categoria;
    private eMedidor medidor;
    private eCliente cliente;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eConexion() {
        this.ps = null;
        this.con = cone();
        this.rs = null;
    }

    public int getConNro() {
        return conNro;
    }

    public void setConNro(int conNro) {
        this.conNro = conNro;
    }

    public int getConLecturaAct() {
        return conLecturaAct;
    }

    public void setConLecturaAct(int conLecturaAct) {
        this.conLecturaAct = conLecturaAct;
    }

    public int getSNro() {
        return sNro;
    }

    public void setSNro(int sNro) {
        this.sNro = sNro;
    }

    public int getMedCodigo() {
        return medCodigo;
    }

    public void setMedCodigo(int medCodigo) {
        this.medCodigo = medCodigo;
    }

    public int getCcCodigo() {
        return ccCodigo;
    }

    public void setCcCodigo(int ccCodigo) {
        this.ccCodigo = ccCodigo;
    }

    public String getConFecha() {
        return conFecha;
    }

    public void setConFecha(String conFecha) {
        this.conFecha = conFecha;
    }

    public eCategoriaConexion getCategoria() {
        return categoria;
    }

    public void setCategoria(eCategoriaConexion categoria) {
        this.categoria = categoria;
    }

    public eMedidor getMedidor() {
        return medidor;
    }

    public void setMedidor(eMedidor medidor) {
        this.medidor = medidor;
    }

    public eCliente getCliente() {
        return cliente;
    }

    public void setCliente(eCliente cliente) {
        this.cliente = cliente;
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

    public int nuevoNro() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(connro),0) + 1 FROM conexion");
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
            ps = con.prepareCall("call pConexion(?)");
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
            colum.getColumn(6).setMinWidth(0);
            colum.getColumn(6).setMaxWidth(0);
            colum.getColumn(7).setMinWidth(0);
            colum.getColumn(7).setMaxWidth(0);
            colum.getColumn(8).setMinWidth(0);
            colum.getColumn(8).setMaxWidth(0);
            colum.getColumn(9).setMinWidth(0);
            colum.getColumn(9).setMaxWidth(0);
            colum.getColumn(10).setMinWidth(0);
            colum.getColumn(10).setMaxWidth(0);
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

    public void recuperaDatosDir(DefaultTableModel modelo, String criterio) {
        this.con = cone();
        modelo.setRowCount(0);

        try {
            ps = con.prepareCall("select c.calcodigo, c.calnombre from direccion d "
                    + " inner join calle c on d.calcodigo = c.calcodigo where d.connro = ?");
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

    public String insertarConexion() {
        this.con = cone();
        String mensaje = null;
        try {
            ps = con.prepareStatement("INSERT INTO conexion VALUES (?,?,?,?,?,?)");
            ps.setInt(1, conNro);
            ps.setString(2, conFecha);
            ps.setInt(3, conLecturaAct);
            ps.setInt(4, sNro);
            ps.setInt(5, medCodigo);
            ps.setInt(6, ccCodigo);
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

    public String actualizarConexion() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE conexion SET conlecturaact = ?, medcodigo = ?, cccodigo = ? "
                    + " WHERE connro = ?");
            ps.setInt(1, conLecturaAct);
            ps.setInt(2, medCodigo);
            ps.setInt(3, ccCodigo);
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

    public String insertarConexionDir() {
        String mensaje = null;

        try {
            ps = con.prepareStatement("INSERT INTO direccion VALUES (?,?)");
            ps.setInt(1, conNro);
            ps.setInt(2, calCodigo);
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
