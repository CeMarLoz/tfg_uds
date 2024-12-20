package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.eCliente;
import modelo.eFacturaVenta;
import modelo.eItemCategoria;
import modelo.eLogin;
import modelo.ePresupuesto;
import modelo.eServicio;
import modelo.eTimbrado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import vista.frmBuscar;
import vista.frmFacturaVenta;

/**
 *
 * @author CesarMatias
 */
public class cFacturaVenta implements ActionListener, DocumentListener {

    // variables
    eFacturaVenta mod;
    eTimbrado mTim;
    eCliente mCli;
    eServicio mSer;
    ePresupuesto mPre;

    eItemCategoria mCat;
    int venNro, timCodigo, cliCodigo, impValor;
    double total;

    frmFacturaVenta vis;
    frmBuscar dlg;

    Date date;

    // tabla detale
    String[] titulos = {"Código", "Descripción", "Cantidad", "Precio", "Impuesto", "Sub Total"};
    DefaultTableModel tModelo = new DefaultTableModel(null, titulos);

    //Instancia del formato de fecha
    SimpleDateFormat formatFecha;
    SimpleDateFormat formatInversor;
    NumberFormat formatNumber = NumberFormat.getInstance();

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cFacturaVenta(frmFacturaVenta vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eFacturaVenta();
        this.mTim = new eTimbrado(); // modelo timbrado
        this.mCli = new eCliente(); // modelo cliente
        this.mSer = new eServicio(); // modelo item
        this.mPre = new ePresupuesto(); // modelo item
        this.mCat = new eItemCategoria(); // modelo item categoria
        this.vis = vis;
        this.vis.setTitle("Factura Venta");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);

        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarCliente.addActionListener(this);
        this.vis.btnBuscarServicio.addActionListener(this);
        this.vis.btnBuscarPresupuesto.addActionListener(this);
        this.vis.btnMas.addActionListener(this);
        this.vis.btnMenos.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtTotal.getDocument().addDocumentListener(this);
        this.vis.txtCliente.getDocument().addDocumentListener(this);
        this.vis.txtPrecio.getDocument().addDocumentListener(this);
        this.vis.txtCantidad.getDocument().addDocumentListener(this);

        iniciar();

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
        cUtiles.habilitarCampos(this.vis.pDetalle, false, new Object[]{});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    private void iniciar() {
        this.vis.tblTabla.setModel(tModelo);

        TableColumnModel colum = this.vis.tblTabla.getColumnModel();
        colum.getColumn(0).setPreferredWidth(80);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(100);
        colum.getColumn(3).setPreferredWidth(100);
        colum.getColumn(4).setPreferredWidth(80);
        colum.getColumn(5).setPreferredWidth(100);
    }

    private void calculosTotales() {
        int impueto = 0, iva10 = 0, iva5 = 0, iva2 = 0, totaliva;
        double importe = 0;
        total = 0;

        for (int i = 0; i < tModelo.getRowCount(); i++) {
            // importe
            importe = Double.parseDouble(tModelo.getValueAt(i, 5).toString().replace(".", "").replace(",", "."));

            // impuesto
            impueto = (int) tModelo.getValueAt(i, 4);

            if (impueto == 10) { // iva 10
                iva10 += Math.round((importe * impueto) / (100 + impueto));
            }

            if (impueto == 5) { // iva 5
                iva5 += Math.round((importe * impueto) / (100 + impueto));
            }

            if (impueto == 12) { // iva 12
                iva10 += Math.round((importe * 10) / (100 + impueto));
                iva2 += Math.round((importe * 2) / (100 + impueto));
            }

            // total
            total += Double.parseDouble(tModelo.getValueAt(i, 5).toString().replace(".", "").replace(",", "."));
        }
        // total iva
        totaliva = iva10 + iva5 + iva2;

        this.vis.txtTotal.setText(formatNumber.format(total));
        this.vis.txtIva10.setText(formatNumber.format(iva10));
        this.vis.txtIva5.setText(formatNumber.format(iva5));
        this.vis.txtIva2.setText(formatNumber.format(iva2));
        this.vis.txtTotalIva.setText(formatNumber.format(totaliva));

        if (total > 0 && !this.vis.txtCliente.getText().isEmpty()) {
            this.vis.btnGuardar.setEnabled(true);
        } else {
            this.vis.btnGuardar.setEnabled(false);
        }
    }

    private void recuperaTimbrado() {
        // seteo de los campos
        this.mTim.setTdocCodigo(1);

        // recuperando datos
        Iterator<eTimbrado> itTim = this.mTim.recuperaDatos(true).iterator();

        if (itTim.hasNext()) {
            this.mTim = itTim.next();
            timCodigo = this.mTim.getTimCodigo();
            this.vis.txtTimbrado.setText(String.valueOf(this.mTim.getTimNro()));
        }
    }

    private void imprimir() {

        try {
            HashMap param = new HashMap();
            param.put("P_NRO", this.mod.getIdVenta());
            param.put("P_VENDEDOR", eLogin.Alias);

            JasperPrint jp = JasperFillManager.fillReport("src/reporte/rptFactVenta.jasper", param, this.mod.cone());
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            String facturaNro;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.rdContado, this.vis.rdCredito,
                this.vis.btnBuscarPresupuesto});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            venNro = this.mod.nuevoNro();
            facturaNro = String.format("%03d", eLogin.Establecimiento) + "-"
                    + String.format("%03d", eLogin.PuntoExpedicion) + "-"
                    + String.format("%07d", venNro);

            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            this.vis.txtNro.setText(facturaNro);
            this.vis.txtNro.grabFocus();

            // recuperando timbrado valido
            recuperaTimbrado();
        }

        if (e.getSource() == this.vis.btnGuardar) {
            String mensaje = null;

            try {
                date = formatFecha.parse(this.vis.txtFecha.getText().trim());
            } catch (ParseException ex) {
            }

            // seteo de los campos
            this.mod.setVenNro(venNro);
            this.mod.setVenFecha(formatInversor.format(date));
            this.mod.setVenContado(this.vis.rdContado.isSelected());
            this.mod.setUsucodigo(eLogin.Usuario);
            this.mod.setTimCodigo(timCodigo);
            this.mod.setCliCodigo(cliCodigo);
            // llamada a la clase para insertar
            mensaje = this.mod.insertarFacturaVenta();

            if (mensaje == null) {
                // seteando campños
                for (int i = 0; i < this.vis.tblTabla.getRowCount(); i++) {
                    this.mod.setSerCodigo(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 0).toString()));
                    this.mod.setVdetCantidad(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 2).toString().replace(".", "").replace(",", ".")));
                    this.mod.setVdetPrecio(Double.parseDouble(this.vis.tblTabla.getValueAt(i, 3).toString().replace(".", "").replace(",", ".")));
                    this.mod.setVdetImpuesto(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 4).toString()));

                    // llamada a la clase para insertar
                    mensaje = this.mod.insertarFacturaVentaDet();
                    if (mensaje != null) {
                        break;
                    }
                }

                imprimir();

                this.vis.lblMensaje.setText("Registro Guardado");

            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            this.vis.rdContado.setSelected(true);
            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);
            cUtiles.limpiarCampos(this.vis.pDetalle);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);
            cUtiles.limpiarCampos(this.vis.pDetalle);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarCliente) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarCliente cBuscarCliente = new cBuscarCliente(mCli, dlg);
            dlg.setVisible(true);

            Iterator<eCliente> itCliente = cBuscarCliente.devolverCliente().iterator();

            if (itCliente.hasNext()) {
                this.mCli = itCliente.next();
                this.cliCodigo = this.mCli.getCliCodigo();
                this.vis.txtCliente.setText(this.mCli.getCliRuc());
                this.vis.txtRazonSocial.setText(this.mCli.getCliNombre() + " " + this.mCli.getCliApellido());
            }

            if (!this.vis.txtCliente.getText().trim().isEmpty()) {
                this.vis.btnBuscarCliente.transferFocus();
            }
        }

        if (e.getSource() == this.vis.btnBuscarServicio) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarServicio cBuscarServicio = new cBuscarServicio(mSer, dlg);
            dlg.setVisible(true);

            Iterator<eServicio> itItem = cBuscarServicio.devolverServicio().iterator();

            if (itItem.hasNext()) {
                this.mSer = itItem.next();
                this.vis.txtPrecio.setText(formatNumber.format(this.mSer.getSerPrecio()));
                this.vis.txtCodigo.setText(String.valueOf(this.mSer.getSerCodigo()));
                this.vis.txtDescripcion.setText(this.mSer.getSerDescripcion());
                this.vis.txtCantidad.setText("1");
            }

            if (!this.vis.txtDescripcion.getText().trim().isEmpty()) {
                this.vis.btnMas.setEnabled(true);
                this.vis.btnBuscarServicio.transferFocus();
                mCat.setIcCodigo(this.mSer.getIccCodigo());
                impValor = mCat.recuperaImpuestoValor();
            }
        }

        if (e.getSource() == this.vis.btnBuscarPresupuesto) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);

            cBuscarPresupuesto cBuscarPresupuesto = new cBuscarPresupuesto(mPre, dlg);
            dlg.setVisible(true);

            Iterator<ePresupuesto> itPresupuesto = cBuscarPresupuesto.devolverPresupuesto().iterator();

            if (itPresupuesto.hasNext()) {
                this.mPre = itPresupuesto.next();
                this.vis.txtPresupuesto.setText(String.valueOf(this.mPre.getPreNro()));

                // cargando detalle
                this.mPre.presupuestoFactura(tModelo, this.vis.txtPresupuesto.getText());

                Iterator<eCliente> itCliente = mCli.presupuestoCliente(this.mPre.getPreNro()).iterator();

                if (itCliente.hasNext()) {
                    this.mCli = itCliente.next();
                    this.cliCodigo = this.mCli.getCliCodigo();
                    this.vis.txtCliente.setText(this.mCli.getCliRuc());
                    this.vis.txtRazonSocial.setText(this.mCli.getCliNombre());
                    this.vis.btnBuscarCliente.setEnabled(false);
                }

                // calculando totales
                calculosTotales();
                if (this.vis.tblTabla.getRowCount() > 0) {
                    this.vis.btnGuardar.setEnabled(true);
                }
            }

            if (!this.vis.txtPresupuesto.getText().trim().isEmpty()) {
                this.vis.btnBuscarPresupuesto.transferFocus();
            }
        }

        if (e.getSource() == this.vis.btnMas) {
            tModelo = (DefaultTableModel) this.vis.tblTabla.getModel();
            tModelo.addRow(new Object[]{this.vis.txtCodigo.getText(), this.vis.txtDescripcion.getText(),
                this.vis.txtCantidad.getText(), this.vis.txtPrecio.getText(), impValor, this.vis.txtSubTotal.getText()});

            calculosTotales();

            this.vis.txtCodigo.setText(null);
            this.vis.txtDescripcion.setText(null);
            this.vis.txtPrecio.setText(null);
            this.vis.txtCantidad.setText(null);

        }

        if (e.getSource() == this.vis.btnMenos) {
            if (this.vis.tblTabla.getSelectedRow() != -1) {
                tModelo.removeRow(this.vis.tblTabla.getSelectedRow());

                // calculando subtotal
                calculosTotales();
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (!this.vis.txtCantidad.getText().isEmpty() && !this.vis.txtPrecio.getText().isEmpty()) {

            int cantidad;
            double precio, subTotal = 0;

            cantidad = Integer.parseInt(this.vis.txtCantidad.getText());
            precio = Double.parseDouble(this.vis.txtPrecio.getText().replace(".", "").replace(",", "."));

            subTotal = cantidad * precio;
            this.vis.txtSubTotal.setText(formatNumber.format(subTotal));

            if (subTotal > 0) {
                this.vis.btnMas.setEnabled(true);
            } else {
                this.vis.btnMas.setEnabled(false);
            }

        } else {
            this.vis.txtSubTotal.setText(null);
            this.vis.btnMas.setEnabled(false);
        }

        if (total > 0 && !this.vis.txtCliente.getText().isEmpty()) {
            this.vis.btnGuardar.setEnabled(true);
        } else {
            this.vis.btnGuardar.setEnabled(false);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (!this.vis.txtCantidad.getText().isEmpty() && !this.vis.txtPrecio.getText().isEmpty()) {

            int cantidad;
            double precio, subTotal = 0;

            cantidad = Integer.parseInt(this.vis.txtCantidad.getText());
            precio = Double.parseDouble(this.vis.txtPrecio.getText().replace(".", "").replace(",", "."));

            subTotal = cantidad * precio;
            this.vis.txtSubTotal.setText(formatNumber.format(subTotal));

            if (subTotal > 0) {
                this.vis.btnMas.setEnabled(true);
            } else {
                this.vis.btnMas.setEnabled(false);
            }

        } else {
            this.vis.txtSubTotal.setText(null);
            this.vis.btnMas.setEnabled(false);
        }

        if (total > 0 && !this.vis.txtCliente.getText().isEmpty()) {
            this.vis.btnGuardar.setEnabled(true);
        } else {
            this.vis.btnGuardar.setEnabled(false);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!this.vis.txtCantidad.getText().isEmpty() && !this.vis.txtPrecio.getText().isEmpty()) {

            int cantidad;
            double precio, subTotal = 0;

            cantidad = Integer.parseInt(this.vis.txtCantidad.getText());
            precio = Double.parseDouble(this.vis.txtPrecio.getText().replace(".", "").replace(",", "."));

            subTotal = cantidad * precio;
            this.vis.txtSubTotal.setText(formatNumber.format(subTotal));

            if (subTotal > 0) {
                this.vis.btnMas.setEnabled(true);
            } else {
                this.vis.btnMas.setEnabled(false);
            }

        } else {
            this.vis.txtSubTotal.setText(null);
            this.vis.btnMas.setEnabled(false);
        }

        if (total > 0 && !this.vis.txtCliente.getText().isEmpty()) {
            this.vis.btnGuardar.setEnabled(true);
        } else {
            this.vis.btnGuardar.setEnabled(false);
        }
    }
}
