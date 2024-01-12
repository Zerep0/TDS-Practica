package umu.tds.utils;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;

public class a {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document pdf = PDF.INSTANCE.crearDocumento("prueba.pdf");
		PDF.INSTANCE.abrirDocumento(pdf, "AppMusic");
		Chapter pagina = PDF.INSTANCE.crearSeccion();
		PDF.INSTANCE.añadirParrafo(pagina, 
				PDF.INSTANCE.crearLinea("AppMusic", PDF.INSTANCE.chapterFont), Element.ALIGN_CENTER);
		PDF.INSTANCE.añadirParrafo(pagina, 
				PDF.INSTANCE.crearLinea("\n\nCANCIONES MÁS ESCUCHADAS DEL MOMENTO", PDF.INSTANCE.paragraphFont), Element.ALIGN_CENTER);
		PDF.INSTANCE.añadirSeccion(pdf, pagina);		
		PDF.INSTANCE.cerrarDocumento(pdf);

	}

}
