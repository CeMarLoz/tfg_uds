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
public class eCategoriaConexion extends cConex {

    private int ccCodigo, ccMtCubico, aux;
    private String ccNombre;
    private double ccMonto, ccExedente, ccMora;

    PreparedStatement ps;
    Connection con;
    ResultSet rs;

    public eCategoriaConexion() {
        this.ps = null;
        this.con = null;
        this.rs = null;
    }

    public eCategoriaConexion(int ccCodigo, String ccNombre) {
        this.ccCodigo = ccCodigo;
        this.ccNombre = ccNombre;
    }

    public int getCcCodigo() {
        return ccCodigo;
    }

    public void setCcCodigo(int ccCodigo) {
        this.ccCodigo = ccCodigo;
    }

    public int getCcMtCubico() {
        return ccMtCubico;
    }

    public void setCcMtCubico(int ccMtCubico) {
        this.ccMtCubico = ccMtCubico;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public String getCcNombre() {
        return ccNombre;
    }

    public void setCcNombre(String ccNombre) {
        this.ccNombre = ccNombre;
    }

    public double getCcMonto() {
        return ccMonto;
    }

    public void setCcMonto(double ccMonto) {
        this.ccMonto = ccMonto;
    }

    public double getCcExedente() {
        return ccExedente;
    }

    public void setCcExedente(double ccExedente) {
        this.ccExedente = ccExedente;
    }

    public double getCcMora() {
        return ccMora;
    }

    public void setCcMora(double ccMora) {
        this.ccMora = ccMora;
    }

    public void cargaComboCategoria(JComboBox<eCategoriaConexion> cBox) {
        this.con = cone();

        try {
            ps = con.prepareStatement("SELECT cccodigo, ccnombre FROM categoriaconexion ORDER BY cccodigo");
            rs = ps.executeQuery();
            while (rs.next()) {
                cBox.addItem(new eCategoriaConexion(rs.getInt(1), rs.getString(2)));
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
        return this.ccNombre;
    }

    @Override
    public boolean equals(Object obj) {
        return this.ccCodigo == ((eCategoriaConexion) obj).ccCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.ccCodigo;
        return hash;
    }
}
