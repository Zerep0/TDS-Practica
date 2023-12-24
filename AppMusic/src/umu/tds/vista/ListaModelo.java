package umu.tds.vista;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

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
		
		public void actualizarLista(ArrayList<String> canciones)
		{
			this.canciones = canciones;
			fireContentsChanged(canciones, 0, getSize());
		}
	

}
