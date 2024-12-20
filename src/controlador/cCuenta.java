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
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.eCuenta;
import modelo.eLecturaMedidor;
import modelo.eServicio;
import vista.frmBuscar;
import vista.frmCuenta;

/**
 *
 * @author CesarMatias
 */
public class cCuenta implements ActionListener, DocumentListener, KeyListener {

    // variables
    eCuenta mod;
    eLecturaMedidor mLec;
    eServicio mSer;
    frmCuenta vis;
    frmBuscar dlg;
    Date date;
    double lAnt, lAct, consumo, precio, conMin, conEx, precioEx, TotalEx, TotalCon, total = 0;

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
    public cCuenta(frmCuenta vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eCuenta();
        this.vis = vis;
        this.vis.setTitle("Cuenta");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);
        //thisthis.vis.setSize(445, 310);

        this.mLec = new eLecturaMedidor(); // modelo del solicitud
        this.mSer = new eServicio(); // modelo de servicio
        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnAnular.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarCuenta.addActionListener(this);
        this.vis.btnBuscarLectura.addActionListener(this);
        this.vis.btnMas.addActionListener(this);
        this.vis.btnMenos.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtTotal.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtNro.addKeyListener(this);

        iniciar();

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCuenta});
        cUtiles.habilitarCampos(this.vis.pLectura, false, new Object[]{});
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

    private void calcularConsumo() {
        lAnt = Double.valueOf(this.vis.txtLecturaAnt.getText());
        lAct = Double.valueOf(this.vis.txtLecturaAct.getText());
        precio = Double.valueOf(this.vis.txtPrecioConsumo.getText().replace(".", "").replace(",", "."));
        conMin = Double.valueOf(this.vis.txtConsumoMin.getText());
        precioEx = Double.valueOf(this.vis.txtPrecioEx.getText().replace(".", "").replace(",", "."));

        consumo = lAct - lAnt;
        if (consumo <= conMin) {
            TotalCon = conMin * precio;
        } else {
            TotalCon = conMin * precio;
            conEx = consumo - conMin;
            TotalEx = conEx * precioEx;
        }

        this.vis.txtConsumo.setText(formatNumber.format(consumo));
        this.vis.txtConsumoEx.setText(formatNumber.format(conEx));

        this.vis.txtTotalConsumo.setText(formatNumber.format(TotalCon));
        this.vis.txtTotalExe.setText(formatNumber.format(TotalEx));
    }

    private double totalCuenta() {
        total = 0;
        for (int i = 0; i < tModelo.getRowCount(); i++) {
            total += Double.parseDouble(tModelo.getValueAt(i, 3).toString().replace(".", "").replace(",", "."));
        }
        return total;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.vis.btnBuscarCuenta, this.vis.txtFecha, this.vis.txtLectura, this.vis.txtConexion});
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
            this.mod.setCueNro(Integer.parseInt(this.vis.txtNro.getText()));
            this.mod.setCueFecha(formatInversor.format(date));
            this.mod.setLmCodigo(Integer.parseInt(this.vis.txtLectura.getText().trim()));

            // llamada a la clase para insertar
            mensaje = this.mod.insertarCuenta();

            if (mensaje == null) {
                // seteando campños
                for (int i = 0; i < this.vis.tblTabla.getRowCount(); i++) {
                    this.mod.setSerCodigo(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 4).toString()));
                    this.mod.setCuedetCantidad(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 1).toString().replace(".", "").replace(",", ".")));
                    this.mod.setCuedetPrecio(Double.parseDouble(this.vis.tblTabla.getValueAt(i, 2).toString().replace(".", "").replace(",", ".")));

                    // llamada a la clase para insertar
                    mensaje = this.mod.insertarCuentaDet();
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

            this.vis.txtNro.grabFocus();
            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);
            cUtiles.limpiarCampos(this.vis.pLectura);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCuenta});
            cUtiles.habilitarCampos(this.vis.pLectura, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnAnular) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea Anular?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                // seteo de los campos
                this.mod.setLmCodigo(Integer.parseInt(this.vis.txtNro.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.anularCuenta();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Anulado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                if (this.vis.tblTabla.getRowCount() >= 1) {
                    tModelo.setRowCount(0);
                }
                cUtiles.limpiarCampos(this.vis.pDatos);
                cUtiles.limpiarCampos(this.vis.pLectura);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCuenta});
                cUtiles.habilitarCampos(this.vis.pLectura, false, new Object[]{});
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
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCuenta});
            cUtiles.habilitarCampos(this.vis.pLectura, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarLectura) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            String conexion;
            cBuscarLecturaMedidor cBuscarLecturaMedidor = new cBuscarLecturaMedidor(mLec, dlg);
            dlg.setVisible(true);

            Iterator<eLecturaMedidor> itSolicitud = cBuscarLecturaMedidor.devolverLecturaMedidor().iterator();

            if (itSolicitud.hasNext()) {
                this.mLec = itSolicitud.next();
                this.vis.txtLectura.setText(String.valueOf(this.mLec.getLmCodigo()));

                conexion = String.valueOf("Conexion: " + this.mLec.getConNro()) + " - Medidor: " + String.valueOf(this.mLec.getMedidor().getMedNro());
                this.vis.txtConexion.setText(conexion);

                // detalle
                this.vis.txtLecturaAct.setText(String.valueOf(this.mLec.getLmLectura()));
                this.vis.txtPrecioConsumo.setText(formatNumber.format(this.mLec.getMonto()));
                this.vis.txtPrecioEx.setText(formatNumber.format(this.mLec.getExceden()));
                this.vis.txtConsumoMin.setText(String.valueOf(this.mLec.getCubico()));

                // recuperando lectura anterior
                this.mLec.setConNro(this.mLec.getConNro());
                this.mLec.setLmCodigo(this.mLec.getLmCodigo());
                this.vis.txtLecturaAnt.setText(String.valueOf(this.mLec.recuperaLecturaAnterior()));
            }

            if (!this.vis.txtConexion.getText().trim().isEmpty()) {
                calcularConsumo(); // llamando funcion para calculaos de consumos
                this.vis.btnBuscarLectura.transferFocus();
                cUtiles.habilitarCampos(this.vis.pLectura, false, new Object[]{this.vis.btnMas, this.vis.btnMenos});
            }
        }

        if (e.getSource() == this.vis.btnMas) {
            int codigo = 0;
            String descripcion = null;

            // seteo de los campos
            this.mSer.setSerCodigo(1);
            // recuperando datos 
            Iterator<eServicio> itServicio = this.mSer.recuperaDatos(false).iterator();

            if (itServicio.hasNext()) {
                this.mSer = itServicio.next();
                codigo = this.mSer.getSerCodigo();
                descripcion = this.mSer.getSerDescripcion();
            }

            // Consumo Normal
            if (conEx > 0||consumo <= conMin) {
                consumo = conMin;
            }
            total += TotalCon;
            tModelo.addRow(new Object[]{descripcion, formatNumber.format(consumo),
                this.vis.txtPrecioConsumo.getText(), this.vis.txtTotalConsumo.getText(), codigo});

            // En caso de consumo excedentes
            if (conEx > 0) {

                this.mSer.setSerCodigo(2);
                Iterator<eServicio> itExe = this.mSer.recuperaDatos(false).iterator();

                if (itExe.hasNext()) {
                    this.mSer = itExe.next();
                    codigo = this.mSer.getSerCodigo();
                    descripcion = this.mSer.getSerDescripcion();
                }

                total += TotalEx;
                tModelo.addRow(new Object[]{descripcion, this.vis.txtConsumoEx.getText(),
                    this.vis.txtPrecioEx.getText(), this.vis.txtTotalExe.getText(), codigo});
            }

            // limpair el campo
            cUtiles.limpiarCampos(this.vis.pLectura);

            // para mostrar el total
            this.vis.txtTotal.setText(formatNumber.format(total));
        }

        if (e.getSource() == this.vis.btnMenos) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea eliminar los items?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                tModelo.setRowCount(0);
                this.vis.txtTotal.setText(null);
            }
        }

        if (e.getSource() == this.vis.btnBuscarCuenta) {
            this.vis.lblMensaje.setText(null);
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);

            cBuscarCuenta cBuscarCuenta = new cBuscarCuenta(mod, dlg);
            dlg.setVisible(true);
            Iterator<eCuenta> itCuenta = cBuscarCuenta.devolverCuenta().iterator();

            if (itCuenta.hasNext()) {
                this.mod = itCuenta.next();
                this.vis.txtNro.setText(String.valueOf(this.mod.getCueNro()));

                // cargando detalle
                this.mod.recuperaDatosDet(tModelo, this.vis.txtNro.getText());

                this.vis.txtLectura.setText(String.valueOf(this.mod.getLmCodigo()));
                this.vis.txtConexion.setText(String.valueOf(this.mod.getMedidor().getMedNro()));
                this.vis.txtFecha.setText(this.mod.getCueFecha());
                this.vis.txtTotal.setText(formatNumber.format(totalCuenta()));

                if (this.mod.isCueAnulado()) {
                    this.vis.lblMensaje.setText("Anulado!");
                }
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                if (!this.mod.isCueAnulado()) {
                    cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar, this.vis.btnAnular});
                } else {
                    cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});
                }
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
