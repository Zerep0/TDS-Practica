package umu.tds.helper;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase que proporciona métodos para la creación de placeholders en componentes de interfaz de usuario Swing.
 */
public class Placeholder {

    private static final String TEXTO_VACIO = "";

    /**
     * Crea un placeholder para un campo de texto.
     *
     * @param campo      El campo de texto al que se le agregará el placeholder.
     * @param PLACEHOLDER El texto del placeholder.
     */
    public void crearPlaceholderText(JTextField campo, String PLACEHOLDER) {
        campo.addFocusListener(new FocusAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(PLACEHOLDER) && campo.getFont().isItalic()) {
                    campo.setText(TEXTO_VACIO);
                    Font nuevaFuente = new Font("Arial", Font.PLAIN, 14);
                    campo.setFont(nuevaFuente);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().equals(TEXTO_VACIO)) {
                    campo.setFont(new Font("Arial", Font.ITALIC, 14));
                    campo.setText(PLACEHOLDER);
                }
            }
        });
    }

    /**
     * Crea un placeholder para un campo de contraseña con un botón de visibilidad.
     *
     * @param campo         El campo de contraseña al que se le agregará el placeholder.
     * @param PLACEHOLDER   El texto del placeholder.
     * @param btnVisibilidad El botón de visibilidad asociado al campo de contraseña.
     */
    public void crearPlaceholderPassword(JPasswordField campo, String PLACEHOLDER, JButton btnVisibilidad) {
        campo.addFocusListener(new FocusAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(campo.getPassword()).equals(PLACEHOLDER) && campo.getFont().isItalic()) {
                    campo.setText(TEXTO_VACIO);
                    campo.setFont(new Font("Arial", Font.PLAIN, 14));
                    campo.setEchoChar('*');
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(campo.getPassword()).equals(TEXTO_VACIO)) {
                    campo.setEchoChar((char) 0);
                    campo.setFont(new Font("Arial", Font.ITALIC, 14));
                    campo.setText(PLACEHOLDER);
                    btnVisibilidad.setEnabled(false);
                }
            }
        });
    }

    /**
     * Crea un placeholder para un campo de contraseña sin un botón de visibilidad.
     *
     * @param campo      El campo de contraseña al que se le agregará el placeholder.
     * @param PLACEHOLDER El texto del placeholder.
     */
    public void crearPlaceholderPassword(JPasswordField campo, String PLACEHOLDER) {
        campo.addFocusListener(new FocusAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(campo.getPassword()).equals(PLACEHOLDER) && campo.getFont().isItalic()) {
                    campo.setText(TEXTO_VACIO);
                    campo.setFont(new Font("Arial", Font.PLAIN, 14));
                    campo.setEchoChar('*');
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(campo.getPassword()).equals(TEXTO_VACIO)) {
                    campo.setEchoChar((char) 0);
                    campo.setFont(new Font("Arial", Font.ITALIC, 14));
                    campo.setText(PLACEHOLDER);
                }
            }
        });
    }
}
