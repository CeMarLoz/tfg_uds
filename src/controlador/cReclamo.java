package controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eCliente;
import modelo.eReclamo;
import vista.frmBuscar;
import vista.frmReclamo;

/**
 *
 * @author CesarMatias
 */
public class cReclamo implements ActionListener, DocumentListener, KeyListener {

    // variables
    eReclamo mod;
    eCliente mCli;
    frmReclamo vis;
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
    public cReclamo(frmReclamo vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eReclamo();
        this.vis = vis;
        this.vis.setTitle("Reclamo");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);
        this.vis.setSize(445, 310);

        this.mCli = new eCliente(); // modelo del cliente
        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarCliente.addActionListener(this);
        this.vis.txtNro.addActionListener(this); // campo de codigo

        // agregar las acciones DocumentListener
        this.vis.txtNro.getDocument().addDocumentListener(this);
        this.vis.txtCliente.getDocument().addDocumentListener(this);
        this.vis.txtFecha.getDocument().addDocumentListener(this);
        this.vis.txtDescripcion.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtNro.addKeyListener(this);
        this.vis.txtCliente.addKeyListener(this);

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.txtNro});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.txtNro) {
            if (opAgregar == null && !this.vis.txtNro.getText().isEmpty()) {
                String nombre;
                // seteo de los campos
                this.mod.setRecNro(Integer.parseInt(this.vis.txtNro.getText()));

                // recuperando datos
                Iterator<eReclamo> itReclamo = this.mod.recuperaDatos().iterator();

                if (itReclamo.hasNext()) {
                    this.mod = itReclamo.next();
                    nombre = this.mod.getCliente().getCliNombre() + " " + this.mod.getCliente().getCliApellido();

                    this.clicodigo = this.mod.getCliCodigo();
                    this.vis.txtDescripcion.setText(this.mod.getRecDescripcion());
                    this.vis.txtCliente.setText(this.mod.getCliente().getCliRuc());
                    this.vis.txtNombre.setText(nombre);

                    try {
                        date = formatInversor.parse(this.mod.getRecFecha());
                        this.vis.txtFecha.setText(formatFecha.format(date));
                    } catch (ParseException ex) {
                    }

                    cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnModificar, this.vis.btnEliminar, this.vis.btnCancelar});
                } else {
                    this.vis.lblMensaje.setText("No existe registro!");
                    cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});
                }
            }
        }

        if (e.getSource() == this.vis.btnAgregar) {
            opAgregar = true;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.vis.txtCliente, this.vis.txtNombre, this.vis.txtFecha});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            vis.txtNro.setText(String.valueOf(this.mod.nuevoCodigo()));
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.vis.txtCliente, this.vis.txtNombre, this.vis.txtFecha});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnGuardar, this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            // seleccionar todo el texto
            cUtiles.selectAll(this.vis.pDatos);
            vis.txtNro.grabFocus();
        }

        if (e.getSource() == this.vis.btnEliminar) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea Eliminar?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                // seteo de los campos
                this.mod.setRecNro(Integer.parseInt(this.vis.txtNro.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarReclamo();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.txtNro});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
            }
        }

        if (e.getSource() == this.vis.btnGuardar) {
            String mensaje;

            try {
                date = formatFecha.parse(this.vis.txtFecha.getText().trim());
            } catch (ParseException ex) {
            }

            // seteo de los campos
            this.mod.setRecNro(Integer.parseInt(this.vis.txtNro.getText()));
            this.mod.setCliCodigo(clicodigo);
            this.mod.setRecFecha(formatInversor.format(date));
            this.mod.setRecDescripcion(this.vis.txtDescripcion.getText().trim());
            this.mod.setAux(Integer.parseInt(this.vis.txtNro.getText().trim()));

            // llamada a la clase para insertar o actualizar
            if (opAgregar) { // si agregar fue seleccionado TRUE
                mensaje = this.mod.insertarReclamo();
            } else { // si agregar fue seleccionado FALSE
                mensaje = this.mod.actualizarReclamo();
            }

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            opAgregar = null;
            this.vis.txtNro.grabFocus();
            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.txtNro});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            opAgregar = null;
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.txtNro});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarCliente) {
            cBuscarCliente cBuscarCliente = new cBuscarCliente(mCli, dlg);
            dlg.setVisible(true);

            Iterator<eCliente> itCliente = cBuscarCliente.devolverCliente().iterator();

            if (itCliente.hasNext()) {
                this.mCli = itCliente.next();
                this.clicodigo = this.mCli.getCliCodigo();
                this.vis.txtCliente.setText(this.mCli.getCliRuc());
                this.vis.txtNombre.setText(this.mCli.getCliNombre());
            }

            if (!this.vis.txtCliente.getText().trim().isEmpty()) {
                this.vis.btnBuscarCliente.transferFocus();
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 54) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 54) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !vis.txtNro.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtNro.getText().trim().length() > 9 || vis.txtCliente.getText().trim().length() > 54) {
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
