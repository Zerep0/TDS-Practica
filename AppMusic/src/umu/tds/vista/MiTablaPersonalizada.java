package umu.tds.vista;

import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import umu.tds.negocio.Cancion;

class MiTablaPersonalizada extends AbstractTableModel {
	
	private LinkedList<Cancion> canciones;
	private String[] columnas = {"Titulo","Interprete","Estilo","Seleccionar"};
	private static final long serialVersionUID = 1L;
	private String nombrePlaylist;
	private HashSet<Cancion> filasMarcadas;
	
	public MiTablaPersonalizada()
	{
		canciones = new LinkedList<>();
		nombrePlaylist = "";
		filasMarcadas = new HashSet<Cancion>();
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
	            return cancion.isMarcada();
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
		if (column == 3) {
            // Actualizar el valor del checkbox
			boolean marcada = (boolean) value;
			if(marcada)
				añadirMarcada(row);
			else quitarMarcada(row);
			canciones.get(row).setMarcada(marcada);
            fireTableCellUpdated(row, column);
        }
	}
	
	
	
	public void actualizarTabla(LinkedList<Cancion> nuevasCanciones, String nombrePlaylist) {
			this.nombrePlaylist = nombrePlaylist;
		    this.canciones = nuevasCanciones;
		    nuevasCanciones.forEach(c -> c.setMarcada(false));
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
	
	public boolean isCancionesMarcadas()
	{
		return filasMarcadas.size() > 0;
	}

	public void añadirMarcada(int selectedRow) {
		Cancion c = canciones.get(selectedRow);
		filasMarcadas.add(c);
		
	}

	public void quitarMarcada(int selectedRow) {
		Cancion c = canciones.get(selectedRow);
		filasMarcadas.remove(c);
	}
	
	public HashSet<Cancion> listaMarcadas()
	{
		return filasMarcadas;
	}
	
	
	public void borrarCanciones()
	{
		this.canciones.removeIf(c -> filasMarcadas.contains(c));
		filasMarcadas.clear();
		fireTableDataChanged();
	}

}