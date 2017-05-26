package game;

/**
 * 
 * A Player for this game. Each player contains name, HP(Health Point), and
 * damage.
 * 
 * @author Chawakorn Suphepre
 *
 */
public class Player {
	private String name;
	private int HP;
	private int damage;

	/**
	 * Initialize new Player with name, HP, and damage.
	 * 
	 * @param name
	 * @param HP
	 * @param damage
	 */
	public Player(String name, int HP, int damage) {
		this.name = name;
		this.HP = HP;
		this.damage = damage;
	}

	/**
	 * Return this player's name.
	 * 
	 * @return name.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Return this player's HP.
	 * 
	 * @return HP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * Set this player's HP
	 * 
	 * @param HP
	 *            is a new HP to set to
	 */
	public void setHP(int HP) {
		this.HP = HP;
	}

	/**
	 * Return this player's damage.
	 * 
	 * @return damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Set this player's damage.
	 * 
	 * @param damage
	 *            is a new damage to set to.w
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
