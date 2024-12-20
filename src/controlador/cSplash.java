package controlador;

import javax.swing.JProgressBar;

public class cSplash extends Thread {

    JProgressBar progreso;

    public cSplash(JProgressBar progreso) {
        super();
        this.progreso = progreso;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            progreso.setValue(i);
            pausa(10);
        }
    }

    public void pausa(int mlSeg) {
        try {
            Thread.sleep(mlSeg);
        } catch (Exception e) {

        }
    }
}
