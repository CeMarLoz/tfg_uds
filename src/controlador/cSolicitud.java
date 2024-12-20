package controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eCliente;
import modelo.eEstadoSolicitud;
import modelo.eSolicitud;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import vista.frmBuscar;
import vista.frmSolicitud;

/**
 *
 * @author CesarMatias
 */
public class cSolicitud implements ActionListener, DocumentListener, KeyListener {

    // variables
    eSolicitud mod;
    eCliente mCli;
    eEstadoSolicitud mEst;
    frmSolicitud vis;
    frmBuscar dlg;
    Boolean opAgregar;
    int clicodigo;
    Date date;

    //Instancia del formato de fecha
    SimpleDateFormat formatFecha;
    SimpleDateFormat formatInversor;

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cSolicitud(frmSolicitud vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eSolicitud();
        this.vis = vis;
        this.vis.setTitle("Solicitud");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);
        //this.vis.setSize(445, 310);

        this.mCli = new eCliente(); // modelo del cliente
        this.mEst = new eEstadoSolicitud(); // modelo de estado
        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarCliente.addActionListener(this);
        this.vis.btnBuscarSolicitud.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtCliente.getDocument().addDocumentListener(this);
        this.vis.txtFecha.getDocument().addDocumentListener(this);
        this.vis.txtDescripcion.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtNro.addKeyListener(this);
        this.vis.txtCliente.addKeyListener(this);

        // cargando combo
        cargaComboEstSolicitud();

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarSolicitud});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    private void cargaComboEstSolicitud() {
        mEst.carCombo(this.vis.cboEstado);
    }

    private void imprimir() {

        try {
            HashMap param = new HashMap();
            param.put("P_NRO", this.vis.txtNro.getText());

            JasperPrint jp = JasperFillManager.fillReport("src/reporte/rptSolicitudes.jasper", param, this.mod.cone());
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            opAgregar = true;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarCliente, this.vis.txtNro, this.vis.scroll});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            vis.txtNro.setText(String.valueOf(this.mod.nuevoNro()));
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.cboEstado, this.vis.scroll});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnGuardar, this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            // seleccionar todo el texto
            cUtiles.selectAll(this.vis.pDatos);
            vis.cboEstado.grabFocus();
        }

        if (e.getSource() == this.vis.btnGuardar) {
            String mensaje;

            try {
                date = formatFecha.parse(this.vis.txtFecha.getText().trim());
            } catch (ParseException ex) {
            }

            // seteo de los campos
            this.mod.setSolNro(Integer.parseInt(this.vis.txtNro.getText()));
            this.mod.setCliCodigo(clicodigo);
            this.mod.setSolFecha(formatInversor.format(date));
            this.mod.setSolDescripcion(this.vis.txtDescripcion.getText().trim());
            this.mod.setEsCodigo(this.vis.cboEstado.getItemAt(this.vis.cboEstado.getSelectedIndex()).getCodigo());
            this.mod.setAux(Integer.parseInt(this.vis.txtNro.getText().trim()));

            // llamada a la clase para insertar o actualizar
            if (opAgregar) { // si agregar fue seleccionado TRUE
                mensaje = this.mod.insertarSolicitud();
            } else { // si agregar fue seleccionado FALSE
                mensaje = this.mod.actualizarSolicitud();
            }

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
                if (opAgregar) { // si agregar fue seleccionado TRUE
                    imprimir();
                }
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            opAgregar = null;
            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarSolicitud});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            opAgregar = null;
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarSolicitud});
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
                this.clicodigo = this.mCli.getCliCodigo();
                this.vis.txtCliente.setText(this.mCli.getCliRuc());
                this.vis.txtNombre.setText(this.mCli.getCliNombre() + " " + this.mCli.getCliApellido());
            }

            if (!this.vis.txtCliente.getText().trim().isEmpty()) {
                this.vis.btnBuscarCliente.transferFocus();
            }
        }

        if (e.getSource() == this.vis.btnBuscarSolicitud) {
            String nombre, descripcion;
            int codigo;
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarSolicitud cBuscarSolicitud = new cBuscarSolicitud(mod, dlg);
            dlg.setVisible(true);

            Iterator<eSolicitud> itSolicitud = cBuscarSolicitud.devolverSolicitud().iterator();

            if (itSolicitud.hasNext()) {
                this.mod = itSolicitud.next();
                this.vis.txtNro.setText(String.valueOf(this.mod.getSolNro()));
                this.clicodigo = this.mod.getCliCodigo();
                this.vis.txtCliente.setText(this.mod.getCliente().getCliRuc());

                // concatenacion para el nombre
                nombre = this.mod.getCliente().getCliNombre() + " " + this.mod.getCliente().getCliApellido();
                this.vis.txtNombre.setText(nombre);

                this.vis.txtFecha.setText(this.mod.getSolFecha());
                this.vis.txtDescripcion.setText(this.mod.getSolDescripcion());

                codigo = this.mod.getEstado().getCodigo();
                descripcion = this.mod.getEstado().getDescripcion();
                this.vis.cboEstado.setSelectedItem(new eEstadoSolicitud(codigo, descripcion));
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnModificar, this.vis.btnCancelar});
                this.vis.lblMensaje.setText(null);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Nombre <= 65 caracteres");
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
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Nombre <= 65 caracteres");
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
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Nombre <= 65 caracteres");
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

        if (e.getSource() == vis.txtCliente) {
            if (vis.txtCliente.getText().length() > 64) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // evento Nombre
        if (e.getSource() == vis.txtCliente) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                vis.txtCliente.setText(vis.txtCliente.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
