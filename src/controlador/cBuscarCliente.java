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
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarCliente implements ActionListener, DocumentListener {

// variables
    eCliente mod;
    frmBuscar dlg;
    ArrayList<eCliente> lCliente = new ArrayList<>();

    public cBuscarCliente(eCliente mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Cliente");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Código, RUC/CIN, Nombres y Apellidos");
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

    public ArrayList<eCliente> devolverCliente() {
        return lCliente;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setCliCodigo((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setCliRuc((String) this.dlg.jTable.getValueAt(select, 1));
                    mod.setCliNombre((String) this.dlg.jTable.getValueAt(select, 3));
                    mod.setCliApellido((String) this.dlg.jTable.getValueAt(select, 4));
                    mod.setCliTelefono((String) this.dlg.jTable.getValueAt(select, 5));

                    // agregar al Array
                    lCliente.add(mod);

                    // limpiando el campo de busqueda
                    this.dlg.txtCriterio.setText(null);
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
