package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author CesarMatias
 */
public class ePuntoExpedicion extends cConex {

    private int pexpNro, estNro, aux;
    private String pexpDescripcion;
    private boolean pexpActivo;

    ArrayList<ePuntoExpedicion> lMotDesc = new ArrayList<>();

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public ePuntoExpedicion(int pexpNro, int estNro, String pexpDescripcion) {
        this.pexpNro = pexpNro;
        this.estNro = estNro;
        this.pexpDescripcion = pexpDescripcion;
    }

    public ePuntoExpedicion() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public int getPexpNro() {
        return pexpNro;
    }

    public void setPexpNro(int pexpNro) {
        this.pexpNro = pexpNro;
    }

    public int getEstNro() {
        return estNro;
    }

    public void setEstNro(int estNro) {
        this.estNro = estNro;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getPexpDescripcion() {
        return pexpDescripcion;
    }

    public void setPexpDescripcion(String pexpDescripcion) {
        this.pexpDescripcion = pexpDescripcion;
    }

    public boolean isPexpActivo() {
        return pexpActivo;
    }

    public void setPexpActivo(boolean pexpActivo) {
        this.pexpActivo = pexpActivo;
    }

    public int nuevoCodigo() {
        this.con = cone();
        int id = 0;
        try {
            ps = con.prepareStatement("SELECT ifnull(max(mdpexpNro),0) + 1 FROM motivodesconexion");
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

    public ArrayList<ePuntoExpedicion> recuperaDatos(boolean rep) {
        this.con = cone();
        lMotDesc.clear();
        try {
            if (rep) {
                ps = con.prepareStatement("SELECT mdpexpNro, mdpexpDescripcion FROM motivodesconexion");
            } else {
                ps = con.prepareStatement("SELECT mdpexpNro, mdpexpDescripcion FROM motivodesconexion WHERE mdpexpNro = ?");
                ps.setInt(1, pexpNro);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                ePuntoExpedicion esta = new ePuntoExpedicion();
                esta.setPexpNro(rs.getInt(1));
                esta.setPexpDescripcion(rs.getString(2));
                lMotDesc.add(esta);
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
        return lMotDesc;
    }

    public String insertarMotivoDesconexion() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("INSERT INTO motivodesconexion VALUES (?,?)");
            ps.setInt(1, pexpNro);
            ps.setString(2, pexpDescripcion);
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

    public String actualizarMotivoDesconexion() {
        String mensaje = null;
        this.con = cone();
        try {
            ps = con.prepareStatement("UPDATE motivodesconexion SET mdpexpNro = ?, mdpexpDescripcion = ? WHERE mdpexpNro = ?");
            ps.setInt(1, pexpNro);
            ps.setString(2, pexpDescripcion);
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

    public String eliminarMotivoDesconexion() {
        this.con = cone();
        try {
            ps = con.prepareStatement("DELETE FROM motivodesconexion WHERE mdpexpNro = ?");
            ps.setInt(1, pexpNro);
            ps.execute();
            return null;
        } catch (SQLException ex) {
            System.err.println(ex.getErrorCode() + " - " + ex.getMessage());
            return "No se pudo eliminar!";
        }
    }

    @Override
    public String toString() {
        return this.pexpDescripcion;
    }

    @Override
    public boolean equals(Object obj) {
        return this.pexpNro == ((ePuntoExpedicion) obj).pexpNro;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.pexpNro;
        return hash;
    }

    public void carCombo(JComboBox<ePuntoExpedicion> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT pexpnro, estnro, pexpdescripcion FROM puntoexpedicion ORDER BY pexpnro");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new ePuntoExpedicion(rs.getInt(1), rs.getInt(2), rs.getString(3)));
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
}
