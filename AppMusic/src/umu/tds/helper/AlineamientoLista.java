package umu.tds.helper;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public  class AlineamientoLista extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
           JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           label.setHorizontalAlignment(JLabel.LEFT); // Alinea a la derecha
           label.setText("                                       " + (index + 1) + ".   " + value);
           label.setIcon(new ImageIcon("src/PortadasMusica/wosMiniatura.png"));
           return label;
       }
   }