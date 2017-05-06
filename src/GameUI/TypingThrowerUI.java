package GameUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import Game.Player;
import Game.TypingThrower;

public class TypingThrowerUI extends JFrame implements KeyListener {
	private JLabel word;
	private String currentWord;
	private TypingThrower game;
	private JProgressBar p1HP;
	private JProgressBar p2HP;

	public TypingThrowerUI(TypingThrower game) {
		super("Typing Thrower");
		this.setSize(1500, 1000);
		addKeyListener(this);
		this.game = game;
		initComponent();
	}

	public void initComponent() {
		currentWord = game.getWord();
		word = new JLabel(currentWord, SwingConstants.CENTER);
		word.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
		add(word, BorderLayout.NORTH);
	}

	public void run() {
		setVisible(true);
	}

	public static void main(String[] args) {
		TypingThrowerUI ui = new TypingThrowerUI(new TypingThrower(new Player(
				100, 100), new Player(100, 100), "dictionary.txt"));
		ui.run();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == currentWord.charAt(0)) {
			currentWord = currentWord.substring(1, currentWord.length());
			if (currentWord.length() == 0)
				currentWord = game.getWord();
			word.setText(currentWord);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
