package umu.tds.vista;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import umu.tds.negocio.Cancion;

class MiTablaPersonalizada extends AbstractTableModel {
	
	private LinkedList<Cancion> canciones;
	private String[] columnas = {"Titulo","Interprete","Estilo","Seleccionar"};
	private static final long serialVersionUID = 1L;
	private String nombrePlaylist;
	
	public MiTablaPersonalizada()
	{
		canciones = new LinkedList<>();
		nombrePlaylist = "";
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
	    if (canciones == null || canciones.isEmpty()) {
	        return null;
	    }

	    Cancion cancion = canciones.get(row);
	    switch (column) {
	        case 0:
	            return cancion.getTitulo();
	        case 1:
	            return cancion.getInterprete();
	        case 2:
	            return cancion.getEstilo();
	        case 3:
	            return false;
	        default:
	            return null;
	    }
	}
	
	public Cancion getCancionAt(int row)
	{
		Cancion cancion = canciones.get(row);
		return cancion;
	}
	
	public LinkedList<Cancion> getCanciones()
	{
		return canciones;
	}
	
	public void setValueAt(Object value, int row, int column) {
	    if (canciones != null && !canciones.isEmpty()) {
	        Cancion cancion = canciones.get(row);
	        if (column == 3) {
	            // Verifica si la canción no es nula antes de acceder a sus propiedades.
	            if (cancion != null) {
	                //cancion.setFavorita((Boolean) value);
	                fireTableCellUpdated(row, column);
	            }
	        }
	    }
	}
	
	public void actualizarTabla(LinkedList<Cancion> nuevasCanciones, String nombrePlaylist) {
		this.nombrePlaylist = nombrePlaylist;
	    this.canciones = nuevasCanciones;
	    fireTableDataChanged();
	}
	
	public void eliminarFilas()
	{
		canciones.clear();
		fireTableDataChanged();
	}
	
	public String getNombrePlaylist()
	{
		return nombrePlaylist;
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