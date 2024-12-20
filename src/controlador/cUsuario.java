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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eUsuario;
import vista.frmFormulario;

/**
 *
 * @author CesarMatias
 */
public class cUsuario implements ActionListener, DocumentListener, KeyListener {

    // variables
    eUsuario mod;
    frmFormulario vis;

    Boolean opAgregar;
    // definicion etiquetas
    private final JLabel lblCodigo;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblAlias;
    private final JLabel lblClave;

    // definicion campo de texto
    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtApellido;
    private final JTextField txtAlias;
    private final JPasswordField txtClave;

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cUsuario(frmFormulario vis) {
        this.txtNombre = new JTextField();
        this.txtApellido = new JTextField();
        this.txtAlias = new JTextField();
        this.txtCodigo = new JTextField();
        this.txtClave = new JPasswordField();
        this.lblNombre = new JLabel();
        this.lblApellido = new JLabel();
        this.lblAlias = new JLabel();
        this.lblClave = new JLabel();
        this.lblCodigo = new JLabel();
        this.mod = new eUsuario();
        this.vis = vis;
        this.vis.setSize(335, 290);

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.txtCodigo.addActionListener(this); // campo de codigo

        // agregar las acciones DocumentListener
        this.txtCodigo.getDocument().addDocumentListener(this);
        this.txtNombre.getDocument().addDocumentListener(this);
        this.txtApellido.getDocument().addDocumentListener(this);
        this.txtAlias.getDocument().addDocumentListener(this);
        this.txtClave.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.txtCodigo.addKeyListener(this);
        this.txtNombre.addKeyListener(this);
        this.txtApellido.addKeyListener(this);
        this.txtAlias.addKeyListener(this);
        this.txtClave.addKeyListener(this);

        iniciar();
    }

    private void iniciar() {
        this.vis.setTitle("Usuario");
        this.vis.setLocationRelativeTo(null);
        this.vis.setResizable(false);
        this.vis.lblMensaje.setText(null);

        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 5, 0, 5, 0, 5, 0};
        layout.rowHeights = new int[]{0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
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

        lblNombre.setText("Nombres");
        lblNombre.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblNombre, gridBagConstraints);

        txtNombre.setColumns(21);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtNombre, gridBagConstraints);

        lblApellido.setText("Apellidos");
        lblApellido.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblApellido, gridBagConstraints);

        txtApellido.setColumns(21);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtApellido, gridBagConstraints);

        lblAlias.setText("Alias");
        lblAlias.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblAlias, gridBagConstraints);

        txtAlias.setColumns(10);
//        txtAlias.setHorizontalAlignment(JTextField.RIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtAlias, gridBagConstraints);

        lblClave.setText("Clave");
        lblClave.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblClave, gridBagConstraints);

        txtClave.setColumns(10);
//        txtClave.setHorizontalAlignment(JTextField.RIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtClave, gridBagConstraints);

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.txtCodigo});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.txtCodigo) {
            if (opAgregar == null && !this.txtCodigo.getText().isEmpty()) {
                // seteo de los campos
                this.mod.setUsucodigo(Integer.parseInt(this.txtCodigo.getText()));

                // recuperando datos
                Iterator<eUsuario> itUsuario = this.mod.recuperaDatos().iterator();

                if (itUsuario.hasNext()) {
                    this.mod = itUsuario.next();
                    this.txtNombre.setText(this.mod.getNombre());
                    this.txtApellido.setText(this.mod.getApellido());
                    this.txtAlias.setText(this.mod.getAlias());
                    this.txtClave.setText(this.mod.getClave());

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
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            txtCodigo.setText(String.valueOf(this.mod.nuevoCodigo()));
            txtCodigo.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{});
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
                this.mod.setUsucodigo(Integer.parseInt(this.txtCodigo.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarUsuario();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.txtCodigo});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
            }
        }

        if (e.getSource() == this.vis.btnGuardar) {

            String mensaje;
            // seteo de los campos
            this.mod.setUsucodigo(Integer.parseInt(this.txtCodigo.getText()));
            this.mod.setNombre(this.txtNombre.getText().trim());
            this.mod.setApellido(this.txtApellido.getText().trim());
            this.mod.setAlias(this.txtAlias.getText().trim());
            this.mod.setClave(this.txtClave.getText().trim());

            this.mod.setAux(Integer.parseInt(this.txtCodigo.getText().trim()));

            // llamada a la clase para insertar o actualizar
            if (opAgregar) { // si agregar fue seleccionado TRUE
                mensaje = this.mod.insertarUsuario();
            } else { // si agregar fue seleccionado FALSE
                mensaje = this.mod.actualizarUsuario();
            }

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            opAgregar = null;
            txtCodigo.grabFocus();
            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.txtCodigo});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            opAgregar = null;
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.txtCodigo});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
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
        } else {
            this.vis.lblMensaje.setText(null);
            this.vis.btnGuardar.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
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

        if (e.getSource() == txtNombre) {
            if (txtNombre.getText().length() > 54) {
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // evento Nombre
        if (e.getSource() == txtNombre) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                txtNombre.setText(txtNombre.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
