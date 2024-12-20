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
public class eCliente extends cConex {

    private int cliCodigo, aux;
    private String cliNombre, cliApellido, cliRuc, cliTelefono;
    private ArrayList<eCliente> lCli = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eCliente(String cliNombre, String cliApellido, String cliRuc) {
        this.cliNombre = cliNombre;
        this.cliApellido = cliApellido;
        this.cliRuc = cliRuc;
    }

    public eCliente() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(int cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliApellido() {
        return cliApellido;
    }

    public void setCliApellido(String cliApellido) {
        this.cliApellido = cliApellido;
    }

    public String getCliRuc() {
        return cliRuc;
    }

    public void setCliRuc(String cliRuc) {
        this.cliRuc = cliRuc;
    }

    public String getCliTelefono() {
        return cliTelefono;
    }

    public void setCliTelefono(String cliTelefono) {
        this.cliTelefono = cliTelefono;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(clicodigo),0) + 1 FROM cliente");
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
            ps = con.prepareCall("call pCliente(?)");
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
            colum.getColumn(1).setPreferredWidth(100);
            colum.getColumn(2).setPreferredWidth(300);
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

    public ArrayList<eCliente> datosCliente() {
        this.con = cone();
        lCli.clear();

        try {
            ps = con.prepareStatement("SELECT * FROM cliente ORDER BY 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                eCliente cli = new eCliente();
                cli.setCliCodigo(rs.getInt(1));
                cli.setCliNombre(rs.getString(2));
                cli.setCliApellido(rs.getString(3));
                cli.setCliRuc(rs.getString(4));
                cli.setCliTelefono(rs.getString(5));
                lCli.add(cli);
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

        return lCli;
    }

    public ArrayList<eCliente> presupuestoCliente(int presupuesto) {
        this.con = cone();
        lCli.clear();

        try {
            ps = con.prepareStatement("SELECT i.* FROM presupuesto p INNER JOIN cuenta c ON p.cuenro = c.cuenro "
                    + " INNER JOIN lecturamedidor l ON c.lmcodigo = l.lmcodigo INNER JOIN conexion x ON l.connro = x.connro "
                    + " INNER JOIN solicitud s ON x.solnro = s.solnro "
                    + " INNER JOIN cliente i ON s.clicodigo = i.clicodigo WHERE p.prenro = ?");
            ps.setInt(1, presupuesto);
            rs = ps.executeQuery();
            while (rs.next()) {
                eCliente cli = new eCliente();
                cli.setCliCodigo(rs.getInt(1));
                cli.setCliNombre(rs.getString(2));
                cli.setCliApellido(rs.getString(3));
                cli.setCliRuc(rs.getString(4));
                cli.setCliTelefono(rs.getString(5));
                lCli.add(cli);
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

        return lCli;
    }

    public String insertarCliente() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO cliente VALUES (?,?,?,?,?)");
            ps.setInt(1, cliCodigo);
            ps.setString(2, cliNombre);
            ps.setString(3, cliApellido);
            ps.setString(4, cliRuc);
            ps.setString(5, cliTelefono);
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

    public String actualizarCliente() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE cliente SET clicodigo = ?, clinombre = ?, cliapellido = ?, "
                    + "cliruc = ?, clitelefono = ? WHERE clicodigo = ?");
            ps.setInt(1, cliCodigo);
            ps.setString(2, cliNombre);
            ps.setString(3, cliApellido);
            ps.setString(4, cliRuc);
            ps.setString(5, cliTelefono);
            ps.setInt(6, aux);
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

    public String eliminarCliente() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM cliente WHERE clicodigo = ?");
            ps.setInt(1, cliCodigo);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
