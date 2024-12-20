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
import modelo.eCalle;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarCalle implements ActionListener, DocumentListener {

// variables
    eCalle mod;
    frmBuscar dlg;
    ArrayList<eCalle> lCalle = new ArrayList<>();
    
    public cBuscarCalle(eCalle mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Calle");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Código y Nombre");
        this.dlg.lblMensaje.setText(null);

        // cargado de tabla
        cargarTabla();

        // agregar las acciones
        this.dlg.btnAgregar.addActionListener(this);
        this.dlg.txtCriterio.getDocument().addDocumentListener(this);
    }
    
    private void cargarTabla() {
        this.mod.recuperaCalle(this.dlg.jTable, this.dlg.txtCriterio.getText());
    }
    
    public ArrayList<eCalle> devolverCalle() {
        return lCalle;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {
            
            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    // seteando el modelo 
                    mod.setCalCodigo((int) this.dlg.jTable.getValueAt(select, 0));
                    mod.setCalNombre((String) this.dlg.jTable.getValueAt(select, 1));

                    // agregar al Array
                    lCalle.add(mod);

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
