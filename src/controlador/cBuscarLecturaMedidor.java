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
import modelo.eLecturaMedidor;
import modelo.eMedidor;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarLecturaMedidor implements ActionListener, DocumentListener {

// variables
    eLecturaMedidor mod;
    eMedidor mMed;
    frmBuscar dlg;
    ArrayList<eLecturaMedidor> lLecturaMed = new ArrayList<>();

    public cBuscarLecturaMedidor(eLecturaMedidor mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar LecturaMedidor");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Código y Medidor");
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

    public ArrayList<eLecturaMedidor> devolverLecturaMedidor() {
        return lLecturaMed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setLmCodigo((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setLmFecha((String) this.dlg.jTable.getValueAt(select, 2));
                    mod.setLmLectura((double) this.dlg.jTable.getValueAt(select, 3));
                    mod.setConNro((int) this.dlg.jTable.getValueAt(select, 4));
                    mod.setMedidor(new eMedidor((int) this.dlg.jTable.getValueAt(select, 5), (int) this.dlg.jTable.getValueAt(select, 1)));

                    // para cuenta
                    mod.setMonto((double) this.dlg.jTable.getValueAt(select, 6));
                    mod.setCubico((int) this.dlg.jTable.getValueAt(select, 7));
                    mod.setExceden((double) this.dlg.jTable.getValueAt(select, 8));

                    // agregar al Array
                    lLecturaMed.add(mod);

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
