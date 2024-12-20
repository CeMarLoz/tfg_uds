/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.cConex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author CesarMatias
 */
public class eMedidor extends cConex {

    private int medCodigo, medNro, aux;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eMedidor() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public eMedidor(int medCodigo, int medNro) {
        this.medCodigo = medCodigo;
        this.medNro = medNro;
    }

    public int getMedCodigo() {
        return medCodigo;
    }

    public void setMedCodigo(int medCodigo) {
        this.medCodigo = medCodigo;
    }

    public int getMedNro() {
        return medNro;
    }

    public void setMedNro(int medNro) {
        this.medNro = medNro;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public void cargaComboMedidor(JComboBox<eMedidor> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT medcodigo, mednro FROM medidor ORDER BY medcodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eMedidor(rs.getInt(1), rs.getInt(2)));
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

    @Override
    public String toString() {
        return this.medNro + "";
    }

    @Override
    public boolean equals(Object obj) {
        return this.medCodigo == ((eMedidor) obj).medCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.medCodigo;
        return hash;
    }
}
