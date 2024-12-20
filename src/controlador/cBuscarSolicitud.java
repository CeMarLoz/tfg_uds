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
import modelo.eCliente;
import modelo.eEstadoSolicitud;
import modelo.eSolicitud;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarSolicitud implements ActionListener, DocumentListener {

// variables
    eSolicitud mod;
    eCliente cliente;
    eEstadoSolicitud estado;
    frmBuscar dlg;
    ArrayList<eSolicitud> lSoli = new ArrayList<>();

    public cBuscarSolicitud(eSolicitud mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Solicitud");
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

    public ArrayList<eSolicitud> devolverSolicitud() {
        return lSoli;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                String nombre, apellido, ruc, descripcion;
                int codigo;
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setSolNro((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setCliCodigo((int) this.dlg.jTable.getValueAt(select, 5));
                    mod.setSolFecha((String) this.dlg.jTable.getValueAt(select, 2));
                    mod.setSolDescripcion((String) this.dlg.jTable.getValueAt(select, 9));

                    nombre = (String) this.dlg.jTable.getValueAt(select, 6);
                    apellido = (String) this.dlg.jTable.getValueAt(select, 7);
                    ruc = (String) this.dlg.jTable.getValueAt(select, 8);
                    cliente = new eCliente(nombre, apellido, ruc);
                    mod.setCliente(cliente);

                    codigo = (int) this.dlg.jTable.getValueAt(select, 4);
                    descripcion = (String) this.dlg.jTable.getValueAt(select, 3);
                    estado = new eEstadoSolicitud(codigo, descripcion);
                    mod.setEstado(estado);

                    // agregar al Array
                    lSoli.add(mod);

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
