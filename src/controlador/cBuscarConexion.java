/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eCategoriaConexion;
import modelo.eCliente;
import modelo.eConexion;
import modelo.eMedidor;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarConexion implements ActionListener, DocumentListener {

// variables
    eConexion mod;
    eCliente cliente;
    frmBuscar dlg;
    ArrayList<eConexion> lConexion = new ArrayList<>();

    public cBuscarConexion(eConexion mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Conexion");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Número, RUC/CIN, Nombres y Apellidos");
        this.dlg.lblMensaje.setText(null);

        // cargado de tabla
        cargarTabla();

        // agregar las acciones
        this.dlg.btnAgregar.addActionListener(this);
        this.dlg.txtCriterio.getDocument().addDocumentListener(this);
    }

    private void cargarTabla() {
        this.mod.recuperaDatos(this.dlg.jTable, this.dlg.txtCriterio.getText());
    }

    public ArrayList<eConexion> devolverConexion() {
        return lConexion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                String nombre, apellido, ruc, descripcion;
                int codigo, nro;
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setConNro((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setConFecha((String) this.dlg.jTable.getValueAt(select, 2));
                    mod.setConLecturaAct((int) this.dlg.jTable.getValueAt(select, 3));
                    mod.setSNro((int) this.dlg.jTable.getValueAt(select, 6));

                    // cliente
                    nombre = (String) this.dlg.jTable.getValueAt(select, 4);
                    apellido = (String) this.dlg.jTable.getValueAt(select, 5);
                    ruc = null;
                    cliente = new eCliente(nombre, apellido, ruc);
                    mod.setCliente(cliente);

                    // categoria conexion
                    codigo = (int) this.dlg.jTable.getValueAt(select, 7);
                    descripcion = (String) this.dlg.jTable.getValueAt(select, 8);
                    mod.setCategoria(new eCategoriaConexion(codigo, descripcion));

                    // medidor
                    codigo = (int) this.dlg.jTable.getValueAt(select, 9);
                    nro = (int) this.dlg.jTable.getValueAt(select, 10);
                    mod.setMedidor(new eMedidor(codigo, nro));

                    // agregar al Array
                    lConexion.add(mod);

                    // limpiando el campo de busqueda
                    this.dlg.txtCriterio.setText(null);
                    this.dlg.removeAll();
                    this.dlg.dispose();
                }
            } else {
                this.dlg.lblMensaje.setText("No se ha seleccionado ninguna fila!");
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        cargarTabla();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        cargarTabla();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        cargarTabla();
    }
}
