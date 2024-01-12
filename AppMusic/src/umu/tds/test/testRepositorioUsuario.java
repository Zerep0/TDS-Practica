package umu.tds.test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;


import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.Usuario;

public class testRepositorioUsuario {
	private CatalogoUsuarios catalogo;

    @Before
    public void inicio() {
        catalogo = CatalogoUsuarios.getUnicaInstancia();
    }
 

    @Test
    public void testAddUsuario() {
        // Verificar que se puede agregar un usuario correctamente
    	catalogo.borrarUsuarios();
        Usuario usuario = new Usuario("testUser", "password", "test@example.com", LocalDate.now());
        
        assertTrue(catalogo.addUsuario(usuario));
        assertNotNull(catalogo.getUsuario("testUser"));

        // Verificar que no se puede agregar un usuario con el mismo login
        Usuario usuarioDuplicado = new Usuario("testUser", "password2", "test2@example.com", LocalDate.now());
        assertFalse(catalogo.addUsuario(usuarioDuplicado));
    }
    
    @Test
    public void testGetUsuarios() {
        // Verificar que se obtienen correctamente todos los usuarios
    	catalogo.borrarUsuarios();
        assertNotNull(catalogo.getUsuarios());
        assertTrue(catalogo.getUsuarios().isEmpty());
        
        List<Usuario> usuariosPrueba = new LinkedList<>();
        usuariosPrueba.add(new Usuario("testUser", "password", "test@example.com", LocalDate.now()));
        usuariosPrueba.add(new Usuario("testUser1", "password1", "test1@example.com", LocalDate.now()));
        usuariosPrueba.add(new Usuario("testUser2", "password2", "test2@example.com", LocalDate.now()));

        usuariosPrueba.forEach(u -> catalogo.addUsuario(u));
        
        assertNotNull(catalogo.getUsuario("testUser"));
        assertNotNull(catalogo.getUsuario("testUser"));
        assertNotNull(catalogo.getUsuario("testUser"));
        assertEquals(usuariosPrueba.size(),catalogo.getUsuarios().size());
    }

    @Test
    public void testGetUsuario() {
    	catalogo.borrarUsuarios();
        // Agregar un usuario y verificar que se puede recuperar correctamente por su login
        Usuario usuario = new Usuario("testUser", "password", "test@example.com", LocalDate.now());
        catalogo.addUsuario(usuario);
        assertNotNull(catalogo.getUsuario("testUser"));
        assertNotNull(catalogo.getUsuario(usuario.getId()));

        // Verificar que no se puede recuperar un usuario inexistente
        assertNull(catalogo.getUsuario("nonExistentUser"));
    }

}
