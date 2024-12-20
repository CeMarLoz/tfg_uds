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
import modelo.eCiudad;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarCiudad implements ActionListener, DocumentListener {

// variables
    eCiudad mod;
    frmBuscar dlg;
    ArrayList<eCiudad> lCiudad = new ArrayList<>();

    public cBuscarCiudad(eCiudad mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Ciudad");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Código y Descripción");
        this.dlg.lblMensaje.setText(null);

        // cargado de tabla
        cargarTabla();

        // agregar las acciones
        this.dlg.btnAgregar.addActionListener(this);
        this.dlg.txtCriterio.getDocument().addDocumentListener(this);
    }

    private void cargarTabla() {
        this.mod.recuperaCiudad(this.dlg.jTable, this.dlg.txtCriterio.getText());
    }

    public ArrayList<eCiudad> devolverCiudad() {
        return lCiudad;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setCiuCodigo((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setCiuDescripcion((String) this.dlg.jTable.getValueAt(select, 1));

                    // agregar al Array
                    lCiudad.add(mod);

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
