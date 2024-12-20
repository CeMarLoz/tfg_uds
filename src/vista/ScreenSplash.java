package vista;

import controlador.cSplash;
import java.awt.Image;
import javax.swing.ImageIcon;

public final class ScreenSplash extends javax.swing.JFrame {

    double i = 50, j = 1;
    cSplash hilo;

    public ScreenSplash() {
        initComponents();
        iniciar();

//         icono de la aplicacion
        java.net.URL imageURL = frmPrincipal.class.getResource("/img/icono.png");
        ImageIcon icono = new ImageIcon(imageURL);
        Image im = icono.getImage();
        setIconImage(im);
    }

    public void iniciar() {
        setLocationRelativeTo(null);
        // Progreso.setVisible(false);
        hilo = new cSplash(getProgreso());
        hilo.start();
        hilo = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Progreso = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Progreso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ProgresoStateChanged(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/splash.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void ProgresoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ProgresoStateChanged
    if (Progreso.getValue() == i) {
        if (j != 101) {
//            AWTUtilities.setWindowOpacity(this, Float.valueOf((100 - j) / 100 + "f"));
            i++;
            j += 2;
        }
    }
    if (Progreso.getValue() == 100) {
        frmLogin v = new frmLogin();
        v.setVisible(true);
        this.dispose();
    }
}//GEN-LAST:event_ProgresoStateChanged

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
            java.util.logging.Logger.getLogger(ScreenSplash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScreenSplash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScreenSplash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScreenSplash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ScreenSplash().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Progreso;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the Progreso
     */
    public javax.swing.JProgressBar getProgreso() {
        return Progreso;
    }

    /**
     * @param Progreso the Progreso to set
     */
    public void setProgreso(javax.swing.JProgressBar Progreso) {
        this.Progreso = Progreso;
    }
}
