package umu.tds.helper;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

public  class AlineamientoLista extends DefaultListCellRenderer {
	private String alineamiento;
	public AlineamientoLista(String alineamiento) {
		this.alineamiento = alineamiento;
	}
	private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
           JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           label.setHorizontalAlignment(alineamiento.equals("centro") ? SwingConstants.CENTER : SwingConstants.LEFT); // Alinea a la derecha
           if(alineamiento.equals("centro"))
        	   label.setText((String) value);
           else label.setText((index + 1) + ".   " + value);
           return label;
       }
   }