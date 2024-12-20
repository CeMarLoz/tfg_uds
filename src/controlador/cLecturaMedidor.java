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
import modelo.eConexion;
import modelo.eLecturaMedidor;
import vista.frmBuscar;
import vista.frmLecturaMedidor;

/**
 *
 * @author CesarMatias
 */
public class cLecturaMedidor implements ActionListener, DocumentListener, KeyListener {

    // variables
    eLecturaMedidor mod;
    eConexion mCon;
    frmLecturaMedidor vis;
    frmBuscar dlg;
    Date date;

    //Instancia del formato de fecha
    SimpleDateFormat formatFecha;
    SimpleDateFormat formatInversor;

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cLecturaMedidor(frmLecturaMedidor vis) {
        this.formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        this.formatInversor = new SimpleDateFormat("yyyy-MM-dd");
        this.mod = new eLecturaMedidor();
        this.mCon = new eConexion(); // conexion
        this.vis = vis;
        this.vis.setTitle("Lectura de Medidor");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);
        //this.vis.setSize(445, 310);

        this.dlg = new frmBuscar(vis, true);
        this.dlg.setLocationRelativeTo(vis); // modal

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.vis.btnBuscarConexion.addActionListener(this);
        this.vis.btnBuscarLecturaMedidor.addActionListener(this);

        // agregar las acciones DocumentListener
        this.vis.txtCodigo.getDocument().addDocumentListener(this);
        this.vis.txtMedidor.getDocument().addDocumentListener(this);
        this.vis.txtLectura.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.vis.txtCodigo.addKeyListener(this);

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarLecturaMedidor});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.txtCodigo, this.vis.btnBuscarConexion, this.vis.txtLectura});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            date = new Date();
            this.vis.txtFecha.setText(formatFecha.format(date));
            this.vis.lblMensaje.setText(null);
            vis.txtCodigo.setText(String.valueOf(this.mod.nuevoCodigo()));
            vis.txtCodigo.grabFocus();
        }

        if (e.getSource() == this.vis.btnEliminar) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea Eliminar?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                // seteo de los campos
                this.mod.setLmCodigo(Integer.parseInt(this.vis.txtCodigo.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarLecturaMedidor();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarLecturaMedidor});
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
            this.mod.setLmCodigo(Integer.parseInt(this.vis.txtCodigo.getText()));
            this.mod.setLmFecha(formatInversor.format(date));
            this.mod.setLmLectura(Double.valueOf(this.vis.txtLectura.getText().replace(".", "").replace(",", ".")));
            this.mod.setConNro(Integer.parseInt(this.vis.txtConexion.getText()));

            // llamada a la clase para insertar o actualizar
            mensaje = this.mod.insertarLecturaMedidor();

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            this.vis.txtCodigo.grabFocus();
            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarLecturaMedidor});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.vis.btnBuscarLecturaMedidor});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.vis.btnBuscarLecturaMedidor) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            cBuscarLecturaMedidor cBuscarLecturaMedidor = new cBuscarLecturaMedidor(mod, dlg);
            dlg.setVisible(true);

            Iterator<eLecturaMedidor> itLecturaMed = cBuscarLecturaMedidor.devolverLecturaMedidor().iterator();

            if (itLecturaMed.hasNext()) {
                this.mod = itLecturaMed.next();
                this.vis.txtCodigo.setText(String.valueOf(this.mod.getLmCodigo()));
                this.vis.txtFecha.setText(String.valueOf(this.mod.getLmFecha()));
                this.vis.txtLectura.setText(String.valueOf(this.mod.getLmLectura()));
                this.vis.txtConexion.setText(String.valueOf(this.mod.getConNro()));
                this.vis.txtMedidor.setText(String.valueOf(this.mod.getMedidor().getMedNro()));
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar, this.vis.btnEliminar});
                this.vis.lblMensaje.setText(null);
            }
        }
        if (e.getSource() == this.vis.btnBuscarConexion) {
            this.dlg = new frmBuscar(vis, true);
            this.dlg.setLocationRelativeTo(vis);
            String nombre;
            cBuscarConexion cBuscarConexion = new cBuscarConexion(mCon, dlg);
            dlg.setVisible(true);

            Iterator<eConexion> itLecturaMed = cBuscarConexion.devolverConexion().iterator();

            if (itLecturaMed.hasNext()) {
                this.mCon = itLecturaMed.next();
                this.vis.txtConexion.setText(String.valueOf(this.mCon.getConNro()));

                // concatenacion para el nombre
                nombre = this.mCon.getCliente().getCliNombre() + " " + this.mCon.getCliente().getCliApellido();
                this.vis.txtMedidor.setText(nombre);
            }

            if (!this.vis.txtMedidor.getText().trim().isEmpty()) {
                this.vis.btnBuscarConexion.transferFocus();
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !this.vis.txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtCodigo.getText().trim().length() > 9 || vis.txtMedidor.getText().trim().length() > 54) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !vis.txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtCodigo.getText().trim().length() > 9 || vis.txtMedidor.getText().trim().length() > 54) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripcion <= 55 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !vis.txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (vis.txtCodigo.getText().trim().length() > 9 || vis.txtMedidor.getText().trim().length() > 54) {
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
        if (e.getSource() == vis.txtCodigo) {

            if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') {
                e.consume();
            }

            if (vis.txtCodigo.getText().length() > 9) {
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
