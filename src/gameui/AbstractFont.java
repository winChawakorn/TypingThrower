package gameui;

import java.awt.Font;
import java.io.File;
import java.net.URL;

public abstract class AbstractFont {
	public Font getFont(String fileName) throws Exception {
	    String path = "/res/" + fileName;
	    URL url = getClass().getResource(path);
	    Font font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
//	    font = font.deriveFont(style,size);
//	    font = font.deriveFont(Font.BOLD,70);

	    return font;
	}
}
