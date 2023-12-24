package CargadorCanciones;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import CargadorCanciones.Canciones;

public class MapperCancionesXMLtoJava {

	public static Canciones cargarCanciones(String fichero) throws URISyntaxException {

		JAXBContext jc;
		Canciones canciones = null;
		
		try {
			jc = JAXBContext.newInstance("CargadorCanciones");
			Unmarshaller u = jc.createUnmarshaller();
			System.out.println(fichero);
			Path path = Paths.get(new File(fichero).toURI());
			File file = path.toFile();
			canciones = (Canciones) u.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}	
		return canciones;
	}
}