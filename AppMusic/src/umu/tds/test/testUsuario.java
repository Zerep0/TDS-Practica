package umu.tds.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import umu.tds.negocio.Cancion;
import umu.tds.negocio.Usuario;

public class testUsuario {
	
	/**
	 * Este test simula el inicio de la aplicacion, y añadir una cancion a reciente
	 */
	@Test
    public void testaddReciente() {
        Usuario usuario = new Usuario("testUser", "password", "test@example.com", LocalDate.now());
        Cancion cancion1 = new Cancion("Cancion1","Ruta1","Estilo1", "Interprete1");
        Cancion cancion2 = new Cancion("Cancion2","Ruta2","Estilo2", "Interprete2");

        // Verificar que la lista de canciones recientes se actualiza correctamente
        usuario.addReciente(cancion1, false);
        assertEquals(1, usuario.getRecientes().size());

        usuario.addReciente(cancion2, false);
        assertEquals(2, usuario.getRecientes().size());

        // Verificar que las canciones recientes se reorganizan correctamente
        assertEquals(cancion2, usuario.getRecientes().get(0));
        assertEquals(cancion1, usuario.getRecientes().get(1));
    }

    @Test
    public void testAddFavorita() {
        Usuario usuario = new Usuario("testUser", "password", "test@example.com", LocalDate.now());
        Cancion cancion1 = new Cancion("Cancion1","Ruta1","Estilo1", "Interprete1");
        Cancion cancion2 = new Cancion("Cancion2","Ruta2","Estilo2", "Interprete2");
        // Verificar que la lista de canciones favoritas se actualiza correctamente, sin duplicados
        usuario.addFavorita(cancion1);
        usuario.addFavorita(cancion2);
        usuario.addFavorita(cancion1);
        // Verificar que la playlist "Favoritas" se actualiza correctamente
        assertEquals(2, usuario.getCancionesPlaylist("Favoritas").size());
        
        usuario.removeFavorita(cancion2);
        assertEquals(cancion1, usuario.getCancionesPlaylist("Favoritas").get(0));
    }

}
