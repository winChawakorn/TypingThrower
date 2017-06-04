package gameui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to creating new font as polymorphism.
 * 
 * @author Vittunyuta Maeprasart
 *
 */
public class CreatedFont {
	
	private static Font PROFONT_FOR_POWERLINE = getFont("ProFont For Powerline.ttf");
	private static Font F_28_DAYS_LATER = getFont("28 Days Later.ttf");
	private static Font BERLIN_SANS_FB_BOLD = getFont("Berlin Sans FB Bold.ttf");
	private static Font PLANET_BENSON_2 = getFont("planet benson 2.ttf");
	
	/**
	 * Creating new font from external files
	 * 
	 * @param fileName
	 *            is name of a external file
	 * @return Font type of external font
	 */
	public static Font getFont(String fileName) {
		String path = "/res/" + fileName;
		URL url = URL.class.getResource(path);
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return font;
	}

	/**
	 * Getting font for text field of Login and SignUp pages
	 * @return font for text field
	 */
	public static Font fontInField() {
		return PROFONT_FOR_POWERLINE.deriveFont(Font.PLAIN, 25);
	}
	
	/**
	 * Getting font for buttons of Login page
	 * @return font for buttons of Login page
	 */
	public static Font fontForBtn() {
		return PROFONT_FOR_POWERLINE.deriveFont(Font.PLAIN, 40);
	}
	
	/**
	 * Getting font for text on Waiting page
	 * @return font for wait message on Waiting page
	 */
	public static Font fontOfWaitMessage() {
		return BERLIN_SANS_FB_BOLD.deriveFont(Font.BOLD, 90);
	}
	
	/**
	 * Getting font for menu buttons on Home page
	 * @return font for wait message on waiting page
	 */
	public static Font fontOf4HomeBtn() {
		return PLANET_BENSON_2.deriveFont(Font.BOLD,60);
	}
	
	/**
	 * Getting font for name text on score board in Home page
	 * @return font for name text on score board
	 */
	public static Font fontOfNameOfScore() {
		return PROFONT_FOR_POWERLINE.deriveFont(Font.PLAIN, 60);
	}
	
	/**
	 * Getting font for detail text on score board in Home page
	 * @return font for text on score board
	 */
	public static Font fontOfDetailOfScore() {
		return PROFONT_FOR_POWERLINE.deriveFont(Font.BOLD, 40);
	}
	
	/**
	 * Getting font for counting number before start game
	 * @return font for counting number
	 */
	public static Font fontCountNum() {
		return F_28_DAYS_LATER.deriveFont(Font.BOLD, 300);
	}
	
	/**
	 * Getting font for sign to start game
	 * @return font for sign to start game
	 */
	public static Font fontCountType() {
		return F_28_DAYS_LATER.deriveFont(Font.BOLD, 230);
	}
}
