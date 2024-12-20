package controlador;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author CesarMatias
 */
public class cUtiles {

    public static Boolean booLocal;
    public static JLabel jlab;
    public static JTextField jText;
    public static JComboBox jCom;
    public static JFormattedTextField jFT;
    public static JPasswordField jPass;
    public static JScrollPane jScroll;
    public static JDateChooser jDateCho;
    public static JTextArea jTA;
    public static JCheckBox jCheck;

    /**
     * Coloca a todo lo que se encuentre dentro de un jPanel en el formato null.
     * Los formatos soportados por este metedo son: jTextfiedl, jCombox,
     * jDateChooser, jFormatterTextfield.
     *
     * @param jpanel
     */
    public static void limpiarCampos(JPanel jpanel) {
        for (int i = 0; i < jpanel.getComponentCount(); i++) {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) {
                jText = (JTextField) jpanel.getComponent(i);
                jText.setText(null);
            } else if (jpanel.getComponent(i).getClass().equals(JComboBox.class)) {
                jCom = (JComboBox) jpanel.getComponent(i);
                jCom.setSelectedIndex(0);
            } else if (jpanel.getComponent(i).getClass().equals(JFormattedTextField.class)) {
                jFT = (JFormattedTextField) jpanel.getComponent(i);
                /*Si formatter.setAllowsInvalid(false) es falso no permite cargar otro tipo de 
                valor que no sea lo que se establecio en este caso no permite el setText null
                por eso se uso el setValue null*/
                jFT.setValue(null);
            } else if (jpanel.getComponent(i).getClass().equals(JCheckBox.class)) {
                jCheck = (JCheckBox) jpanel.getComponent(i);
                jCheck.setSelected(false);
            } else if (jpanel.getComponent(i).getClass().equals(JPasswordField.class)) {
                jPass = (JPasswordField) jpanel.getComponent(i);
                jPass.setText(null);
            } else if (jpanel.getComponent(i).getClass().equals(JDateChooser.class)) {
                jDateCho = (JDateChooser) jpanel.getComponent(i);
                jDateCho.setDate(null);
            }
            // En caso de tener un jScrollPane
            if (jpanel.getComponent(i).getClass().equals(JScrollPane.class)) {
                jScroll = (JScrollPane) jpanel.getComponent(i);

                // si el contenido es un area de texto
                if (jScroll.getViewport().getView().getClass().equals(JTextArea.class)) {
                    jTA = (JTextArea) jScroll.getViewport().getView();
                    jTA.setText(null);
                }
            }
        }
    }

    /**
     * Metodo que permite recibir un contenedor y colocarle segun el booleano
     * exceptuando los objetos que recibe para excluirlos.
     *
     * @param jpanel
     * @param boo
     * @param jtf
     */
    public static void habilitarCampos(JPanel jpanel, Boolean boo, Object[] ObjectException) {
        for (int i = 0; i < jpanel.getComponentCount(); i++) {
            booLocal = false;
            for (Object ObjectException1 : ObjectException) {
                if (jpanel.getComponent(i).equals(ObjectException1)) {
                    jpanel.getComponent(i).setEnabled(!boo);
                    booLocal = true;

                    // En caso de tener un jScrollPane
                    if (jpanel.getComponent(i).getClass().equals(JScrollPane.class)) {
                        jScroll = (JScrollPane) jpanel.getComponent(i);
                        jScroll.getHorizontalScrollBar().setEnabled(!boo);
                        jScroll.getVerticalScrollBar().setEnabled(!boo);
                        jScroll.getViewport().getView().setEnabled(!boo);
                    }
                    break;
                }
            }
            if (!booLocal) {
                jpanel.getComponent(i).setEnabled(boo);

                // En caso de tener un jScrollPane
                if (jpanel.getComponent(i).getClass().equals(JScrollPane.class)) {
                    jScroll = (JScrollPane) jpanel.getComponent(i);
                    jScroll.getHorizontalScrollBar().setEnabled(boo);
                    jScroll.getVerticalScrollBar().setEnabled(boo);
                    jScroll.getViewport().getView().setEnabled(boo);
                }
            }
            if (jpanel.getComponent(i).getClass().equals(JLabel.class)) {
                jlab = (JLabel) jpanel.getComponent(i);
                jlab.setEnabled(true);
            }
        }
    }

    /**
     * Agrupa un conjunto de textField y se evalua si por lo menos un dato se
     * encuentra vacio para retornar true de otro modo retorna falso
     *
     * @param jpanel
     * @return
     */
    public static boolean verificarNulos(JPanel jpanel) {
        boolean errConver = false;
        for (int i = 0; i < jpanel.getComponentCount(); i++) {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) {
                jText = (JTextField) jpanel.getComponent(i);
                if (jText.getText().trim().isEmpty()) {
                    return true;
                }
            } else if (jpanel.getComponent(i).getClass().equals(JPasswordField.class)) {
                jText = (JPasswordField) jpanel.getComponent(i);
                if (jText.getText().trim().isEmpty()) {
                    return true;
                }
            } else if (jpanel.getComponent(i).getClass().equals(JScrollPane.class)) {
                jScroll = (JScrollPane) jpanel.getComponent(i);
                // si el contenido es un area de texto
                if (jScroll.getViewport().getView().getClass().equals(JTextArea.class)) {
                    jTA = (JTextArea) jScroll.getViewport().getView();
                    if (jTA.getText().trim().isEmpty()) {
                        return true;
                    }
                }
            } else if (jpanel.getComponent(i).getClass().equals(JFormattedTextField.class)) {
                jFT = (JFormattedTextField) jpanel.getComponent(i);

                // en caso de number
                if (jFT.getText().trim().isEmpty()) {
                    return true;
                } else {
                    // en caso de fecha 
                    if (!jFT.getText().matches("^(\\d{1,3}(?:[.]\\d{3})*(?:[,]\\d+)?)")) {
                        try {
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            formatoFecha.setLenient(false);
                            formatoFecha.parse(jFT.getText());
                        } catch (ParseException ex) {
                            errConver = true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Agrupa un conjunto de textField y asigna el atributo selectAll
     *
     * @param jpanel
     */
    public static void selectAll(JPanel jpanel) {
        for (int i = 0; i < jpanel.getComponentCount(); i++) {
            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) {
                jText = (JTextField) jpanel.getComponent(i);
                jText.selectAll();
            }
            if (jpanel.getComponent(i).getClass().equals(JFormattedTextField.class)) {
                jFT = (JFormattedTextField) jpanel.getComponent(i);
                jFT.selectAll();
            }
        }
    }

    public static void pasarFocus(JPanel jpanel) {
        //combox, textfield, jformatter, jbotton
        for (int i = 0; i < jpanel.getComponentCount(); i++) {

            if (jpanel.getComponent(i).getClass().equals(JTextField.class)) {
                jText = (JTextField) jpanel.getComponent(i);
                jText.addActionListener(tranfiereElFoco);
            } else if (jpanel.getComponent(i).getClass().equals(JComboBox.class)) {
                jCom = (JComboBox) jpanel.getComponent(i);
                jCom.addActionListener(tranfiereElFoco);
                jCom.addKeyListener(new PresionarTecla());
            } else if (jpanel.getComponent(i).getClass().equals(JFormattedTextField.class)) {
                jFT = (JFormattedTextField) jpanel.getComponent(i);
                jFT.addActionListener(tranfiereElFoco);
            }
        }
    }

    /**
     * Encargada de transferir el foco al siguiente componente.
     */
    private static ActionListener tranfiereElFoco = new ActionListener() {

        // @Override
        public void actionPerformed(ActionEvent arg0) {
            // Se transfiere el foco al siguiente elemento.
            ((Component) arg0.getSource()).transferFocus();
        }
    };

    public static class PresionarTecla extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                // if (ke.getSource().getClass().equals(JButton.class)) {
                //     ((JButton) ke.getSource()).doClick();
                // }
                if (ke.getSource().getClass().equals(JComboBox.class)) {
                    ((JComboBox) ke.getSource()).transferFocus();
                }
            }
        }
    }

    /**
     * Sirva para agregar mes a la fecha
     *
     * @param date
     * @param amount
     * @return date
     */
    public static Date addMonth(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);

        return calendar.getTime();
    }
}
