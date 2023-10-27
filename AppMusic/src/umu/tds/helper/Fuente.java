package umu.tds.helper;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Fuente {
	
private Font fuenteBauhaus; 
	
	public Fuente() throws FontFormatException, IOException
	{
		fuenteBauhaus = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fuentes/Bauhaus93Regular.ttf"));
		fuenteBauhaus = fuenteBauhaus.deriveFont(70f);
	}
	
	public Font getFont()
	{
		return fuenteBauhaus;
	}

}
