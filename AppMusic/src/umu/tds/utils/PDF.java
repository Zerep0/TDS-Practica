package umu.tds.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public enum PDF {
	INSTANCE;
	public final Font chapterFont = FontFactory.getFont("src/Fuentes/BAUHS93.ttf", 54);
    public final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.NORMAL);
    public final Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
    private int NumeroChapters = 0;
         
    public Chapter crearSeccion()
    {
    	return new Chapter(NumeroChapters++);
    }
    
    public Chunk crearLinea(String informacion, Font fuenteElegida)
    {
    	return new Chunk(informacion,fuenteElegida);
    }

    public Document crearDocumento(String nombre) {
    	File pdfNewFile = new File(nombre);
    	Document document = null;
    	try {
    		document = new Document();
    	    try {
    	        PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
    	    } catch (FileNotFoundException fileNotFoundException) {
    	        System.out.println("No such file was found to generate the PDF "
    	                + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
    	    }
    	}catch(Exception e)
    	{
    		System.out.println("Fallo en la generacion del pdf");
    	}
    	return document;
    }
    
    public void abrirDocumento(Document documento, String title)
    {
    	if(documento != null)
    	{
    		documento.open();
    		documento.addTitle(title);
    		documento.addSubject("Using iText (usando iText)");
    		documento.addKeywords("Java, PDF, iText");
    		documento.addAuthor("AppMusic");
    		documento.addCreator("AppMusic");
    	}
    		
    }
    
    public void cerrarDocumento(Document documento)
    {
    	if(documento != null)
    		documento.close();
    }
    
    public void añadirParrafo(Chapter chapter,Chunk chunk, int alineamiento)
    {
    	Paragraph parrafo = new Paragraph(chunk);
    	parrafo.setAlignment(alineamiento);
    	chapter.add(parrafo);
    }
    
    public void añadirSeccion(Document documento, Chapter seccion)
    {
    	try {
			documento.add(seccion);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
