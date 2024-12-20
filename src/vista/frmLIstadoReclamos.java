package vista;

import java.awt.Image;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.eLogin;
import modelo.eReclamo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CesarMatias
 */
public class frmLIstadoReclamos extends javax.swing.JFrame implements DocumentListener {

    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    DateFormat datePy = new SimpleDateFormat("dd/MM/YYYY");
    eReclamo mod;

    public frmLIstadoReclamos() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Listado de Reclamos");
//        setSize(new Dimension(500, 180));

        btnImprimir.setEnabled(false);

        this.mod = new eReclamo();

        ((JTextField) txtFecIni.getDateEditor().getUiComponent()).getDocument().addDocumentListener(this);
        ((JTextField) txtFecFin.getDateEditor().getUiComponent()).getDocument().addDocumentListener(this);

//        icono de la aplicacion
        java.net.URL imageURL = frmPrincipal.class.getResource("/img/icono.png");
        ImageIcon icono = new ImageIcon(imageURL);
        Image im = icono.getImage();
        setIconImage(im);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        lblFecIni = new javax.swing.JLabel();
        txtFecIni = new com.toedter.calendar.JDateChooser();
        lblFechFin = new javax.swing.JLabel();
        txtFecFin = new com.toedter.calendar.JDateChooser();
        lblMensaje = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        lblFecIni.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblFecIni.setText("Fecha Inicio");

        txtFecIni.setDateFormatString("dd-MM-yyyy");
        txtFecIni.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFecIniPropertyChange(evt);
            }
        });

        lblFechFin.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblFechFin.setText("Fecha Fin");

        txtFecFin.setDateFormatString("dd-MM-yyyy");

        lblMensaje.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMensaje.setText(".");

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(btnImprimir);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minus.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMensaje, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblFecIni)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFecIni, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblFechFin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFecFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFechFin)
                    .addComponent(lblFecIni)
                    .addComponent(txtFecIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMensaje))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiardatos();
        btnImprimir.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        String fecha = "", where = "1";

        fecha = "Desde " + datePy.format(txtFecIni.getDate()) + " Hasta " + datePy.format(txtFecFin.getDate());
        where += " AND recfecha BETWEEN '" + dateFormat.format(txtFecIni.getDate()) + "' AND '" + dateFormat.format(txtFecFin.getDate()) + "'";

        try {
            HashMap param = new HashMap();
            param.put("P_WHERE", where);
            param.put("P_USER", eLogin.Alias);
            param.put("P_FECHA", fecha);
            JasperPrint jp = JasperFillManager.fillReport("src/reporte/rptListadoReclamos.jasper", param, this.mod.cone());
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtFecIniPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFecIniPropertyChange
        if (txtFecIni.getDate() != null) {
            txtFecFin.setMinSelectableDate(txtFecIni.getDate());
        }
    }//GEN-LAST:event_txtFecIniPropertyChange

    private void limpiardatos() {

        txtFecIni.setDate(null);
        txtFecFin.setDate(null);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLIstadoReclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLIstadoReclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLIstadoReclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLIstadoReclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLIstadoReclamos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblFecIni;
    private javax.swing.JLabel lblFechFin;
    private javax.swing.JLabel lblMensaje;
    public com.toedter.calendar.JDateChooser txtFecFin;
    public com.toedter.calendar.JDateChooser txtFecIni;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (txtFecIni.getDate() == null || txtFecFin.getDate() == null) {
            lblMensaje.setText("Fecha Inicio y Fecha Fin no puden estar nulos");
            btnImprimir.setEnabled(false);
        } else if (txtFecIni.getDate().after(txtFecFin.getDate())) {
            lblMensaje.setText("Fecha Inicio <= Fecha Fin");
            btnImprimir.setEnabled(false);
        } else {
            lblMensaje.setText(null);
            btnImprimir.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (txtFecIni.getDate() == null || txtFecFin.getDate() == null) {
            lblMensaje.setText("Fecha Inicio y Fecha Fin no puden estar nulos");
            btnImprimir.setEnabled(false);
        } else if (txtFecIni.getDate().after(txtFecFin.getDate())) {
            lblMensaje.setText("Fecha Inicio <= Fecha Fin");
            btnImprimir.setEnabled(false);
        } else {
            lblMensaje.setText(null);
            btnImprimir.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (txtFecIni.getDate() == null || txtFecFin.getDate() == null) {
            lblMensaje.setText("Fecha Inicio y Fecha Fin no puden estar nulos");
            btnImprimir.setEnabled(false);
        } else if (txtFecIni.getDate().after(txtFecFin.getDate())) {
            lblMensaje.setText("Fecha Inicio <= Fecha Fin");
            btnImprimir.setEnabled(false);
        } else {
            lblMensaje.setText(null);
            btnImprimir.setEnabled(true);
        }
    }
}
