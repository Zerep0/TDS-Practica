package umu.tds.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderizadorLetrasCabecera implements TableCellRenderer {
    
    private TableCellRenderer miTabla;
    private Font fuente;
    private Color color;
     
    public RenderizadorLetrasCabecera(TableCellRenderer miTabla, Font fuente,Color color) {
    	this.fuente = fuente;
        this.miTabla = miTabla;
        this.color = color;
    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = miTabla.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
        if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            label.setFont(fuente);
            label.setForeground(color);
            label.setBorder(BorderFactory.createEtchedBorder());
        }
         
        return comp;
    }
 
}