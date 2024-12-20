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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.eCalle;
import modelo.eCategoriaConexion;
import modelo.eConexion;
import modelo.eMedidor;
import modelo.eSolicitud;
import vista.frmBuscar;
import vista.frmConexion;

/**
 *
 * @author CesarMatias
 */
public class cConexion implements ActionListener, DocumentListener, KeyListener {

    // variables
    eConexion mod;

    eCategoriaConexion mCat;
    eSolicitud mSol;
    eMedidor mMed;
    eCalle mCal;
    frmConexion vis;
    frmBuscar dlg;
    Date date;
    Boolean opAgregar;

    // tabla detale
    String[] titulos = {"Código", "Descripción"};
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
    public cConexion(frmConexion vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eConexion();
        this.mCat = new eCategoriaConexion(); // cagoria conexion
        this.mMed = new eMedidor(); // medidor
        this.mSol = new eSolicitud(); // modelo del solicitud
        this.mCal = new eCalle(); // calle
        this.vis = vis;
        this.vis.setTitle("Conexion");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);
        this.vis.setSize(490, 510);

        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarSolicitud.addActionListener(this);
        this.vis.btnBuscarConexion.addActionListener(this);
        this.vis.btnBuscarCalle.addActionListener(this);
        this.vis.btnMas.addActionListener(this);
        this.vis.btnMenos.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtNombre.getDocument().addDocumentListener(this);
        this.vis.txtDescripcion.getDocument().addDocumentListener(this);
        this.vis.txtLecturaAct.getDocument().addDocumentListener(this);
        this.vis.txtCodigo.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtNro.addKeyListener(this);
        this.vis.txtNombre.addKeyListener(this);

        iniciar();

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);
        cUtiles.pasarFocus(this.vis.pDireccion);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarConexion});
        cUtiles.habilitarCampos(this.vis.pDireccion, false, new Object[]{});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    private void iniciar() {
        this.vis.tblTabla.setModel(tModelo);

        TableColumnModel colum = this.vis.tblTabla.getColumnModel();
        colum.getColumn(0).setPreferredWidth(100);
        colum.getColumn(1).setPreferredWidth(500);

        // cargando combo
        cargaComboEstSolicitud();
    }

    private void cargaComboEstSolicitud() {
        mCat.cargaComboCategoria(this.vis.cboCategoria);
        mMed.cargaComboMedidor(this.vis.cboMedidor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            opAgregar = true;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.vis.txtSolicitud, this.vis.txtNombre, this.vis.txtFecha, this.vis.btnBuscarConexion});
            cUtiles.habilitarCampos(this.vis.pDireccion, true, new Object[]{this.vis.txtCodigo, this.vis.txtDescripcion});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            vis.txtNro.setText(String.valueOf(this.mod.nuevoNro()));
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.cboCategoria, this.vis.cboMedidor, this.vis.txtLecturaAct});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnGuardar, this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            // seleccionar todo el texto
            cUtiles.selectAll(this.vis.pDatos);
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnGuardar) {
            String mensaje = null;

            try {
                date = formatFecha.parse(this.vis.txtFecha.getText().trim());
            } catch (ParseException ex) {
            }

            // seteo de los campos
            this.mod.setConNro(Integer.parseInt(this.vis.txtNro.getText()));
            this.mod.setConFecha(formatInversor.format(date));
            this.mod.setConLecturaAct(Integer.parseInt(this.vis.txtLecturaAct.getText()));
            this.mod.setSNro(Integer.parseInt(this.vis.txtSolicitud.getText()));
            this.mod.setMedCodigo(this.vis.cboMedidor.getItemAt(this.vis.cboMedidor.getSelectedIndex()).getMedCodigo());
            this.mod.setCcCodigo(this.vis.cboCategoria.getItemAt(this.vis.cboCategoria.getSelectedIndex()).getCcCodigo());
            this.mod.setAux(Integer.parseInt(this.vis.txtNro.getText().trim()));

            if (opAgregar) { // si agregar fue seleccionado TRUE
                // llamada a la clase para insertar
                mensaje = this.mod.insertarConexion();
            } else {
                mensaje = this.mod.actualizarConexion();
            }

            if (mensaje == null) {
                if (opAgregar) { // si agregar fue seleccionado TRUE
                    // Detalle
                    for (int i = 0; i < this.vis.tblTabla.getRowCount(); i++) {
                        this.mod.setCalCodigo(Integer.parseInt(this.vis.tblTabla.getValueAt(i, 0).toString()));

                        // llamada a la clase para insertar
                        mensaje = this.mod.insertarConexionDir();
                        if (mensaje != null) {
                            break;
                        }
                    }
                    this.vis.lblMensaje.setText("Registro Guardado");
                } else {
                    this.vis.lblMensaje.setText("Registro Guardado");
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
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarConexion});
            cUtiles.habilitarCampos(this.vis.pDireccion, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            if (this.vis.tblTabla.getRowCount() >= 1) {
                tModelo.setRowCount(0);
            }
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarConexion});
            cUtiles.habilitarCampos(this.vis.pDireccion, false, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarSolicitud) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            String nombre;
            cBuscarSolicitud cBuscarSolicitud = new cBuscarSolicitud(mSol, dlg);
            dlg.setVisible(true);

            Iterator<eSolicitud> itSolicitud = cBuscarSolicitud.devolverSolicitud().iterator();

            if (itSolicitud.hasNext()) {
                this.mSol = itSolicitud.next();
                this.vis.txtSolicitud.setText(String.valueOf(this.mSol.getSolNro()));

                // concatenacion para el nombre
                nombre = this.mSol.getCliente().getCliNombre() + " " + this.mSol.getCliente().getCliApellido();
                this.vis.txtNombre.setText(nombre);
            }

            if (!this.vis.txtNombre.getText().trim().isEmpty()) {
                this.vis.btnBuscarSolicitud.transferFocus();
            }
        }

        if (e.getSource() == this.vis.btnBuscarConexion) {
            String nombre;
            int codigo, nro;
            this.vis.lblMensaje.setText(null);
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarConexion cBuscarConexion = new cBuscarConexion(mod, dlg);
            dlg.setVisible(true);

            Iterator<eConexion> itConexion = cBuscarConexion.devolverConexion().iterator();

            if (itConexion.hasNext()) {
                this.mod = itConexion.next();

                this.vis.txtNro.setText(String.valueOf(this.mod.getConNro()));
                this.mod.recuperaDatosDir(tModelo, this.vis.txtNro.getText()); // recuperamos las direcciones
                this.vis.txtSolicitud.setText(String.valueOf(this.mod.getSNro()));
                this.vis.txtFecha.setText(this.mod.getConFecha());
                this.vis.txtLecturaAct.setText(String.valueOf(this.mod.getConLecturaAct()));

                // concatenacion para el nombre
                nombre = this.mod.getCliente().getCliNombre() + " " + this.mod.getCliente().getCliApellido();
                this.vis.txtNombre.setText(nombre);

                // cateoria conexion
                codigo = this.mod.getCategoria().getCcCodigo();
                nombre = this.mod.getCategoria().getCcNombre();
                this.vis.cboCategoria.setSelectedItem(new eCategoriaConexion(codigo, nombre));

                // medidor
                codigo = this.mod.getMedidor().getMedCodigo();
                nro = this.mod.getMedidor().getMedNro();
                this.vis.cboMedidor.setSelectedItem(new eMedidor(codigo, nro));

            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, true, new Object[]{this.vis.btnAgregar, this.vis.btnGuardar});
            }
        }

        if (e.getSource() == this.vis.btnBuscarCalle) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarCalle cBuscarCalle = new cBuscarCalle(mCal, dlg);
            dlg.setVisible(true);

            Iterator<eCalle> itCalle = cBuscarCalle.devolverCalle().iterator();

            if (itCalle.hasNext()) {
                this.mCal = itCalle.next();
                this.vis.txtCodigo.setText(String.valueOf(this.mCal.getCalCodigo()));
                this.vis.txtDescripcion.setText(this.mCal.getCalNombre());
            }
            if (!this.vis.txtDescripcion.getText().trim().isEmpty()) {
                this.vis.btnBuscarCalle.transferFocus();
            }
        }

        if (e.getSource() == this.vis.btnMas) {
            if (!this.vis.txtCodigo.getText().isEmpty()) {

                tModelo = (DefaultTableModel) this.vis.tblTabla.getModel();
                tModelo.addRow(new Object[]{this.vis.txtCodigo.getText(), this.vis.txtDescripcion.getText()});

                cUtiles.limpiarCampos(this.vis.pDireccion);
            }
        }
        if (e.getSource() == this.vis.btnMenos) {
            if (this.vis.tblTabla.getSelectedRow() != -1) {
                tModelo.removeRow(this.vis.tblTabla.getSelectedRow());
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9
                || vis.txtNombre.getText().trim().length() > 54
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
                || vis.txtNombre.getText().trim().length() > 54
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
                || vis.txtNombre.getText().trim().length() > 54
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

        if (e.getSource() == vis.txtNombre) {
            if (vis.txtNombre.getText().length() > 64) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // evento Nombre
        if (e.getSource() == vis.txtNombre) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                vis.txtNombre.setText(vis.txtNombre.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
