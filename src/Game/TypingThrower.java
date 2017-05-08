package Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TypingThrower {
	private Player p1;
	private Player p2;
	private InputStream in;
	private List<String> words;

	public TypingThrower(Player player1, Player player2, String fileName) {
		this.p1 = player1;
		this.p2 = player2;
		in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// ignore it and try other way
		}
		// if (in == null) {
		// ClassLoader loader = TypingThrower.class.getClassLoader();
		// in = loader.getResourceAsStream(fileName);
		// }
		if (in == null) {
			in = this.getClass().getResourceAsStream(
					"/wordsFile/dictionary.txt");
		}
		if (in == null)
			throw new RuntimeException();
		words = readWords();
	}

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

	public String getWord() {
		Random r = new Random();
		return words.get(r.nextInt(words.size() - 1));
	}

	public void P1Attack() {
		p2.setHP(p2.getHP() - p1.getDamage());
	}

	public void P2Attack() {
		p1.setHP(p1.getHP() - p2.getDamage());
	}

	public boolean isP1Die() {
		return p1.getHP() <= 0;
	}

	public boolean isP2Die() {
		return p2.getHP() <= 0;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

}