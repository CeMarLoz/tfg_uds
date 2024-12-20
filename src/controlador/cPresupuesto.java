package controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.ePresupuesto;
import modelo.eCuenta;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import vista.frmBuscar;
import vista.frmPresupuesto;

/**
 *
 * @author CesarMatias
 */
public class cPresupuesto implements ActionListener, DocumentListener, KeyListener {

    // variables
    ePresupuesto mod;
    eCuenta mCue;
    frmPresupuesto vis;
    frmBuscar dlg;
    Date date;

    // tabla detale
    String[] titulos = {"Concepto", "Cantidad", "Precio", "Sub Total", ""};
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
    public cPresupuesto(frmPresupuesto vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new ePresupuesto();
        this.mCue = new eCuenta(); // modelo de cuenta
        this.vis = vis;
        this.vis.setTitle("Presupuesto");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);

        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarPresupuesto.addActionListener(this);
        this.vis.btnBuscarCuenta.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtTotal.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtNro.addKeyListener(this);

        iniciar();

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarPresupuesto});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    private void iniciar() {
        this.vis.tblTabla.setModel(tModelo);

        TableColumnModel colum = this.vis.tblTabla.getColumnModel();
        colum.getColumn(0).setPreferredWidth(250);
        colum.getColumn(1).setPreferredWidth(100);
        colum.getColumn(2).setPreferredWidth(100);
        colum.getColumn(3).setPreferredWidth(100);
        colum.getColumn(4).setMinWidth(0);
        colum.getColumn(4).setMaxWidth(0);
    }

    private double totalPresupuesto() {
        double total;

        total = 0;
        for (int i = 0; i < tModelo.getRowCount(); i++) {
            total += Double.parseDouble(tModelo.getValueAt(i, 3).toString().replace(".", "").replace(",", "."));
        }
        return total;
    }

    private void imprimir() {

        try {
            HashMap param = new HashMap();
            param.put("P_NRO", this.vis.txtNro.getText());

            JasperPrint jp = JasperFillManager.fillReport("src/reporte/rptPresupuesto.jasper", param, this.mod.cone());
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCuenta, this.vis.txtNro});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            vis.txtNro.setText(String.valueOf(this.mod.nuevoNro()));
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnGuardar) {
            String mensaje = null;

            try {
                date = formatFecha.parse(this.vis.txtFecha.getText().trim());
            } catch (ParseException ex) {
            }

            // seteo de los campos
            this.mod.setPreNro(Integer.parseInt(this.vis.txtNro.getText()));
            this.mod.setPreFecha(formatInversor.format(date));
            this.mod.setCueNro(Integer.parseInt(this.vis.txtCuenta.getText().trim()));

            // llamada a la clase para insertar
            mensaje = this.mod.insertarPresupuesto();

            if (mensaje == null) {
                // seteando campños
                for (int i = 0; i < this.vis.tblTabla.getRowCount(); i++) {
                    this.mod.setSerCodigo(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 4).toString()));
                    this.mod.setPredetCantidad(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 1).toString().replace(".", "").replace(",", ".")));
                    this.mod.setPredetPrecio(Double.parseDouble(this.vis.tblTabla.getValueAt(i, 2).toString().replace(".", "").replace(",", ".")));

                    // llamada a la clase para insertar
                    mensaje = this.mod.insertarPresupuestoDet();
                    if (mensaje != null) {
                        break;
                    } else {
                        this.vis.lblMensaje.setText("Registro Guardado");
                    }
                }
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            imprimir();

            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarPresupuesto});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnEliminar) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea Eliminar?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                // seteo de los campos
                this.mod.setPreNro(Integer.parseInt(this.vis.txtNro.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarPresupuesto();
                if (mensaje == null) {
                    if (this.vis.tblTabla.getRowCount() >= 1) {
                        tModelo.setRowCount(0);
                    }
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarPresupuesto});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
            }
        }

        if (e.getSource() == this.vis.btnCancelar) {
            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarPresupuesto});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarCuenta) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);

            cBuscarCuenta cBuscarCuenta = new cBuscarCuenta(mCue, dlg);
            dlg.setVisible(true);

            Iterator<eCuenta> itCuenta = cBuscarCuenta.devolverCuenta().iterator();

            if (itCuenta.hasNext()) {
                this.mCue = itCuenta.next();
                this.vis.txtCuenta.setText(String.valueOf(this.mCue.getCueNro()));
                this.vis.txtMedidor.setText(String.valueOf(this.mCue.getMedidor().getMedNro()));

                // cargando detalle
                this.mCue.recuperaDatosDet(tModelo, this.vis.txtCuenta.getText());
                this.vis.txtTotal.setText(formatNumber.format(totalPresupuesto()));
            }
        }

        if (e.getSource() == this.vis.btnBuscarPresupuesto) {
            this.vis.lblMensaje.setText(null);
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);

            cBuscarPresupuesto cBuscarPresupuesto = new cBuscarPresupuesto(mod, dlg);
            dlg.setVisible(true);

            Iterator<ePresupuesto> itPresupuesto = cBuscarPresupuesto.devolverPresupuesto().iterator();

            if (itPresupuesto.hasNext()) {
                this.mod = itPresupuesto.next();
                this.vis.txtNro.setText(String.valueOf(this.mod.getPreNro()));

                // cargando detalle
                this.mod.recuperaDatosDet(tModelo, this.vis.txtNro.getText());

                this.vis.txtCuenta.setText(String.valueOf(this.mod.getCueNro()));
                this.vis.txtFecha.setText(this.mod.getPreFecha());
                this.vis.txtMedidor.setText(String.valueOf(this.mod.getMedidor().getMedNro()));
                this.vis.txtTotal.setText(formatNumber.format(totalPresupuesto()));
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar, this.vis.btnEliminar});
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9
                || this.vis.tblTabla.getRowCount() <= 0) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9
                || this.vis.tblTabla.getRowCount() <= 0) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9
                || this.vis.tblTabla.getRowCount() <= 0) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // evento Codigo
        if (e.getSource() == vis.txtNro) {

            if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') {
                e.consume();
            }

            if (vis.txtNro.getText().length() > 9) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
