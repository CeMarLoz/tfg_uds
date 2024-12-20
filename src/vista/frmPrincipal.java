/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.cCliente;
import controlador.cConexion;
import controlador.cCuenta;
import controlador.cFacturaVenta;
import controlador.cLecturaMedidor;
import controlador.cPresupuesto;
import controlador.cReclamo;
import controlador.cSolicitud;
import controlador.cUsuario;
import java.awt.Image;
import javax.swing.ImageIcon;
import modelo.eLogin;

/**
 *
 * @author CesarMatias
 */
public class frmPrincipal extends javax.swing.JFrame {

    frmFormulario doscampos;

    /**
     * Creates new form NewApplication
     */
    public frmPrincipal() {
        initComponents();

        setSize(800, 600);
        setTitle("Solución Tecnológica para Junta de Saneamiento Ambiental ubicado en la Ciudad de Caaguazú – Toro Blanco");
        setLocationRelativeTo(null);

        if ("admin".equals(eLogin.Alias)) {
            mnuSis.setVisible(true);
        } else {
            mnuSis.setVisible(false);
        }

        // icono de la aplicacion
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

        menuBar = new javax.swing.JMenuBar();
        mnuSer = new javax.swing.JMenu();
        mteSolicitud = new javax.swing.JMenuItem();
        mteConexion = new javax.swing.JMenuItem();
        mteLectura = new javax.swing.JMenuItem();
        mteCueta = new javax.swing.JMenuItem();
        mtePresupuesto = new javax.swing.JMenuItem();
        mteVenta = new javax.swing.JMenuItem();
        mteReclamo = new javax.swing.JMenuItem();
        mnuRep = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        mnuRef = new javax.swing.JMenu();
        mteCliente = new javax.swing.JMenuItem();
        mnuSis = new javax.swing.JMenu();
        mteUsuario = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mnuSer.setMnemonic('F');
        mnuSer.setText("Funciones");

        mteSolicitud.setMnemonic('s');
        mteSolicitud.setText("Solicitud");
        mteSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteSolicitudActionPerformed(evt);
            }
        });
        mnuSer.add(mteSolicitud);

        mteConexion.setMnemonic('c');
        mteConexion.setText("Conexión");
        mteConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteConexionActionPerformed(evt);
            }
        });
        mnuSer.add(mteConexion);

        mteLectura.setMnemonic('l');
        mteLectura.setText("Lectura de Medidor");
        mteLectura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteLecturaActionPerformed(evt);
            }
        });
        mnuSer.add(mteLectura);

        mteCueta.setMnemonic('u');
        mteCueta.setText("Cuenta");
        mteCueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteCuetaActionPerformed(evt);
            }
        });
        mnuSer.add(mteCueta);

        mtePresupuesto.setMnemonic('p');
        mtePresupuesto.setText("Presupueto");
        mtePresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mtePresupuestoActionPerformed(evt);
            }
        });
        mnuSer.add(mtePresupuesto);

        mteVenta.setMnemonic('f');
        mteVenta.setText("Factura");
        mteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteVentaActionPerformed(evt);
            }
        });
        mnuSer.add(mteVenta);

        mteReclamo.setMnemonic('r');
        mteReclamo.setText("Reclamo");
        mteReclamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteReclamoActionPerformed(evt);
            }
        });
        mnuSer.add(mteReclamo);

        menuBar.add(mnuSer);

        mnuRep.setMnemonic('e');
        mnuRep.setText("Reportes");

        jMenuItem1.setText("Listado de Solicitudes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnuRep.add(jMenuItem1);

        jMenuItem2.setText("Listado de Ventas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnuRep.add(jMenuItem2);

        jMenuItem3.setText("Listado de Reclamos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        mnuRep.add(jMenuItem3);

        jMenuItem4.setText("Listado de Cuentas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mnuRep.add(jMenuItem4);

        menuBar.add(mnuRep);

        mnuRef.setMnemonic('l');
        mnuRef.setText("Referenciales");

        mteCliente.setMnemonic('c');
        mteCliente.setText("Cliente");
        mteCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteClienteActionPerformed(evt);
            }
        });
        mnuRef.add(mteCliente);

        menuBar.add(mnuRef);

        mnuSis.setMnemonic('s');
        mnuSis.setText("Sistema");

        mteUsuario.setText("Usuario");
        mteUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mteUsuarioActionPerformed(evt);
            }
        });
        mnuSis.add(mteUsuario);

        menuBar.add(mnuSis);

        jMenu1.setText("Salir");

        jMenuItem5.setText("Salir");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mteSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteSolicitudActionPerformed
        frmSolicitud solicitud = new frmSolicitud();
        cSolicitud cSolicitud = new cSolicitud(solicitud);
        solicitud.setVisible(true);
    }//GEN-LAST:event_mteSolicitudActionPerformed

    private void mteClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteClienteActionPerformed
        doscampos = new frmFormulario();
        cCliente cCliente = new cCliente(doscampos);
        doscampos.setVisible(true);
    }//GEN-LAST:event_mteClienteActionPerformed

    private void mteReclamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteReclamoActionPerformed
        frmReclamo reclamo = new frmReclamo();
        cReclamo cReclamo = new cReclamo(reclamo);
        reclamo.setVisible(true);
    }//GEN-LAST:event_mteReclamoActionPerformed

    private void mteConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteConexionActionPerformed
        frmConexion conexion = new frmConexion();
        cConexion cConexion = new cConexion(conexion);
        conexion.setVisible(true);
    }//GEN-LAST:event_mteConexionActionPerformed

    private void mteLecturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteLecturaActionPerformed
        frmLecturaMedidor lectura = new frmLecturaMedidor();
        cLecturaMedidor cLecturaMedidor = new cLecturaMedidor(lectura);
        lectura.setVisible(true);
    }//GEN-LAST:event_mteLecturaActionPerformed

    private void mteCuetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteCuetaActionPerformed
        frmCuenta cuenta = new frmCuenta();
        cCuenta cCuenta = new cCuenta(cuenta);
        cuenta.setVisible(true);
    }//GEN-LAST:event_mteCuetaActionPerformed

    private void mtePresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mtePresupuestoActionPerformed
        frmPresupuesto presu = new frmPresupuesto();
        cPresupuesto cPresupuesto = new cPresupuesto(presu);
        presu.setVisible(true);
    }//GEN-LAST:event_mtePresupuestoActionPerformed

    private void mteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteVentaActionPerformed
        frmFacturaVenta vent = new frmFacturaVenta();
        cFacturaVenta cFacturaVenta = new cFacturaVenta(vent);
        vent.setVisible(true);
    }//GEN-LAST:event_mteVentaActionPerformed

    private void mteUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mteUsuarioActionPerformed
        doscampos = new frmFormulario();
        cUsuario cUsuario = new cUsuario(doscampos);
        doscampos.setVisible(true);
    }//GEN-LAST:event_mteUsuarioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        frmLIstadoSolicitud lSol = new frmLIstadoSolicitud();
        lSol.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        frmLIstadoVentas lVen = new frmLIstadoVentas();
        lVen.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        frmLIstadoReclamos lRec = new frmLIstadoReclamos();
        lRec.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        frmLIstadoCuentas lCue = new frmLIstadoCuentas();
        lCue.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        frmLogin v = new frmLogin();
        v.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnuRef;
    private javax.swing.JMenu mnuRep;
    private javax.swing.JMenu mnuSer;
    private javax.swing.JMenu mnuSis;
    private javax.swing.JMenuItem mteCliente;
    private javax.swing.JMenuItem mteConexion;
    private javax.swing.JMenuItem mteCueta;
    private javax.swing.JMenuItem mteLectura;
    private javax.swing.JMenuItem mtePresupuesto;
    private javax.swing.JMenuItem mteReclamo;
    private javax.swing.JMenuItem mteSolicitud;
    private javax.swing.JMenuItem mteUsuario;
    private javax.swing.JMenuItem mteVenta;
    // End of variables declaration//GEN-END:variables

}
