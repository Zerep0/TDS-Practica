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
    private Color color;
     
    public RenderizadorLetrasCabecera(TableCellRenderer miTabla,Color color) {
        this.miTabla = miTabla;
        this.color = color;
    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = miTabla.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
        if (comp instanceof JLabel) {
            JLabel label = (JLabel) comp;
            label.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
            label.setForeground(color);
            label.setBackground(new Color(193,255,245));
            label.setBorder(BorderFactory.createEtchedBorder());
        }
         
        return comp;
    }
 
}