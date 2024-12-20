/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author CesarMatias
 */
public class cRealizarCopiaSeg {

    public void Export() {
        try {
            Process p = Runtime.getRuntime().exec("mysqldump -u root -p12345 aguateria --routines");
            InputStream is = p.getInputStream();
            FileOutputStream fos = new FileOutputStream("backup-" + LocalDate.now() + ".sql");

            byte[] buffer = new byte[1000];
            int leido = is.read(buffer);

            while (leido > 0) {
                fos.write(buffer, 0, leido);
                leido = is.read(buffer);
            }
            fos.close();

            JOptionPane.showMessageDialog(null, "Copia de Seguridad Terminado con nombre: " + "backup-" + LocalDate.now() + ".sql");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.err.println(ex.getMessage());
        }
    }
}
