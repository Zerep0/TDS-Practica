package umu.tds.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import umu.tds.negocio.Cancion;

import java.io.IOException;

public class Player {
	// canciones almacenadas en src/main/resources
	private Cancion cancionActual = null;
	private MediaPlayer mediaPlayer;
	
	public Player(){
		//existen otras formas de lanzar JavaFX desde Swing
		try {
			com.sun.javafx.application.PlatformImpl.startup(()->{});			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}
	public void play(String boton, Cancion cancion){
		switch (boton) { 
		case "play":
			try {
				setCancionActual(cancion);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mediaPlayer.play();
			break;
		case "stop": 
			mediaPlayer.stop();
			break;
		case "pause": 
			mediaPlayer.pause();
			break;
	}
	}
	private void setCancionActual(Cancion cancion) throws FileNotFoundException {
		if (cancionActual != cancion){
			cancionActual = cancion;
			String rutaCancion = cancion.getRuta();
		    System.out.println(rutaCancion);
			URL resourceURL = null;
			try {
				resourceURL = new URL(rutaCancion);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Path mp3 = null;
			try {
				mp3 = Files.createTempFile("now-playing", ".mp3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};

			System.out.println(mp3.getFileName());
			try (InputStream stream = resourceURL.openStream()) {
				Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("finished-copy: " + mp3.getFileName());

			Media hit = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(hit);
			
		}
		
		
	}

}