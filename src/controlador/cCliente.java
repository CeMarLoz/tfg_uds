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
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eCliente;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import vista.frmBuscar;
import vista.frmFormulario;

/**
 *
 * @author CesarMatias
 */
public class cCliente implements ActionListener, DocumentListener, KeyListener {

    // variables
    eCliente mod;
    frmFormulario vis;
    frmBuscar dlg;
    Boolean opAgregar;
    // definicion etiquetas
    private final JLabel lblCodigo;
    private final JLabel lblNombre;
    private final JLabel lblApellido;
    private final JLabel lblRuc;
    private final JLabel lblTelefono;

    // definicion campo de texto
    private final JTextField txtCodigo;
    private final JTextField txtNombre;
    private final JTextField txtApellido;
    private final JTextField txtRuc;
    private final JTextField txtTelefono;

    // definicion boton
    private final JButton btnBuscarCliente;
//    private final JButton btnImprimir;

    /**
     * Recibe la vista o formulario
     *
     * @param vis
     */
    public cCliente(frmFormulario vis) {
        this.btnBuscarCliente = new JButton();
//        this.btnImprimir = new JButton();
        this.txtNombre = new JTextField();
        this.txtCodigo = new JTextField();
        this.txtApellido = new JTextField();
        this.txtRuc = new JTextField();
        this.txtTelefono = new JTextField();
        this.lblNombre = new JLabel();
        this.lblCodigo = new JLabel();
        this.lblApellido = new JLabel();
        this.lblRuc = new JLabel();
        this.lblTelefono = new JLabel();
        this.mod = new eCliente();
        this.dlg = new frmBuscar(vis, true);
        this.vis = vis;
        this.vis.setSize(335, 290);

        // agregar las acciones ActionListener
        this.vis.btnAgregar.addActionListener(this);
        this.vis.btnModificar.addActionListener(this);
        this.vis.btnEliminar.addActionListener(this);
        this.vis.btnGuardar.addActionListener(this);
        this.vis.btnCancelar.addActionListener(this);
        this.vis.btnSalir.addActionListener(this);
        this.btnBuscarCliente.addActionListener(this);
//        this.btnImprimir.addActionListener(this);

        // agregar las acciones DocumentListener
        this.txtCodigo.getDocument().addDocumentListener(this);
        this.txtNombre.getDocument().addDocumentListener(this);
        this.txtApellido.getDocument().addDocumentListener(this);
        this.txtRuc.getDocument().addDocumentListener(this);
        this.txtTelefono.getDocument().addDocumentListener(this);

        // agregar las acciones KeyListener
        this.txtCodigo.addKeyListener(this);
        this.txtNombre.addKeyListener(this);

        iniciar();
    }

    private void iniciar() {
        this.vis.setTitle("Clientes");
        this.vis.setLocationRelativeTo(null); //frame
        this.vis.setResizable(false);
        this.dlg.setLocationRelativeTo(vis); // modal
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

        lblRuc.setText("RUC / CINº");
        lblRuc.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblRuc, gridBagConstraints);

        txtRuc.setColumns(10);
        txtRuc.setHorizontalAlignment(JTextField.RIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtRuc, gridBagConstraints);

        lblTelefono.setText("Teléfono");
        lblTelefono.setFont(new Font("Dialog", Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(lblTelefono, gridBagConstraints);

        txtTelefono.setColumns(10);
        txtTelefono.setHorizontalAlignment(JTextField.RIGHT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        this.vis.pDatos.add(txtTelefono, gridBagConstraints);

        btnBuscarCliente.setMnemonic('f');
        btnBuscarCliente.setIcon(new ImageIcon(getClass().getResource("/img/search.png")));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        this.vis.pDatos.add(btnBuscarCliente, gridBagConstraints);

        // para pasar el foco al campo siguiente
        cUtiles.pasarFocus(this.vis.pDatos);

        // habilitacion de campos y botones a partir de las excepciones
        cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCliente});
        cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.vis.btnAgregar) {
            opAgregar = true;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.btnBuscarCliente});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnCancelar});

            this.vis.lblMensaje.setText(null);
            txtCodigo.setText(String.valueOf(this.mod.nuevoCodigo()));
            txtCodigo.grabFocus();
        }

        if (e.getSource() == this.vis.btnModificar) {
            opAgregar = false;
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, true, new Object[]{this.btnBuscarCliente});
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
                this.mod.setCliCodigo(Integer.parseInt(this.txtCodigo.getText()));

                // llamada a la clase para insertar
                String mensaje = this.mod.eliminarCliente();
                if (mensaje == null) {
                    this.vis.lblMensaje.setText("Registro Eliminado");
                } else {
                    this.vis.lblMensaje.setText(mensaje);
                    return;
                }

                cUtiles.limpiarCampos(this.vis.pDatos);
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCliente});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
            }
        }

        if (e.getSource() == this.vis.btnGuardar) {

            String mensaje;
            // seteo de los campos
            this.mod.setCliCodigo(Integer.parseInt(this.txtCodigo.getText()));
            this.mod.setCliNombre(this.txtNombre.getText().trim());
            this.mod.setCliApellido(this.txtApellido.getText().trim());
            this.mod.setCliRuc(this.txtRuc.getText().trim());
            this.mod.setCliTelefono(this.txtTelefono.getText().trim());
            this.mod.setAux(Integer.parseInt(this.txtCodigo.getText().trim()));

            // llamada a la clase para insertar o actualizar
            if (opAgregar) { // si agregar fue seleccionado TRUE
                mensaje = this.mod.insertarCliente();
            } else { // si agregar fue seleccionado FALSE
                mensaje = this.mod.actualizarCliente();
            }

            if (mensaje == null) {
                this.vis.lblMensaje.setText("Registro Guardado");
            } else {
                this.vis.lblMensaje.setText(mensaje);
                return;
            }

            cUtiles.limpiarCampos(this.vis.pDatos);
            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCliente});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnCancelar) {
            cUtiles.limpiarCampos(this.vis.pDatos);
            this.vis.lblMensaje.setText(null);

            // habilitacion de campos y botones a partir de las excepciones
            cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{this.btnBuscarCliente});
            cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnAgregar, this.vis.btnSalir});
        }

        if (e.getSource() == this.vis.btnSalir) {
            this.vis.dispose();
        }

        if (e.getSource() == this.btnBuscarCliente) {
            cBuscarCliente cBuscarCliente = new cBuscarCliente(mod, dlg);
            dlg.setVisible(true);

            Iterator<eCliente> itCliente = cBuscarCliente.devolverCliente().iterator();

            if (itCliente.hasNext()) {
                this.mod = itCliente.next();
                this.txtCodigo.setText(String.valueOf(this.mod.getCliCodigo()));
                this.txtNombre.setText(this.mod.getCliNombre());
                this.txtApellido.setText(this.mod.getCliApellido());
                this.txtRuc.setText(this.mod.getCliRuc());
                this.txtTelefono.setText(this.mod.getCliTelefono());
            }

            // si no esta vacio cambia los botones habilitados
            if (!cUtiles.verificarNulos(this.vis.pDatos)) {
                // habilitacion de campos y botones a partir de las excepciones
                cUtiles.habilitarCampos(this.vis.pDatos, false, new Object[]{});
                cUtiles.habilitarCampos(this.vis.pBotones, false, new Object[]{this.vis.btnModificar, this.vis.btnEliminar, this.vis.btnCancelar});
                this.vis.lblMensaje.setText(null);
            }
        }

//        if (e.getSource() == this.btnImprimir) {
//            try {
//                HashMap param = new HashMap();
//                param.put("p_titulo", "Listado de Clientes");
//
//                JasperPrint jp = JasperFillManager.fillReport("src/reporte/rptCliente.jasper", param,
//                        new JRBeanCollectionDataSource(this.mod.datosCliente()));
//                JasperViewer.viewReport(jp, false);
//            } catch (JRException ex) {
//                System.err.println(ex.getMessage());
//            }
//        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cUtiles.verificarNulos(this.vis.pDatos) || !txtCodigo.getText().trim().matches("^[1-9][0-9]*")) {
            this.vis.btnGuardar.setEnabled(false);
        } else if (txtCodigo.getText().trim().length() > 9 || txtNombre.getText().trim().length() > 64
                || txtApellido.getText().trim().length() > 64 || txtRuc.getText().trim().length() > 14
                || txtTelefono.getText().trim().length() > 14) {
            this.vis.lblMensaje.setText("Codigo <= 10, Nombre y Apellido <= 65, Ruc y Telefono <= 15 caracteres");
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
        } else if (txtCodigo.getText().trim().length() > 9 || txtNombre.getText().trim().length() > 64
                || txtApellido.getText().trim().length() > 64 || txtRuc.getText().trim().length() > 14
                || txtTelefono.getText().trim().length() > 14) {
            this.vis.lblMensaje.setText("Codigo <= 10, Nombre y Apellido <= 65, Ruc y Telefono <= 15 caracteres");
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
        } else if (txtCodigo.getText().trim().length() > 9 || txtNombre.getText().trim().length() > 64
                || txtApellido.getText().trim().length() > 64 || txtRuc.getText().trim().length() > 14
                || txtTelefono.getText().trim().length() > 14) {
            this.vis.lblMensaje.setText("Codigo <= 10, Nombre y Apellido <= 65, Ruc y Telefono <= 15 caracteres");
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
            if (txtNombre.getText().length() > 64 || txtApellido.getText().length() > 64
                    || txtRuc.getText().length() > 14 || txtTelefono.getText().length() > 14) {
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
        if (e.getSource() == txtApellido) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                txtApellido.setText(txtApellido.getText().trim());
            }
        }
        if (e.getSource() == txtRuc) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                txtRuc.setText(txtRuc.getText().trim());
            }
        }
        if (e.getSource() == txtTelefono) {
            if (KeyEvent.VK_SPACE == e.getKeyCode()) {
                txtTelefono.setText(txtTelefono.getText().trim());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
