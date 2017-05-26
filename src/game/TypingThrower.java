package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Typing thrower game class that control the game action.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class TypingThrower {
	private Player p1, p2;
	private InputStream in;
	private List<String> words;

	/**
	 * Initialize new game with 2 player.
	 * 
	 * @param player1
	 *            is the first player.
	 * @param player2
	 *            is the second player.
	 */
	public TypingThrower(Player player1, Player player2) {
		this.p1 = player1;
		this.p2 = player2;
		in = null;
		if (in == null) {
			in = this.getClass().getResourceAsStream("/res/dictionary.txt");
		}
		if (in == null)
			throw new RuntimeException();
		words = readWords();
	}

	/**
	 * Read word from a text file and get its into a list of String. Return that
	 * list.
	 * 
	 * @return a list of String that contains many word from the file.
	 */
	public List<String> readWords() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		List<String> list = new ArrayList<String>();
		while (true) {
			String word = "";
			try {
				word = reader.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (word == null)
				break;
			list.add(word.trim().toLowerCase());
		}
		return list;
	}

	/**
	 * Return a random word from the list of String.
	 * 
	 * @return
	 */
	public String getWord() {
		Random r = new Random();
		return words.get(r.nextInt(words.size() - 1));
	}

	/**
	 * Make p1 attack p2 and calculate the HP.
	 */
	public void P1Attack() {
		p2.setHP(p2.getHP() - p1.getDamage());
	}

	/**
	 * Make p2 attack p1 and calculate the HP.
	 */
	public void P2Attack() {
		p1.setHP(p1.getHP() - p2.getDamage());
	}

	/**
	 * Return win or lose status of p1.
	 * 
	 * @return true if p1 is already loss, else false.
	 */
	public boolean isP1Lose() {
		return p1.getHP() <= 0;
	}

	/**
	 * Return win or lose status of p1.
	 * 
	 * @return true if p2 is already loss, else false.
	 */
	public boolean isP2Lose() {
		return p2.getHP() <= 0;
	}

	/**
	 * Return first player.
	 * @return first player.
	 */
	public Player getP1() {
		return p1;
	}
	/**
	 * Return second player.
	 * @return second player.
	 */
	public Player getP2() {
		return p2;
	}
	/**
	 * Return if the game is end.
	 * @return true if the game is end, else false.
	 */
	public boolean isEnd() {
		return isP1Lose() || isP2Lose();
	}

}