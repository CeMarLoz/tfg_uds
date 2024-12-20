package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author CesarMatias
 */
public class eReclamo extends cConex {

    private int recNro, cliCodigo, aux;
    private String recDescripcion, recFecha;
    private eCliente cliente;

    ArrayList<eReclamo> lRec = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eReclamo() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public eCliente getCliente() {
        return cliente;
    }

    public void setCliente(eCliente cliente) {
        this.cliente = cliente;
    }

    public int getRecNro() {
        return recNro;
    }

    public void setRecNro(int recNro) {
        this.recNro = recNro;
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

    public String getRecDescripcion() {
        return recDescripcion;
    }

    public void setRecDescripcion(String recDescripcion) {
        this.recDescripcion = recDescripcion;
    }

    public String getRecFecha() {
        return recFecha;
    }

    public void setRecFecha(String recFecha) {
        this.recFecha = recFecha;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(recnro),0) + 1 FROM reclamo");
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

    public ArrayList<eReclamo> recuperaDatos() {
        String nombre, apellido, ruc;
        this.con = cone();
        lRec.clear();

        try {
            ps = con.prepareStatement("SELECT recnro, recfecha, recdescripcion, c.clicodigo, c.cliruc, c.clinombre, "
                    + " c.cliapellido FROM reclamo r "
                    + "INNER JOIN cliente c ON r.clicodigo = c.clicodigo WHERE recnro = ?");
            ps.setInt(1, recNro);

            rs = ps.executeQuery();
            while (rs.next()) {
                eReclamo rec = new eReclamo();

                rec.setRecNro(rs.getInt("recnro"));
                rec.setRecFecha(rs.getString("recfecha"));
                rec.setRecDescripcion(rs.getString("recdescripcion"));
                rec.setCliCodigo(rs.getInt("clicodigo"));

                nombre = rs.getString("clinombre");
                apellido = rs.getString("cliapellido");
                ruc = rs.getString("cliruc");
                cliente = new eCliente(nombre, apellido, ruc);

                rec.setCliente(cliente);
                lRec.add(rec);
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

        return lRec;
    }

    public String insertarReclamo() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO reclamo VALUES (?,?,?,?)");
            ps.setInt(1, recNro);
            ps.setString(2, recFecha);
            ps.setString(3, recDescripcion);
            ps.setInt(4, cliCodigo);
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

    public String actualizarReclamo() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE reclamo SET recnro = ?, recfecha = ?, recdescripcion = ?, "
                    + " clicodigo = ? WHERE recnro = ?");
            ps.setInt(1, recNro);
            ps.setString(2, recFecha);
            ps.setString(3, recDescripcion);
            ps.setInt(4, cliCodigo);
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

    public String eliminarReclamo() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM reclamo WHERE recnro = ?");
            ps.setInt(1, recNro);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }
}
