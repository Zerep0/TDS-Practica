package umu.tds.vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import umu.tds.negocio.Cancion;

public class ListaModelo extends AbstractListModel<Cancion> {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		public int getSize() {
			return canciones.size();
		}
		
		public Cancion getElementAt(int index) {
			return canciones.get(index);
		}
		
		public void actualizarLista(ArrayList<Cancion> canciones)
		{
			this.canciones = canciones;
			fireContentsChanged(canciones, 0, getSize());
		}


}
