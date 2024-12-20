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
public class eTimbrado extends cConex {

    private int timCodigo, timNro, timNroInicial, timNroFinal, tdocCodigo, pextNro, estNro;
    private String timVencimiento;
    private boolean tinActivo;
    ArrayList<eTimbrado> lTim = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eTimbrado() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getTimCodigo() {
        return timCodigo;
    }

    public void setTimCodigo(int timCodigo) {
        this.timCodigo = timCodigo;
    }

    public int getTimNro() {
        return timNro;
    }

    public void setTimNro(int timNro) {
        this.timNro = timNro;
    }

    public int getTimNroInicial() {
        return timNroInicial;
    }

    public void setTimNroInicial(int timNroInicial) {
        this.timNroInicial = timNroInicial;
    }

    public int getTimNroFinal() {
        return timNroFinal;
    }

    public void setTimNroFinal(int timNroFinal) {
        this.timNroFinal = timNroFinal;
    }

    public int getTdocCodigo() {
        return tdocCodigo;
    }

    public void setTdocCodigo(int tdocCodigo) {
        this.tdocCodigo = tdocCodigo;
    }

    public int getPextNro() {
        return pextNro;
    }

    public void setPextNro(int pextNro) {
        this.pextNro = pextNro;
    }

    public int getEstNro() {
        return estNro;
    }

    public void setEstNro(int estNro) {
        this.estNro = estNro;
    }

    public String getTimVencimiento() {
        return timVencimiento;
    }

    public void setTimVencimiento(String timVencimiento) {
        this.timVencimiento = timVencimiento;
    }

    public boolean isTinActivo() {
        return tinActivo;
    }

    public void setTinActivo(boolean tinActivo) {
        this.tinActivo = tinActivo;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(roltimCodigo),0) + 1 FROM tim");
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

    public ArrayList<eTimbrado> recuperaDatos(boolean doc) {
        this.con = cone();
        lTim.clear();
        try {
            if (doc) {
                ps = con.prepareStatement("SELECT timcodigo, timnro FROM timbrado "
                        + " WHERE timcodigo = ? AND timactivo <> 0");
                ps.setInt(1, tdocCodigo);
            } else {
                ps = con.prepareStatement("SELECT roltimCodigo, roldescripcion FROM tim WHERE roltimCodigo = ?");
                ps.setInt(1, timCodigo);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                eTimbrado tim = new eTimbrado();
                tim.setTimCodigo(rs.getInt(1));
                tim.setTimNro(rs.getInt(2));
                lTim.add(tim);
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
        return lTim;
    }

    public String insertarRol() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO tim VALUES (?,?)");
            ps.setInt(1, timCodigo);
            ps.setInt(2, timNro);
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

    public String actualizarRol() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE tim SET roltimCodigo = ?, roldescripcion = ? WHERE roltimCodigo = ?");
            ps.setInt(1, timCodigo);
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
}
