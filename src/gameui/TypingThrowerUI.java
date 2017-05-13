package gameui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Game.Player;
import game.TypingThrower;
import gameui.TypingThrowerUI;

public class TypingThrowerUI extends JFrame implements KeyListener {
	private JLabel word;
	private String currentWord;
	private JPanel north;
	private TypingThrower game;
	private JProgressBar p1HP;
	private JProgressBar p2HP;
	private JPanel menu;
	private JPanel playing;

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
		north = new JPanel();
		north.add(word);
		north.setBorder(new EmptyBorder(100, 0, 0, 0));
		p1HP = new JProgressBar();
		p1HP.setMaximum(100);
		p1HP.setValue(100);
		p2HP = new JProgressBar();
		p2HP.setMaximum(100);
		p2HP.setValue(100);
		p2HP.setSize(200, 20);
		add(north, BorderLayout.NORTH);
		add(p1HP, BorderLayout.WEST);
		add(p2HP, BorderLayout.EAST);
	}

	public void run() {
		setVisible(true);
	}

	public static void main(String[] args) {
		TypingThrowerUI ui = new TypingThrowerUI(new TypingThrower(new Player(100, 10), new Player(100, 10), "dictionary.txt"));
		ui.run();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == currentWord.charAt(0)) {
			currentWord = currentWord.substring(1, currentWord.length());
			if (currentWord.length() == 0) {
				game.P1Attack();
				p2HP.setValue(game.getP2().getHP());
				currentWord = game.getWord();
			}
			word.setText(currentWord);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
