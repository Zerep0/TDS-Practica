package umu.tds.vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import umu.tds.negocio.Cancion;

public class ListaModelo extends AbstractListModel<String> {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		ArrayList<String> canciones = new ArrayList<String>();
		public int getSize() {
			return canciones.size();
		}
		
		public String getElementAt(int index) {
			return canciones.get(index);
		}
		
		public void actualizarLista(List<String> canciones)
		{
			this.canciones = (ArrayList<String>) canciones;
			fireContentsChanged(canciones, 0, getSize());

		}


}
