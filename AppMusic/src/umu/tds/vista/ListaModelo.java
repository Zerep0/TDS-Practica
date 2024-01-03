package umu.tds.vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import umu.tds.negocio.Cancion;

public class ListaModelo<T> extends AbstractListModel<T> {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		ArrayList<T> canciones = new ArrayList<T>();
		public int getSize() {
			return canciones.size();
		}
		
		public T getElementAt(int index) {
			return canciones.get(index);
		}
		
		public void actualizarLista(ArrayList<T> canciones)
		{
			this.canciones = canciones;
			fireContentsChanged(canciones, 0, getSize());
		}
		
		public void add(T c)
		{
			int i;
			for(i = 0;i<canciones.size();i++)
			{
				if(c.equals(canciones.get(i)))
				{
					return;
				}
			}
			canciones.add(c);
			fireContentsChanged(canciones, 0, getSize());
		}
		
		public void remove(T nombre)
		{
			int i;
			for(i = 0;i<canciones.size();i++)
			{
				if(nombre.equals(canciones.get(i)))
				{
					canciones.remove(nombre);
					fireContentsChanged(canciones, 0, getSize());
					return;
				}
			}
		}


}
