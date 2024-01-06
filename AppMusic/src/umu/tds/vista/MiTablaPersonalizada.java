package umu.tds.vista;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

import umu.tds.negocio.Cancion;

class MiTablaPersonalizada extends AbstractTableModel {
	
	private LinkedList<Cancion> canciones;
	private String[] columnas = {"Titulo","Interprete","Estilo","Seleccionar"};
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private String nombrePlaylist;
	private HashSet<Integer> filasMarcadas;
	
	public MiTablaPersonalizada()
	{
		canciones = new LinkedList<>();
		nombrePlaylist = "";
		filasMarcadas = new HashSet<Integer>();
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
	            return checkBoxes.get(row).isSelected();
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
			checkBoxes.get(row).setSelected(!checkBoxes.get(row).isSelected());
            fireTableCellUpdated(row, column);
        }
	}
	
	
	
	public void actualizarTabla(LinkedList<Cancion> nuevasCanciones, String nombrePlaylist) {
		if(this.nombrePlaylist != nombrePlaylist)
		{
			this.nombrePlaylist = nombrePlaylist;
		    this.canciones = nuevasCanciones;
		    inicializarCheckBoxes();
		    fireTableDataChanged();
		}
		
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
        return String.class;
	}
		
	public boolean isCellEditable(int row, int col) {
		
		return  col == 3; // Solo la columna de checkboxes es editable.
	}
	
	public void inicializarCheckBoxes()
	{
	     checkBoxes.clear();
		 for(int i = 0;i<canciones.size();i++)
		 {
			 checkBoxes.add(new JCheckBox());
		 }	
	}
	
	public boolean isCancionesMarcadas()
	{
		return filasMarcadas.size() > 0;
	}

	public void añadirMarcada(int selectedRow) {
		filasMarcadas.add(selectedRow);
		
	}

	public void quitarMarcada(int selectedRow) {
		filasMarcadas.remove(selectedRow);
	}
	
	public HashSet<Integer> listaMarcadas()
	{
		return filasMarcadas;
	}
	
	public void borrarCancion(int row)
	{
		canciones.remove(row);
		checkBoxes.remove(row);
		fireTableDataChanged();
	}
	
	public void showCheckBox()
	{
		int i = 0;
		for (JCheckBox check : checkBoxes) {
			System.out.println("La checkbox " + i + " esta " + check.isSelected());
			i++;
		}
		
	}
}