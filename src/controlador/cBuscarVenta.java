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
import modelo.eFacturaVenta;
import vista.frmBuscar;

/**
 *
 * @author CesarMatias
 */
public class cBuscarVenta implements ActionListener, DocumentListener {

// variables
    eFacturaVenta mod;
    eCliente cliente;
    frmBuscar dlg;
    ArrayList<eFacturaVenta> lVen = new ArrayList<>();

    public cBuscarVenta(eFacturaVenta mod, frmBuscar dlg) {
        this.mod = mod;
        this.dlg = dlg;

        // inicializados
        this.dlg.setTitle("Buscar Factura Venta");
        this.dlg.setResizable(false);
        this.dlg.lblCriterio.setText("Criterio: Factura, RUC/CIN, Nombres y Apellidos");
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

    public ArrayList<eFacturaVenta> devolverVenta() {
        return lVen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dlg.btnAgregar) {
            String nombre, apellido, ruc;
            boolean activo;
            if (this.dlg.jTable.getSelectedRow() != -1) {
                int select = this.dlg.jTable.getSelectedRow();
                if (select >= 0) {
                    activo = (int) this.dlg.jTable.getValueAt(select, 8) == 1;
                    // seteando el modelo 
                    mod.setVenNro((int) this.dlg.jTable.getValueAt(select, 4));
                    mod.setIdVenta((int) this.dlg.jTable.getValueAt(select, 7));
                    mod.setVenContado(activo);

                    nombre = (String) this.dlg.jTable.getValueAt(select, 5);
                    apellido = (String) this.dlg.jTable.getValueAt(select, 6);
                    ruc = null;
                    cliente = new eCliente(nombre, apellido, ruc);
                    mod.setCliente(cliente);

                    // agregar al Array
                    lVen.add(mod);

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
