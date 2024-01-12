package umu.tds.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import umu.tds.controlador.ControladorAppMusic;
import umu.tds.negocio.Cancion;

import java.io.IOException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public enum Player {
	INSTANCE;
	// canciones almacenadas en src/main/resources
	private Cancion cancionActual = null;
	private MediaPlayer mediaPlayer;
	
	private Player(){
		//existen otras formas de lanzar JavaFX desde Swing
		try {
			com.sun.javafx.application.PlatformImpl.startup(()->{});			
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception: " + ex.getMessage());
		}
	}
	public void play(String boton, Cancion cancion, LinkedList<JSlider> sliders, LinkedList<JLabel> labels){
		switch (boton) { 
		case "play":
			try {
				setCancionActual(cancion, sliders, labels);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mediaPlayer.play();
			break;
		case "stop": 
			mediaPlayer.stop();
			cancionActual = null;
			break;
		case "pause": 
			mediaPlayer.pause();
			break;
	}
	}
	private void setCancionActual(Cancion cancion, LinkedList<JSlider> sliders, LinkedList<JLabel> labels) throws FileNotFoundException {
		if (cancionActual != cancion){
			cancionActual = cancion;
			String rutaCancion = cancion.getRuta();
		    System.out.println(rutaCancion);
			URL resourceURL = null;
			try {
				resourceURL = new URL(rutaCancion);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Path mp3 = null;
			try {
				mp3 = Files.createTempFile("now-playing", ".mp3");
			} catch (IOException e) {
				e.printStackTrace();
			};

			System.out.println(mp3.getFileName());
			try (InputStream stream = resourceURL.openStream()) {
				Files.copy(stream, mp3, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("finished-copy: " + mp3.getFileName());

			Media hit = new Media(mp3.toFile().toURI().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.setVolume(ControladorAppMusic.getInstancia().getVolumen());
			
			mediaPlayer.setOnEndOfMedia(() -> {
		        ControladorAppMusic.getInstancia().siguienteAlFinalizar();
		        
		    });
			
			
			
			Timer timer = new Timer(100, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               int i = 0;
	               for(JSlider s : sliders)
	               {
	            	   actualizarSlider(s,labels.get(i));
	            	   i++;
	               }
	                
	            }
	        });
			
			timer.start();
			
		}
		
	}
	
	public Cancion getCancionReproduciendo()
	{
		return cancionActual;
	}
	
	public void actualizarSlider(JSlider slider, JLabel etiqueta)
	{
		int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds(); // Reemplázalo con el método específico de tu MediaPlayer
        slider.setValue(currentTime);
        int minutos = currentTime / 60;
        int segundos = currentTime % 60;
        etiqueta.setText(String.format("%02d:%02d", minutos, segundos));
        slider.setMaximum((int)mediaPlayer.getTotalDuration().toSeconds());
	}
	
	public void actualizarVolumen(float nuevoVolumen) {
		if(cancionActual != null)
		{
			mediaPlayer.setVolume(nuevoVolumen);
			System.out.println(nuevoVolumen);
		}
		
	}
	
	
}