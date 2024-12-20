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
import modelo.eCuenta;
import modelo.eMedidor;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarCuenta implements ActionListener, DocumentListener {

// variables
    eCuenta mod;
    frmBuscar dlg;
    ArrayList<eCuenta> lCuen = new ArrayList<>();

    public cBuscarCuenta(eCuenta mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Cuenta");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Número y Medidor");
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

    public ArrayList<eCuenta> devolverCuenta() {
        return lCuen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {

            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                boolean activo;
                if (select >= 0) {
                    activo = (int) this.dlg.jTable.getValueAt(select, 5) == 1;
                    // seteando el modelo 
                    mod.setCueNro((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setLmCodigo((int) this.dlg.jTable.getValueAt(select, 3));
                    mod.setCueFecha((String) this.dlg.jTable.getValueAt(select, 2));
                    mod.setCueAnulado(activo);
                    mod.setMedidor(new eMedidor((int) this.dlg.jTable.getValueAt(select, 4), (int) this.dlg.jTable.getValueAt(select, 1)));
                    // agregar al Array
                    lCuen.add(mod);

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
