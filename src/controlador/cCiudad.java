/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eCiudad;
import vista.frmBuscar;
import vista.frmFormulario;

/**
 *
 * @author CesarMatias
 */
public class cCiudad implements ActionListener, DocumentListener, KeyListener {

    // variables
    eCiudad mod;
    frmFormulario vis;
    frmBuscar dlg;
    Boolean opAgregar;
    // definicion etiquetas
    private final JLabel lblCodigo;
    private final JLabel lblDescripción;

    // definicion campo de texto
    private final JTextField txtCodigo;
    private final JTextField txtDescripción;

    // definicion boton
    private final JButton btnBuscarCiudad;

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cCiudad(frmFormulario vis) {
        this.btnBuscarCiudad = new JButton();
        this.txtDescripción = new JTextField();
        this.txtCodigo = new JTextField();
        this.lblDescripción = new JLabel();
        this.lblCodigo = new JLabel();
        this.mod = new eCiudad();
        this.dlg = new frmBuscar(vis, true);
        this.vis = vis;
        this.vis.setSize(345, 188);

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.btnBuscarCiudad.addActionListener(this);

        // agregar las acciones DocumentListener
        this.txtCodigo.getDocument().addDocumentListener(this);
        this.txtDescripción.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.txtCodigo.addKeyListener(this);
        this.txtDescripción.addKeyListener(this);

        iniciar();
    }

    private void iniciar() {
        this.vis.setTitle("Ciudad");
        this.vis.setLocationRelativeTo(null); //frame
        this.vis.setResizable(false);
        this.dlg.setLocationRelativeTo(vis); // modal
        this.vis.lblMensaje.setText(null);

        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 5, 0, 5, 0, 5, 0};
        layout.rowHeights = new int[]{0, 5, 0};
        this.vis.pDatos.setLayout(layout);

        lblCodigo.setText("Código");
        lblCodigo.setFont(new Font("Dialog", Font.BOLD, 12));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblCodigo, gridBagConstraints);

        txtCodigo.setColumns(5);
        txtCodigo.setHorizontalAlignment(JTextField.RIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtCodigo, gridBagConstraints);

        lblDescripción.setText("Descripción");
        lblDescripción.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblDescripción, gridBagConstraints);

        txtDescripción.setColumns(21);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtDescripción, gridBagConstraints);

        btnBuscarCiudad.setMnemonic('f');
        btnBuscarCiudad.setIcon(new ImageIcon(getClass().getResource("/img/search.png")));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        this.vis.pDatos.add(btnBuscarCiudad, gridBagConstraints);

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCiudad});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            opAgregar = true;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.btnBuscarCiudad});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            txtCodigo.setText(String.valueOf(this.mod.nuevoCodigo()));
            txtCodigo.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.btnBuscarCiudad});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnGuardar, this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            // seleccionar todo el texto
            cUtiles.selectAll(this.vis.pDatos);
            txtCodigo.grabFocus();
        }

        if (e.getSource() == this.vis.btnEliminar) {
            if (JOptionPane.showConfirmDialog(this.vis, "¿Seguro que desea Eliminar?", "CONFIRMAR!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

                // seteo de los campos
                this.mod.setCiuCodigo(Integer.parseInt(this.txtCodigo.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarCiudad();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCiudad});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
            }
        }

        if (e.getSource() == this.vis.btnGuardar) {

            String mensaje;
            // seteo de los campos
            this.mod.setCiuCodigo(Integer.parseInt(this.txtCodigo.getText()));
            this.mod.setCiuDescripcion(this.txtDescripción.getText().trim());
            this.mod.setAux(Integer.parseInt(this.txtCodigo.getText().trim()));

            // llamada a la clase para insertar o actualizar
            if (opAgregar) { // si agregar fue seleccionado TRUE
                mensaje = this.mod.insertarCiudad();
            } else { // si agregar fue seleccionado FALSE
                mensaje = this.mod.actualizarCiudad();
            }

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCiudad});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCiudad});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.btnBuscarCiudad) {
            cBuscarCiudad cBuscarCiudad = new cBuscarCiudad(mod, dlg);
            dlg.setVisible(true);

            Iterator<eCiudad> itCiudad = cBuscarCiudad.devolverCiudad().iterator();

            if (itCiudad.hasNext()) {
                this.mod = itCiudad.next();
                this.txtCodigo.setText(String.valueOf(this.mod.getCiuCodigo()));
                this.txtDescripción.setText(this.mod.getCiuDescripcion());
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnModificar, this.vis.btnEliminar, this.vis.btnCancelar});
                this.vis.lblMensaje.setText(null);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (txtCodigo.getText().trim().length() > 9 || txtDescripción.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripción <= 65 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (txtCodigo.getText().trim().length() > 9 || txtDescripción.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripción <= 65 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (txtCodigo.getText().trim().length() > 9 || txtDescripción.getText().trim().length() > 64) {
            this.vis.lblMensaje.setText("Codigo <= 10 y Descripción <= 65 caracteres");
            this.vis.btnGuardar.setEnabled(false);
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        // evento Codigo
        if (e.getSource() == txtCodigo) {

            if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') {
                e.consume();
            }

            if (txtCodigo.getText().length() > 9) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }

        if (e.getSource() == txtDescripción) {
            if (txtDescripción.getText().length() > 64) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // evento Descripción
        if (e.getSource() == txtDescripción) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                txtDescripción.setText(txtDescripción.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
