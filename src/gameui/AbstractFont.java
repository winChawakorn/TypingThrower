package gameui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

public class AbstractFont {
	
	public Font getFont(String fileName) {
	    String path = "/res/" + fileName;
	    URL url = getClass().getResource(path);
	    Font font=null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	    return font;
	}
}
