package Game;

public class Main {
	public static void main(String[] args) {
		TypingThrower game = new TypingThrower(new Player(100, 100),
				new Player(100, 100), "dictionary.txt");
		System.out.println(game.getWord());
	}
}
