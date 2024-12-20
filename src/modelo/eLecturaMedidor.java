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
public class eLecturaMedidor extends cConex {

    private int lmCodigo, conNro, cubico;
    private String lmFecha;
    private double lmLectura, monto, exceden;
    private eMedidor medidor;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public int getLmCodigo() {
        return lmCodigo;
    }

    public void setLmCodigo(int lmCodigo) {
        this.lmCodigo = lmCodigo;
    }

    public int getConNro() {
        return conNro;
    }

    public void setConNro(int conNro) {
        this.conNro = conNro;
    }

    public String getLmFecha() {
        return lmFecha;
    }

    public void setLmFecha(String lmFecha) {
        this.lmFecha = lmFecha;
    }

    public double getLmLectura() {
        return lmLectura;
    }

    public void setLmLectura(double lmLectura) {
        this.lmLectura = lmLectura;
    }

    public eMedidor getMedidor() {
        return medidor;
    }

    public void setMedidor(eMedidor medidor) {
        this.medidor = medidor;
    }

    public int getCubico() {
        return cubico;
    }

    public void setCubico(int cubico) {
        this.cubico = cubico;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getExceden() {
        return exceden;
    }

    public void setExceden(double exceden) {
        this.exceden = exceden;
    }

    /**
     * @autor CesarMatias
     */
    public eLecturaMedidor() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(lmcodigo),0) + 1 FROM lecturamedidor");
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
            ps = con.prepareCall("call pLecturaMedidor(?)");
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
            colum.getColumn(1).setPreferredWidth(100);
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

    public String insertarLecturaMedidor() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO lecturamedidor VALUES (?,?,?,?)");
            ps.setInt(1, lmCodigo);
            ps.setString(2, lmFecha);
            ps.setDouble(3, lmLectura);
            ps.setInt(4, conNro);
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

    public String eliminarLecturaMedidor() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM lecturamedidor WHERE lmcodigo = ?");
            ps.setInt(1, lmCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    public int recuperaLecturaAnterior() {
        this.con = cone();
        int lectura = 0;
        try {
            ps = con.prepareCall("call pLecturaAnt(?,?)");
            ps.setInt(1, conNro);
            ps.setInt(2, lmCodigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                lectura = rs.getInt(1);
                return lectura;
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

        return lectura;
    }
}
