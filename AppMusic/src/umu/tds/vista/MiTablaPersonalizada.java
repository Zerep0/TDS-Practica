package umu.tds.vista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import umu.tds.negocio.Cancion;

class MiTablaPersonalizada extends AbstractTableModel {
	
	private LinkedList<Cancion> canciones;
	private String[] columnas = {"Titulo","Interprete","Estilo","Seleccionar"};
	private static final long serialVersionUID = 1L;
	
	public MiTablaPersonalizada()
	{
		this.canciones = new LinkedList<>();
	}

	public int getRowCount() {
		
		return canciones.size();
	}

	public int getColumnCount() {
		
		return columnas.length;
	}
	
	public String getColumnName(int col) {
		
		return columnas[col];
	}

	public Object getValueAt(int row, int column) {
        Cancion cancion = canciones.get(row);
        switch (column) {
            case 0:
                return cancion.getTitulo();
            case 1:
                return cancion.getInterprete();
            case 2:
                return cancion.getEstilo();
            case 3:
                return true;
            default:
                return null;
        }
    }
	
	public void setValueAt(Object value, int row, int column) {
        Cancion cancion = canciones.get(row);
        if (column == 3) {
        	
            //cancion.setFavorita((Boolean) value);
        }
        fireTableCellUpdated(row, column);
    }
	
	public void actualizarTabla(LinkedList<Cancion> canciones)
	{
		this.canciones = canciones;
        fireTableDataChanged();
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 3) {
            return Boolean.class; // CheckBox
        }
        return super.getColumnClass(columnIndex);
	}
		
	public boolean isCellEditable(int row, int col) {
		
		return  col == 3; // Solo la columna de checkboxes es editable.
	}

}